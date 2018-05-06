package com.onlylemi.mapview.utils;

import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.MapMath;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Collection;


/**
 * Created by patnym on 2018-05-06.
 */
@RunWith(RobolectricTestRunner.class)
public class MapMathTest {

    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                {
                        new PointF(1, 0),
                        new PointF(0.5f, 0.5f),
                        0.5f
                },
                {
                        new PointF(1, 0),
                        new PointF(0.5f, -0.5f),
                        -0.5f
                },
                {
                        new PointF(1, 0),
                        new PointF(1, 0),
                        0.0f
                },
                {
                        new PointF(0.5f, 0.5f),
                        new PointF(0, 1),
                        0.5f
                },
                {
                        new PointF(0.5f, 0.5f),
                        new PointF(0, -1),
                        -0.5f
                },
                {
                        new PointF(0, 1),
                        new PointF(0, 1),
                        0.0f
                }
        };
        return Arrays.asList(data);
    }

    @Test
    public void crossProduct_is_correct() {
        for (Object[] d : data()) {
            crossTest((PointF) d[0], (PointF) d[1], (float) d[2]);
        }
    }

    private void crossTest(PointF x1, PointF x2, float expected) {
        Assert.assertEquals(expected, MapMath.crossProduct(x1, x2));
    }



}
