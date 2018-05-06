package com.onlylemi.mapview.library.utils.math;

import android.graphics.PointF;

/**
 * Created by patnym on 2018-05-06.
 */

public class Line {

    private float x;
    private float y;
    private PointF start;
    private PointF stop;

    public Line(PointF start, PointF stop) {
        this.start = start;
        this.stop = stop;
        this.x = stop.x - start.x;
        this.y = stop.y  - start.y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public PointF getStart() {
        return start;
    }

    public PointF getStop() {
        return stop;
    }
}
