package com.onlylemi.mapview.library.camera;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.camera.modes.ICameraState;
import com.onlylemi.mapview.library.camera.modes.implementation.FreeCameraState;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.layer.MapBaseLayer;
import com.onlylemi.mapview.library.utils.BasicInput;
import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.MapUtils;

import java.util.Map;

/**
 * Copyright (C) 9/30/17 nyman (patnym) - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license.
 * <p>
 * You should have received a copy of the MIT license with
 * this file. If not, please write to: patrik911217@gmail.com
 * <p>
 * If you wanna contribute add your name below and please
 * assign yourself a cool nickname.
 */

public class MapViewCamera {
    private static final String TAG = "MapViewCamera";

    private enum TouchState {
        TOUCH_STATE_NO,
        TOUCH_STATE_SCROLL,
        TOUCH_STATE_SCALE,
        TOUCH_STATE_TWO_POINTED
    }

    //All current possible camera modes
    public enum CameraMode {
        FREE,
        CONTAIN_USER,
        FOLLOW_USER,
        CONTAIN_POINTS
    }

    private Matrix viewMatrix;

    private float currentZoom = 1.0f; //aka no zoom

    private float maxZoom;
    private float minZoom;

    private LocationUser user;

    //// TODO: 10/1/17 Might not be needed later on, but currently everything is a bit messy, this helps until more cleaing is done
    private MapView mapView;

    //Represents the width and height of the view (AKA - the canvas size)
    private float viewPortWidth;
    private float viewPortHeight;

    private ICameraState currentCameraState;
    private ICameraState previousCameraState;

    //Temp values used to handle touch events
    private TouchState currentTouchState = TouchState.TOUCH_STATE_NO; // default touch state
    private PointF startTouch = new PointF();
    private float saveZoom;
    private float oldDist;


    public MapViewCamera(MapView mapView) {
        this.mapView = mapView;
        viewMatrix = new Matrix();

        //Default to free camera state
        changeMode(CameraMode.FREE);
    }

    /**
     * Called from the render thread when it starts rendering
     * Will get called after the first call to onSurfaceChangedd
     */
    public void init() {
        initZoom(true);
    }

    /**
     * Called from the render thread if the render surface changes
     * Will always get called during initialization due to how android handle views
     * @param width
     * @param height
     */
    public synchronized void onSurfaceChanged(float width, float height) {
        viewPortWidth = width;
        viewPortHeight = height;

        Log.v(TAG, "Surface changed, new dims: " + width + "x" + height);

        if(currentCameraState != null) {
            currentCameraState.onViewPortChanged(width, height, this);
        }

        //Recalculate zoom values
        initZoom(false);
    }

    /**
     * Called each update from the render thread
     * @param deltaTimeNano
     * @return
     */
    public Matrix update(long deltaTimeNano) {
        //Free mode is a special case
        if(!(currentCameraState instanceof FreeCameraState)) {
            currentCameraState.update(viewMatrix, deltaTimeNano);
        }

        return viewMatrix;
    }

    /**
     * Changes the current camera mode
     * @param mode
     */
    public void changeMode(CameraMode mode) {
        previousCameraState = currentCameraState;
        currentCameraState = CameraModeFactory.createMode(mode, this);

        Log.v(TAG, "Changed mode to: " + currentCameraState.toString());
        //Initialize mode
        currentCameraState.init(this);
    }

    /**
     * Returns to the previous mode
     */
    public void revertMode() {
        if(previousCameraState == null) {
            Log.w(TAG, "Tryed to revert to a null mode. Perhaps you should set a mode before trying to revert? " +
                    " This check save you, Gotcha back jack..!");
            return;
        }


        ICameraState tempState = currentCameraState;
        currentCameraState = previousCameraState;
        previousCameraState = tempState;
        Log.v(TAG, "Reverted to previous mode: " + currentCameraState.toString());
    }

    //region zoom

    /**
     * Calculate max and min zoom
     * @param centerZoom if true will also center the map and set the zoom to show the entire map
     */
    private void initZoom(boolean centerZoom) {
        float widthRatio = viewPortWidth / mapView.getMapWidth();
        float heightRatio = viewPortHeight / mapView.getMapHeight();

        Log.i(TAG, "widthRatio:" + widthRatio);
        Log.i(TAG, "heightRatio:" + heightRatio);

        float idealZoom = 1.0f;
        if (widthRatio * mapView.getMapHeight() <= viewPortHeight) {
            idealZoom = widthRatio;
        } else if (heightRatio * mapView.getMapWidth() <= viewPortWidth) {
            idealZoom = heightRatio;
        }

        maxZoom = idealZoom + mapView.getMapModeOptions().zoomMaxPadding;
        minZoom = idealZoom + mapView.getMapModeOptions().zoomMinPadding;

        if(centerZoom) {
            setCurrentZoom(idealZoom, 0, 0);

            //Translate map to middle
            float midX = (viewPortWidth - mapView.getMapWidth() * currentZoom) / 2;
            float midY = (viewPortHeight - mapView.getMapHeight() * currentZoom) / 2;

            float[] v = new float[9];
            viewMatrix.getValues(v);

            v[2] = midX;
            v[5] = midY;

            viewMatrix.setValues(v);
        }
    }

    public void setCurrentZoom(float zoom) {
        setCurrentZoom(zoom, viewPortWidth / 2, viewPortHeight / 2);
    }

    public void setCurrentZoom(float zoom, float x, float y) {
        float scale = MapMath.truncateNumber(zoom, minZoom, maxZoom);

        viewMatrix.postScale(scale / currentZoom, scale / currentZoom, x, y);

        currentZoom = scale;
    }

    //endregion

    //region touchevents

    //Handle touch events
    public boolean onTouch(BasicInput event) {
        float newDist;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                startTouch.set(event.getX(), event.getY());
                currentTouchState = TouchState.TOUCH_STATE_SCROLL;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    saveZoom = currentZoom;
                    currentTouchState = TouchState.TOUCH_STATE_TWO_POINTED;

                    startTouch = MapMath.getMidPointBetweenTwoPoints(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                }
                break;
            case MotionEvent.ACTION_UP:
                currentTouchState = TouchState.TOUCH_STATE_NO;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                currentTouchState = TouchState.TOUCH_STATE_NO;
                break;
            case MotionEvent.ACTION_MOVE:
                switch (currentTouchState) {
                    case TOUCH_STATE_SCROLL:
                        if(!(currentCameraState instanceof FreeCameraState)) {
                            changeMode(CameraMode.FREE);
                        }
                        viewMatrix.postTranslate(event.getX() - startTouch.x, event.getY() -
                                startTouch.y);
                        startTouch.set(event.getX(), event.getY());
                        break;
                    case TOUCH_STATE_TWO_POINTED:
                        oldDist = MapMath.getDistanceBetweenTwoPoints(event.getX(0), event.getY(0), startTouch.x, startTouch.y);
                        currentTouchState = TouchState.TOUCH_STATE_SCALE;
                        break;
                    case TOUCH_STATE_SCALE:
                        newDist = MapMath.getDistanceBetweenTwoPoints(event.getX(0), event.getY(0), startTouch.x, startTouch.y);
                        float scale = newDist / oldDist;

                        setCurrentZoom(saveZoom * scale, startTouch.x, startTouch.y);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    //endregion

    //region getset

    public LocationUser getUser() {
        return user;
    }

    public void setUser(LocationUser user) {
        this.user = user;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    //endregion
}
