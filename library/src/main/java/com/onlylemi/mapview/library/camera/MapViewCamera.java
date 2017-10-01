package com.onlylemi.mapview.library.camera;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.camera.modes.ICameraState;
import com.onlylemi.mapview.library.camera.modes.implementation.FreeCameraState;
import com.onlylemi.mapview.library.layer.MapBaseLayer;
import com.onlylemi.mapview.library.utils.BasicInput;

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

    //Represents the width and height of the view (AKA - the canvas size)
    private float viewPortWidth;
    private float viewPortHeight;

    private ICameraState currentCameraState;
    private ICameraState previousCameraState;

    private TouchState currentTouchState = TouchState.TOUCH_STATE_NO; // default touch state
    private PointF startTouch = new PointF();


    public MapViewCamera() {
        viewMatrix = new Matrix();

        //Default to free camera state
        changeMode(CameraMode.FREE);
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
    //Handle touch events
    public boolean onTouch(BasicInput event) {
        float newDist;
        float currentZoom = 0;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                startTouch.set(event.getX(), event.getY());
                currentTouchState = TouchState.TOUCH_STATE_SCROLL;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
//                if (event.getPointerCount() == 2) {
//                    saveMatrix.set(currentMatrix);
//                    saveZoom = currentZoom;
//                    startTouch.set(event.getX(0), event.getY(0));
//                    currentTouchState = TouchState.TOUCH_STATE_TWO_POINTED;
//
//                    mid = midPoint(event);
//                    oldDist = distance(event, mid);
//                }
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
//                    case TOUCH_STATE_TWO_POINTED:
//                        oldDist = distance(event, mid);
//                        currentTouchState = TouchState.TOUCH_STATE_SCALE;
//                        break;
//                    case TOUCH_STATE_SCALE:
//                        oldMode = mode == mode.FREE ? oldMode : mode;
//                        currentFreeModeTime = modeOptions.returnFromFreeModeDelayNanoSeconds;
//                        mode = MapView.TRACKING_MODE.FREE;
//                        currentMatrix.set(saveMatrix);
//                        newDist = distance(event, mid);
//                        float scale = newDist / oldDist;
//
//                        if (scale * saveZoom < minZoom) {
//                            scale = minZoom / saveZoom;
//                        } else if (scale * saveZoom > maxZoom) {
//                            scale = maxZoom / saveZoom;
//                        }
//                        thread.setZoom(scale * saveZoom);
//
//                        PointF initPoint = isFollowUser ? user.getWorldPosition() : mid;
//
//                        currentMatrix.postScale(scale, scale, initPoint.x, initPoint.y);
//                        thread.setWorldMatrix(currentMatrix);
//                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }
}
