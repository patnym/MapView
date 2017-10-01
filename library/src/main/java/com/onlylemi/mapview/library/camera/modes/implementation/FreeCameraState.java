package com.onlylemi.mapview.library.camera.modes.implementation;

import android.graphics.Matrix;
import android.util.Log;

import com.onlylemi.mapview.library.camera.MapViewCamera;
import com.onlylemi.mapview.library.camera.modes.ICameraState;

/**
 * Copyright (C) 10/1/17 nyman (patnym) - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license.
 * <p>
 * You should have received a copy of the MIT license with
 * this file. If not, please write to: patrik911217@gmail.com
 * <p>
 * If you wanna contribute add your name below and please
 * assign yourself a cool nickname.
 */

/**
 * This is a dummy state to simulate the "FREE" mode, AKA when only touch inputs are handled
 */
public class FreeCameraState implements ICameraState {

    public FreeCameraState() {
        //do nothing
    }

    @Override
    public void init(MapViewCamera camera) {
        //do nothing
    }

    @Override
    public void update(Matrix m, long deltaTimeNano) {
        //do nothing
        Log.d("FreeMode", "I am updating ,why?");
    }

    @Override
    public void onViewPortChanged(float with, float height, MapViewCamera camera) {
        //do nothing
    }

}
