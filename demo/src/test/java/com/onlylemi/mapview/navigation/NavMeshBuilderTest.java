package com.onlylemi.mapview.navigation;

import android.graphics.PointF;

import com.onlylemi.mapview.TestHelper;
import com.onlylemi.mapview.library.navigation.Edge;
import com.onlylemi.mapview.library.navigation.NavMesh;
import com.onlylemi.mapview.library.navigation.NavMeshBuilder;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.utils.collision.MapAxisBox;
import com.onlylemi.mapview.library.utils.collision.MapConvexObject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.onlylemi.mapview.TestHelper.createBox;

/**
 * Created by patnym on 2018-05-06.
 */

@RunWith(RobolectricTestRunner.class)
public class NavMeshBuilderTest {

    //region Data

    private void createAxisBoxSpaces() {
        space1 = new Space(new MapAxisBox(new PointF(0, 0), 2, 2));
        space2 = new Space(new MapAxisBox(new PointF(2, 0), 2, 2));
    }

    //endregion

    private Space space1;
    private Space space2;

    @Test
    public void can_link_axisbox_spaces() {
        createAxisBoxSpaces();
        NavMeshBuilder.connectSpaces(space1, space2);
        Assert.assertEquals(2.0f, space1.getNeighbourEdge(space2).getCost());
        Assert.assertEquals(2.0f, space2.getNeighbourEdge(space1).getCost());
    }

    @Test
    public void can_link_convex_spaces() {
        Space s1 = new Space(new MapConvexObject(createBox(
                new PointF(22.4f, 81.0f),
                new PointF(245.4f, 140f)
        )));

        Space s2 = new Space(new MapConvexObject(createBox(
                new PointF(245.4f, 81.0f),
                new PointF(284.9f, 384.5f)
        )));
        NavMeshBuilder.connectSpaces(s1, s2);
        Assert.assertTrue(s1.getNeighbours().size() > 0);
        Assert.assertTrue(s2.getNeighbours().size() > 0);
    }

    @Test
    public void can_create_correct_edge_between_axisbox_spaces() {
        createAxisBoxSpaces();
        NavMeshBuilder.connectSpaces(space1, space2);
        Edge edge = space1.getNeighbourEdge(space2);
        Assert.assertEquals(new PointF(1, 0), edge.getMidpoint());
    }

//    @Test
//    public void can_create_correct_edge_between_convex_spaces() {
//
//    }

    @Test
    public void edges_are_not_same_reference() {
        createAxisBoxSpaces();
        NavMeshBuilder.connectSpaces(space1, space2);
        Edge edge1 = space1.getNeighbourEdge(space2);
        Edge edge2 = space2.getNeighbourEdge(space1);
        Assert.assertFalse(edge1 == edge2);
    }
}
