package com.onlylemi.mapview.library.camera;

import android.util.Log;

import com.onlylemi.mapview.library.camera.modes.ICameraState;
import com.onlylemi.mapview.library.camera.modes.implementation.ContainUserCameraState;
import com.onlylemi.mapview.library.camera.modes.implementation.FreeCameraState;

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

class CameraModeFactory {
    private static final String TAG = "CameraModeFactory";

    //Creates a camera mode
    public static ICameraState createMode(MapViewCamera.CameraMode mode, MapViewCamera camera) {
        ICameraState returnState = null;
        switch (mode) {
            case FREE:
                returnState = new FreeCameraState();
                break;
            case CONTAIN_USER:
                returnState = new ContainUserCameraState(camera);
                break;
            default:
                //shouldnt happen
                Log.w(TAG, "Factory defaulted, what did you do? (This should never happen)");
        }

        return returnState;
    }

}
