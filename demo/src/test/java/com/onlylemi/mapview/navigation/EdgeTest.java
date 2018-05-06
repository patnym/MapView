package com.onlylemi.mapview.navigation;

import android.graphics.PointF;

import com.onlylemi.mapview.library.navigation.Edge;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by patnym on 2018-05-06.
 */

@RunWith(RobolectricTestRunner.class)
public class EdgeTest {

    @Test
    public void can_get_midpoint() {
        Edge edge = new Edge(new PointF(0, 1), new PointF(0, -1));
        Assert.assertEquals(new PointF(0, 0), edge.getMidpoint());
    }

    @Test
    public void can_clone_edge() {
        Edge edge = new Edge(new PointF(0, 1), new PointF(0, -1));
        Assert.assertFalse(edge == edge.clone());
    }

}
