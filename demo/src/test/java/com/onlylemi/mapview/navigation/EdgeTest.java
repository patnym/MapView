package com.onlylemi.mapview.navigation;

import android.graphics.PointF;

import com.onlylemi.mapview.library.exceptions.InvalidNeighbourException;
import com.onlylemi.mapview.library.navigation.Edge;
import com.onlylemi.mapview.library.navigation.IDiscoverable;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Iterator;

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
    public void is_idiscoverable() {
        Assert.assertTrue(new Edge() instanceof IDiscoverable);
    }

    @Test
    public void can_set_neighbours() {
        Edge edge = new Edge(new PointF(0, 1), new PointF(0, -1));
        edge.setNeighbours(new ArrayList<IDiscoverable>());
        Assert.assertTrue(edge.getDiscoverables().size() == 0);
    }

    @Test
    public void can_add_neighbour() {
        Edge edge = new Edge(new PointF(0, 1), new PointF(0, -1));
        Edge edge2= new Edge(new PointF(1, 2), new PointF(1, -2));
        edge.addNeighbour(edge2);
        Iterator<IDiscoverable> it = edge.getDiscoverables().iterator();
        Assert.assertEquals(edge2, it.next());
    }

    @Test(expected = InvalidNeighbourException.class)
    public void can_not_add_same_neighbour_twice() {
        Edge edge = new Edge(new PointF(0, 1), new PointF(0, -1));
        Edge edge2= new Edge(new PointF(0, 2), new PointF(0, -2));
        edge.addNeighbour(edge2);
        edge.addNeighbour(edge2);
    }

    @Test
    public void can_get_neighbour_cost() {
        Edge edge = new Edge(new PointF(0, 1), new PointF(0, -1));
        Edge edge2= new Edge(new PointF(1, 2), new PointF(1, -2));
        edge.addNeighbour(edge2);

        Assert.assertEquals(1.0f, edge.getDiscoverableCost(edge2));
    }

    @Test
    public void can_add_temporary_neighbour() {
        Edge edge = new Edge(new PointF(0, 1), new PointF(0, -1));
        Edge tEdge = new Edge(new PointF(1, 1), new PointF(1, -1));
        edge.addDisposableDiscoverable(tEdge);
        Assert.assertEquals(1, edge.getDiscoverables().size());
    }
}
