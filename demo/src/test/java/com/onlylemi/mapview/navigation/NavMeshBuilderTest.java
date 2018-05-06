package com.onlylemi.mapview.navigation;

import android.graphics.PointF;

import com.onlylemi.mapview.library.navigation.Edge;
import com.onlylemi.mapview.library.navigation.NavMeshBuilder;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.utils.collision.MapAxisBox;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
    public void can_create_correct_edge_between_axisbox_spaces() {
        createAxisBoxSpaces();
        NavMeshBuilder.connectSpaces(space1, space2);
        Edge edge = space1.getNeighbourEdge(space2);
        Assert.assertEquals(new PointF(1, 0), edge.getMidpoint());
    }

    @Test
    public void edges_are_not_same_reference() {
        createAxisBoxSpaces();
        NavMeshBuilder.connectSpaces(space1, space2);
        Edge edge1 = space1.getNeighbourEdge(space2);
        Edge edge2 = space2.getNeighbourEdge(space1);
        Assert.assertFalse(edge1 == edge2);
    }
}
