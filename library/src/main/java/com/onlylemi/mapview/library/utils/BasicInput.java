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


    private float x, y;
    private int action;

    public BasicInput(MotionEvent event) {
        this(event.getX(), event.getY(), event.getAction());
    }

    public BasicInput(float x, float y, int action) {
        this.x = x;
        this.y = y;
        this.action = action;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}
