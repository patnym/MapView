package com.onlylemi.mapview.library.utils;

import android.view.MotionEvent;

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

public class BasicInput {


    private float[] x, y;

    private int pointerCount;
    private int action;

    public BasicInput(MotionEvent event) {
        if(event.getPointerCount() == 1) {
            init(event.getX(), event.getY(), event.getAction());
        } else {
            float[] tX = new float[event.getPointerCount()];
            float[] tY = new float[event.getPointerCount()];
            for(int i = 0; i < event.getPointerCount(); i++) {
                tX[i] = event.getX(i);
                tY[i] = event.getY(i);
            }
            init(tX, tY, event.getAction(), event.getPointerCount());
        }
    }

    public void init(float x, float y, int action) {
        this.x = new float[]{ x };
        this.y = new float[]{ y };
        this.action = action;
        this.pointerCount = 1;
    }

    public void init(float[] x, float[] y, int action, int pointerCount) {
        this.x = x;
        this.y = y;
        this.action = action;
        this.pointerCount = pointerCount;
    }

    public float getX() {
        return x[0];
    }

    public float getX(int i) {
        return x[i];
    }

    public void setX(float[] x) {
        this.x = x;
    }

    public float getY() {
        return y[0];
    }

    public float getY(int i) {
        return y[i];
    }

    public void setY(float[] y) {
        this.y = y;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getPointerCount() {
        return pointerCount;
    }

    public void setPointerCount(int pointerCount) {
        this.pointerCount = pointerCount;
    }

}
