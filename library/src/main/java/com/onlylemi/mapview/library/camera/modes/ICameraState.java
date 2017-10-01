package com.onlylemi.mapview.library.camera.modes;

import android.graphics.Matrix;
import android.view.MotionEvent;

import com.onlylemi.mapview.library.camera.MapViewCamera;

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

public interface ICameraState {
    /**
     * Called on creation
     * @param camera
     */
    void init(MapViewCamera camera);

    /**
     * Called each update from the render thead
     * @param m
     * @param deltaTimeNano
     */
    void update(Matrix m, long deltaTimeNano);

    /**
     * Called if the view port changes
     * @param with
     * @param height
     */
    void onViewPortChanged(float with, float height, MapViewCamera camera);
}
