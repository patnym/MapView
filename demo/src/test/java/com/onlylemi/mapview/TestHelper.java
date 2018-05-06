package com.onlylemi.mapview;

import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.math.Line;

import junit.framework.Assert;

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
}
