package com.onlylemi.mapview.library.utils.collision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.math.Line;

import java.util.ArrayList;

/**
 * Created by patnym on 14/12/2017.
 */

public class MapAxisBox extends BaseCollisionMesh {

    private float width;
    private float height;

    public MapAxisBox(PointF topLeft, PointF botRight) {
        this(new PointF(topLeft.x + ((botRight.x - topLeft.x) / 2),
                topLeft.y + ((botRight.y - topLeft.y) / 2)),
                botRight.x - topLeft.x, botRight.y - topLeft.y);
    }

    public MapAxisBox(PointF position, float width, float height) {
        this.position = new PointF(position.x - (width / 2), position.y - (height / 2));
        this.width = width;
        this.height = height;

        this.debugPaint = new Paint();
        this.debugPaint.setStyle(Paint.Style.STROKE);
        this.debugPaint.setStrokeWidth(2.0f);
        createCollisionLines(this.position);
    }


    @Override
    public boolean isPointInside(PointF position) {
        return ((position.x > this.position.x && position.x < this.position.x + width) &&
                (position.y > this.position.y && position.y < this.position.y + height));
    }

    @Override
    public void debugDraw(Matrix m, Canvas canvas) {
        PointF topLeft = MapMath.transformPoint(m, position);
        PointF botRight = MapMath.transformPoint(m, new PointF(position.x + width, position.y + height));
        canvas.drawRect(topLeft.x, topLeft.y, botRight.x, botRight.y, debugPaint);
    }

    private void createCollisionLines(PointF topLeft) {
        collisionLines = new ArrayList(4);
        collisionLines.add(new Line(
                new PointF(topLeft.x, topLeft.y),
                new PointF(topLeft.x + width, topLeft.y)
        ));
        collisionLines.add(new Line(
                new PointF(topLeft.x + width, topLeft.y),
                new PointF(topLeft.x + width, topLeft.y + height)
        ));
        collisionLines.add(new Line(
                new PointF(topLeft.x + width, topLeft.y + height),
                new PointF(topLeft.x, topLeft.y + height)
        ));
        collisionLines.add(new Line(
                new PointF(topLeft.x, topLeft.y + height),
                new PointF(topLeft.x, topLeft.y)
        ));
    }
}
