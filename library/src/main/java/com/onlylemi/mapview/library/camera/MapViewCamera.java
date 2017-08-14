package com.onlylemi.mapview.library.camera;

import android.graphics.Matrix;

import com.onlylemi.mapview.library.camera.modes.ContainPointsMode;
import com.onlylemi.mapview.library.camera.modes.FollowUserMode;
import com.onlylemi.mapview.library.camera.modes.IBaseMode;
import com.onlylemi.mapview.library.graphics.BaseGraphics;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.MapUtils;

import java.util.List;

/**
 * Created by patny on 2017-08-14.
 */

public class MapViewCamera {

    public Matrix viewMatrix = new Matrix();
    private float zoom = 1.0f;
    private float viewHeight, viewWidth;

    private float maxZoom = 2.0f;
    private float minZoom = 0.2f;

    private IBaseMode currentMode;
    private IBaseMode previousMode;

    public MapViewCamera() {}

    public MapViewCamera(float viewWidth, float viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }

    public Matrix update(long deltaTimeNano) {
        //This should later on default to free mode
        if(currentMode != null)
            viewMatrix = currentMode.update(viewMatrix, zoom, deltaTimeNano);

        return viewMatrix;
    }

    public void setContainGraphicsMode(final List<? extends BaseGraphics> points) {
        saveMode(currentMode);

        currentMode = new ContainPointsMode(MapUtils.getPositionListFromGraphicList(points), this);
    }

    public void setFollowUserMode(final LocationUser user) {
        saveMode(currentMode);

        currentMode = new FollowUserMode(user, this);
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public void setMinZoom(float minZoom) {
        this.minZoom = minZoom;
    }

    public float getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(float viewHeight) {
        this.viewHeight = viewHeight;
    }

    public float getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(float viewWidth) {
        this.viewWidth = viewWidth;
    }

    private void saveMode(IBaseMode mode) {
        previousMode = mode;
    }

    private void revert() {
        currentMode = previousMode;
    }

    public float calculateZoomWithinPoints(float maxX, float maxY, float minX, float minY) {
        float imageWidth = maxX - minX;
        float imageHeight = maxY - minY;

        float widthRatio = viewWidth / imageWidth;
        float heightRatio = viewHeight / imageHeight;
        float ratio = 0.0f;

        if (widthRatio * imageHeight <= viewHeight) {
            ratio = widthRatio;
        } else if (heightRatio * imageWidth <= viewWidth) {
            ratio = heightRatio;
        }

        return ratio;
    }

    public void translate(float x, float y) {
        viewMatrix.postTranslate(x, y);
    }

    public void updateZoom(float zoom) {
        updateZoom(zoom, 0, 0);
    }

    public void updateZoom(float zoom, float x, float y) {
        float scale = MapMath.truncateNumber(zoom, minZoom, maxZoom);

        viewMatrix.postScale(scale / this.zoom, scale / this.zoom, x, y);
        this.zoom = scale;
    }
}
