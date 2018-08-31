package com.onlylemi.mapview.library.utils.math;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.MapMath;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.onlylemi.mapview.library.utils.MapMath.isLesserOrEqual;

/**
 * Created by patnym on 2018-05-06.
 */

public class Line {
    protected float x;
    protected float y;
    protected PointF start;
    protected PointF stop;
    protected PointF midPoint;

    private Paint debugPaint;

    public Line(PointF start, PointF stop) {
        this.start = start;
        this.stop = stop;
        this.x = stop.x - start.x;
        this.y = stop.y  - start.y;
        midPoint = new PointF(start.x + (x / 2), start.y + (y / 2));

        debugPaint = new Paint();
        this.debugPaint = new Paint();
        this.debugPaint.setColor(Color.YELLOW);
        this.debugPaint.setStyle(Paint.Style.STROKE);
        this.debugPaint.setStrokeWidth(2.0f);
    }

    public Line findCommonEdge(Line line) {
        if(MapMath.crossProduct(this, line) != 0) {
            return null;
        }
        //Check if one point is inside the other line
        Set<PointF> iPoints = new HashSet();
        //Compare this with line
        if(line.isPointInside(start)) {
            iPoints.add(start);
        }
        if(line.isPointInside(stop)) {
            iPoints.add(stop);
        }
        //Compare line with this
        if(isPointInside(line.start)) {
            iPoints.add(line.start);
        }
        if(isPointInside(line.stop)) {
            iPoints.add(line.stop);
        }

        if(iPoints.size() <= 1) {
            return null;
        }
        Iterator<PointF> it = iPoints.iterator();
        return new Line(it.next(), it.next());
    }

    public void debugDraw(Matrix m, Canvas canvas) {
        float[] start = { this.start.x, this.start.y };
        float[] stop = { this.stop.x, this.stop.y };
        m.mapPoints(start);
        m.mapPoints(stop);
        canvas.drawLine(start[0], start[1], stop[0], stop[1], debugPaint);
    }

    public boolean isPointInside(PointF point) {
        if (start.x >= stop.x) {
            if (!(isLesserOrEqual(stop.x, point.x) && isLesserOrEqual(point.x, start.x))) {return false;}
        } else {
            if (!(isLesserOrEqual(start.x, point.x) && isLesserOrEqual(point.x,stop.x))) {return false;}
        }
        if (start.y >= stop.y) {
            if (!(isLesserOrEqual(stop.y, point.y) && isLesserOrEqual(point.y, start.y))) {return false;}
        } else {
            if (!(isLesserOrEqual(start.y, point.y) && isLesserOrEqual(point.y, stop.y))) {return false;}
        }
        return true;
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

    public PointF getMidPoint() {
        return midPoint;
    }

    public void setDebugColor(int color) {
        this.debugPaint.setColor(color);
    }
}
