package com.onlylemi.mapview.library;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.Log;

import com.onlylemi.mapview.library.camera.BaseMode;
import com.onlylemi.mapview.library.camera.ContainPointsMode;
import com.onlylemi.mapview.library.camera.ContainUserMode;
import com.onlylemi.mapview.library.camera.FollowUserMode;
import com.onlylemi.mapview.library.camera.FreeMode;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.utils.MapMath;

import java.util.List;

/**
 * Created by patnym on 26/12/2017.
 */
//I'ave kept this class seperate because I wanna add level support in the future
public class MapViewCamera {
    private static final String TAG = "MapViewCamera";

    private Matrix worldMatrix = new Matrix();
    private float currentZoom = 1; //Its just alot easier to keep track of any scaling like this
    private PointF currentPosition = new PointF();

    private CameraModeFactory factory;

    //Holds a reference to the current user we eventually are tracking
    private LocationUser currentUser;

    private BaseMode currentCameraMode;
    private BaseMode previousCameraMode;

    //MapView width and height
    private int viewWidth;
    private int viewHeight;

    //The actual current maplayer width and height
    private int mapWidth;
    private int mapHeight;

    //These are zoom paddings - Multiples, aka 2 = you can zoom in twice the size of the original
    private float maxZoomPadding = 2.0f;
    private float minZoomPadding = 0.5f;

    private float maxZoom;
    private float minZoom;

    public MapViewCamera(int viewWidth, int viewHeight, int mapWidth, int mapHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.factory = new CameraModeFactory(this);
        initZoom();
    }

    /**
     * Will initialize/create all modes
     */
    public void initialize(@Nullable LocationUser user) {
        if(user != null) {
            currentUser = user;
        }

        currentCameraMode = factory.createFreeMode();
        currentCameraMode.onStart();
    }

    /**
     * Updates any camera mode and also returns the world matrix
     * @param deltaTimeNano
     * @return
     */
    public Matrix update(long deltaTimeNano) {
        return currentCameraMode.update(worldMatrix, deltaTimeNano);
    }

    /**
     * Reverts to the previous camera mode
     */
    public void revertCameraMode() {
        BaseMode tmp = currentCameraMode;
        currentCameraMode.onEnd();
        currentCameraMode = previousCameraMode;
        previousCameraMode = tmp;
        currentCameraMode.onStart();
    }

    /**
     * Calls on end on the current camera mode and swaps to the input one
     * @param cameraMode
     */
    public void switchCameraMode(BaseMode cameraMode) {
        currentCameraMode.onEnd();
        previousCameraMode = currentCameraMode;
        currentCameraMode = cameraMode;
        currentCameraMode.onStart();
    }

    /**
     * Calculates the min and max zoom values
     * We assume the default starting mode is the entire map in view centered
     */
    public void initZoom() {
        float widthRatio = (float) viewWidth / mapWidth;
        float heightRatio = (float) viewHeight / mapHeight;

        Log.i(TAG, "widthRatio:" + widthRatio);
        Log.i(TAG, "heightRatio:" + heightRatio);

        float zoom = 1.0f;

        if (widthRatio * mapHeight <= viewHeight) {
            zoom = widthRatio;
        } else if (heightRatio * mapWidth <= viewWidth) {
            zoom = heightRatio;
        }

        minZoom = currentZoom - (currentZoom * minZoomPadding);
        //If set to use contain user mode, this value will get overridden to prevent jerking
        maxZoom = currentZoom * maxZoomPadding;

        zoom(zoom, 0, 0);
        translate((viewWidth / 2) - ((mapWidth * currentZoom) / 2), (viewHeight / 2) - ((mapHeight * currentZoom) / 2));
    }

    public void translate(float x, float y) {
        currentPosition.x += x;
        currentPosition.y += y;
        worldMatrix.postTranslate(x, y);
    }

    public void zoom(float zoom) {
        zoom(zoom, viewWidth / 2, viewHeight / 2);
    }

    public void zoom(float zoom, float worldX, float worldY) {
        //float newZoom = MapMath.truncateNumber(zoom, minZoom, maxZoom);
        worldMatrix.postScale(zoom / currentZoom, zoom / currentZoom, worldX, worldY);
        currentZoom = zoom;
    }

    //region GETSET

    public float getCurrentZoom() {
        return currentZoom;
    }

    public PointF getCurrentPosition() {
        return currentPosition;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public float getMaxZoom() {
        return maxZoom;
    }

    public float getMinZoom() {
        return minZoom;
    }

    public void setMaxZoom(float maxZoom) {
        this.maxZoom = maxZoom;
    }

    public void setMinZoom(float minZoom) {
        this.minZoom = minZoom;
    }

    public LocationUser getCurrentUser() {
        return currentUser;
    }

    public CameraModeFactory getFactory() {
        return factory;
    }

    //endregion GETSET

    //region factory

    public class CameraModeFactory {

        private MapViewCamera camera;

        public CameraModeFactory(MapViewCamera camera) {
            this.camera = camera;
        }

        public ContainUserMode createContainUserMode() {
            return new ContainUserMode(camera, camera.getCurrentUser());
        }

        public FreeMode createFreeMode() {
            return new FreeMode(camera);
        }

        public FollowUserMode createFollowUserMode() {
            return new FollowUserMode(camera, camera.getCurrentUser());
        }

        public ContainPointsMode createContainPointsMode(List<PointF> points, boolean containUser, float padding) {
            if(containUser) {
                points.add(camera.getCurrentUser().getPosition());
            }

            return new ContainPointsMode(camera, points, padding);
        }

    }

    //endregion
}