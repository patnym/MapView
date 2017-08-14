package com.onlylemi.mapview.library.camera.modes;

import android.graphics.Matrix;
import android.graphics.PointF;

import com.onlylemi.mapview.library.camera.MapViewCamera;
import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.MapUtils;

import java.util.List;

/**
 * Created by patny on 2017-08-14.
 */

public class ContainPointsMode implements IBaseMode {

    private List<PointF> zoomPoints;
    private float padding = 200.0f;
    private MapViewCamera camera;

    private float translationSpeed = 400.0f / MapMath.NANOSECOND;
    private float zoomSpeed = 2.0f / MapMath.NANOSECOND;

    public ContainPointsMode(List<PointF> zoomPoints, MapViewCamera camera) {
        this.zoomPoints = zoomPoints;
        this.camera = camera;
    }

    @Override
    public Matrix update(Matrix transformMatrix, float currentZoom, long deltaTimeNano) {
        Matrix returnTransformMatrix = new Matrix();
        returnTransformMatrix.set(transformMatrix);

        float[] minmax = MapUtils.getMaxMinFromPointList(zoomPoints, padding);
        float zoom = camera.calculateZoomWithinPoints(minmax[0], minmax[1], minmax[2], minmax[3]);
        float d = zoom - currentZoom;
        int sign = (int) (d / Math.abs(d));
        d = d * sign; //Absolute distance
        float zVelocity = zoomSpeed * sign * deltaTimeNano;
        d -= Math.abs(zVelocity);

        //move towards target using velocity
        if (d <= 0.0f) {
            camera.updateZoom(zoom);
        } else {
            camera.updateZoom(currentZoom + zVelocity);
        }

        //My point on the view coordinate system
        PointF dst = MapMath.getMidPointBetweenTwoPoints(minmax[0], minmax[1], minmax[2], minmax[3]);
        float[] b = {dst.x, dst.y};
        returnTransformMatrix.mapPoints(b);

        //My point in view coords
        dst.x = b[0];
        dst.y = b[1];

        //Mid point of the view coordinate system
        PointF trueMid = new PointF(camera.getViewWidth() / 2, camera.getViewHeight() / 2);

        //Direction - NOTE we are going from the mid towards our point because graphics yo
        PointF desti = new PointF(trueMid.x - b[0], trueMid.y - b[1]);

        //This is also the distance from our point to the middle
        float distance = desti.length();

        PointF dir = new PointF();

        dir.x = desti.x / distance;
        dir.y = desti.y / distance;

        distance -= translationSpeed * deltaTimeNano;

        if (distance <= 0.0f) {
            returnTransformMatrix.postTranslate(desti.x, desti.y);
        } else {
            returnTransformMatrix.postTranslate(dir.x * translationSpeed * deltaTimeNano, dir.y * translationSpeed * deltaTimeNano);
        }

        return returnTransformMatrix;
    }
}
