package com.onlylemi.mapview;

import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.math.Line;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patnym on 2018-05-06.
 */

public class TestHelper {

    public static PointF point(float x, float y) {
        return new PointF(x, y);
    }

    public static Line line(PointF point1, PointF point2) {
        return new Line(point1, point2);
    }

    public static void testPointsAreLine(Line line, PointF expectedPoint1,
                                  PointF expectedPoint2) {
        Assert.assertTrue(line.getStart().equals(expectedPoint1) ||
                line.getStop().equals(expectedPoint1));
        Assert.assertTrue(line.getStart().equals(expectedPoint2) ||
                line.getStop().equals(expectedPoint2));
    }

    public static List<PointF> createBox(PointF topLeft, PointF botRight) {
        float width = botRight.x - topLeft.x;
        float height = botRight.y - topLeft.y;

        List<PointF> shape = new ArrayList<>();
        shape.add(new PointF(topLeft.x, topLeft.y));
        shape.add(new PointF(topLeft.x + width, topLeft.y));
        shape.add(new PointF(botRight.x, botRight.y));
        shape.add(new PointF(topLeft.x, topLeft.y + height));
        return shape;
    }
}
