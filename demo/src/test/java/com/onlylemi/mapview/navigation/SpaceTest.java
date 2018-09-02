package com.onlylemi.mapview.navigation;

import android.graphics.PointF;

import com.onlylemi.mapview.library.exceptions.InvalidNeighbourException;
import com.onlylemi.mapview.library.navigation.Edge;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.utils.collision.MapAxisBox;
import com.onlylemi.mapview.library.utils.collision.MapAxisCircle;
import com.onlylemi.mapview.library.utils.collision.MapConvexObject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.onlylemi.mapview.TestHelper.point;

/**
 * Created by patnym on 2018-05-06.
 */

@RunWith(RobolectricTestRunner.class)
public class SpaceTest {

    //region Data

    private MapAxisBox axisBox() {
        return new MapAxisBox(new PointF(0, 0), 2, 2);
    }

    //endregion

    @Test
    public void can_create_space() {
        Space space = new Space();
        Assert.assertNotNull(space);
    }

    @Test
    public void can_add_neighbours() {
        Space space = new Space(axisBox());
        Edge edge = new Edge();
        space.addNeighbour(space, edge);
        space.addNeighbour(new Space(axisBox()), new Edge());
        Assert.assertEquals(2, space.getNeighbours().size());
    }

    @Test(expected = InvalidNeighbourException.class)
    public void can_only_add_same_neighbour_once() {
        Space space = new Space(axisBox());
        space.addNeighbour(space, new Edge());
        space.addNeighbour(space, new Edge());
    }

    @Test
    public void is_point_inside_space() {
        Space space = new Space(new MapAxisBox(new PointF(0, 0), 2, 2));
        Assert.assertTrue(space.isPointInside(new PointF(0, 0)));
    }

    @Test
    public void is_point_outside_space() {
        Space space = new Space(axisBox());
        Assert.assertFalse(space.isPointInside(new PointF(5, 0)));
    }

    @Test
    public void can_get_neighbour_edge() {
        Space space1 = new Space(new MapAxisBox(new PointF(0, 0),2, 2));
        Space space2 = new Space(new MapAxisBox(new PointF(2, 0),2, 2));
        Edge edge12 = new Edge(new PointF(1, 1), new PointF(1, -1));
        space1.addNeighbour(space2, edge12);
        Assert.assertEquals(edge12, space1.getNeighbourEdge(space2));
    }

    @Test
    public void can_find_common_edge() {
        Space space1 = new Space(new MapAxisBox(new PointF(0, 0),2, 2));
        Space space2 = new Space(new MapAxisBox(new PointF(2, 0),2, 2));
        Edge edge = space1.findCommonEdge(space2);
        Assert.assertEquals(point(1, 0), edge.getMidpoint());
    }

    @Test
    public void return_null_on_none_common_edge() {
        Space space1 = new Space(new MapAxisBox(point(0, 0), 3f, 14f));
        Space space2 = new Space(new MapAxisBox(point(7,0), 3f, 14f));
        Edge edge = space1.findCommonEdge(space2);
        Assert.assertNull(edge);
    }

    @Test
    public void can_get_all_edges() {
        Space space1 = new Space(new MapAxisBox(new PointF(0, 0),2, 2));
        Space space2 = new Space(new MapAxisBox(new PointF(2, 0),2, 2));
        Edge edge12 = new Edge(new PointF(1, 1), new PointF(1, -1));
        space1.addNeighbour(space2, edge12);
        space2.addNeighbour(space1, edge12);
        Assert.assertTrue(space1.getEdges().contains(edge12));
    }
    
    //// TODO: 2018-05-27 Write above tests but for a convex shape aswell 
}
