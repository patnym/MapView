package com.onlylemi.mapview.library.utils.collision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.math.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patnym on 2018-05-06.
 */

public class MapConvexObject extends BaseCollisionMesh {

    public MapConvexObject(List<PointF> shape) {
        this(new PointF(0, 0), shape);
    }

    public MapConvexObject(PointF position, List<PointF> shape) {
        this.position = position;
        this.collisionLines = new ArrayList();
        PointF prev = shape.get(shape.size() - 1);
        for(int i = 0; i < shape.size(); i++) {
            PointF cur = shape.get(i);
            collisionLines.add(new Line(
                        new PointF(
                            prev.x + position.x,
                            prev.y + position.y
                    ),  new PointF(
                            cur.x + position.x,
                            cur.y + position.y
                    )));
            collisionLines.get(i).setDebugColor(Color.BLACK);
            prev = cur;
        }
        if(!isShapeConvex(collisionLines)) {
            throw new IllegalArgumentException("Shape must be convex, " +
                    "AKA: All sides must turn the same direction" + shape.toString());
        }
    }

    @Override
    public boolean isPointInside(PointF position) {
        Direction dir = Direction.SAME;
        for(int i = 0; i < collisionLines.size(); i++) {
            Line shapeLine = collisionLines.get(i);
            Direction nDir = getDirection(shapeLine, new Line(shapeLine.getStart(), position));
            if(dir == Direction.SAME) {
                dir = nDir;
            } else if(nDir != Direction.SAME &&
                    nDir != dir) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void debugDraw(Matrix m, Canvas canvas) {
        for (Line l : collisionLines) {
            l.debugDraw(m, canvas);
        }
    }

    public static Direction getDirection(Line u, Line v) {
        float value = MapMath.crossProduct(u, v);
        if(value > 0) {
            return Direction.LEFT;
        } else if(value < 0) {
            return Direction.LEFT.RIGHT;
        }
        return Direction.SAME;
    }

    public enum Direction {
        LEFT,
        RIGHT,
        SAME
    }

    public static boolean isShapeConvex(List<Line> shape) {
        Direction dir = Direction.SAME;
        Line prev = shape.get(shape.size() - 1);
        for(int i = 0; i < shape.size(); i++) {
            Direction nDir = getDirection(prev, shape.get(i));
            if(dir == Direction.SAME) {
                dir = nDir;
            } else if(nDir != Direction.SAME &&
                    nDir != dir) {
                return false;
            }
            prev = shape.get(i);
        }
        return true;
    }
}
