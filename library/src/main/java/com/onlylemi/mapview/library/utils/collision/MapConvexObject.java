package com.onlylemi.mapview.library.utils.collision;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.math.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patnym on 2018-05-06.
 */

public class MapConvexObject extends BaseCollision {

    List<Line> collisionShape;

    public MapConvexObject(PointF position, List<PointF> shape) {
        this.position = position;
        this.collisionShape = new ArrayList();
        PointF prev = shape.get(shape.size() - 1);
        for(int i = 0; i < shape.size(); i++) {
            PointF cur = shape.get(i);
            collisionShape.add(new Line(
                        new PointF(
                            prev.x + position.x,
                            prev.y + position.y
                    ),  new PointF(
                            cur.x + position.x,
                            cur.y + position.y
                    )));
            prev = cur;
        }
    }

    @Override
    public boolean isPointInside(PointF position) {
        Direction dir = Direction.SAME;
        for(int i = 0; i < collisionShape.size(); i++) {
            Line shapeLine = collisionShape.get(i);
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

    }

    private Direction getDirection(Line u, Line v) {
        float value = MapMath.crossProduct(u, v);
        if(value > 0) {
            return Direction.LEFT;
        } else if(value < 0) {
            return Direction.LEFT.RIGHT;
        }
        return Direction.SAME;
    }

    private enum Direction {
        LEFT,
        RIGHT,
        SAME
    }
}
