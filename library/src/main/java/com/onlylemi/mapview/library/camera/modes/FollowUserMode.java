package com.onlylemi.mapview.library.camera.modes;

import android.graphics.Matrix;
import android.graphics.PointF;

import com.onlylemi.mapview.library.camera.MapViewCamera;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.utils.MapMath;

import java.util.List;

/**
 * Created by patny on 2017-08-14.
 */

public class FollowUserMode implements IBaseMode {

    private LocationUser user;
    private MapViewCamera camera;

    private float translationSpeed = 400.0f / MapMath.NANOSECOND;
    private float zoomSpeed = 2.0f / MapMath.NANOSECOND;

    public FollowUserMode(LocationUser user, MapViewCamera camera) {
        this.user = user;
        this.camera = camera;
    }

    @Override
    public Matrix update(Matrix transformMatrix, float currentZoom, long deltaTimeNano) {
        Matrix returnTransformMatrix = new Matrix();
        returnTransformMatrix.set(transformMatrix); //Copy matrix

        //My point on the view coordinate system
        PointF dst = new PointF();
        dst.set(user.getPosition());
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
