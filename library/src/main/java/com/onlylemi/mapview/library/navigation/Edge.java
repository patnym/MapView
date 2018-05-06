package com.onlylemi.mapview.library.navigation;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.math.Line;

/**
 * Created by patnym on 2018-05-06.
 */

public class Edge implements Cloneable {

    protected float cost;
    protected Line line;

    public Edge() {

    }

    public Edge(PointF start, PointF stop) {
        this.line = new Line(start, stop);
    }

    public Edge(Line line) {
        this.line = line;
    }

    public PointF getMidpoint() {
        return line.getMidPoint();
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getCost() {
        return cost;
    }

    public void debugDraw(Matrix m, Canvas canvas) {
        line.debugDraw(m, canvas);
    }

    @Override
    public Object clone() {
        PointF start = line.getStart();
        PointF stop = line.getStop();
        return new Edge(new PointF(start.x, start.y), new PointF(stop.x, stop.y));
    }
}
