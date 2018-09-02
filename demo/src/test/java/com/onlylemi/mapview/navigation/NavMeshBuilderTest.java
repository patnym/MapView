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

import java.util.ArrayList;
import java.util.List;

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

    /**
     * This creates a space structure that looks like this
     *  |----------|
     *  | s1    |  |
     *  |-------|  |
     *  |  |////|  |
     *  |s4|----|s2|
     *  |  | s5 |  |
     *  |  |----|  |
     *  |  |////|  |
     *  |  | s6 |  |
     *  |  |----|  |
     *  |  |////|  |
     *  |-------|  |
     *  |   s3  |  |
     *  ------------
     */
    private void createComplexStructure() {
        //Create a structure
        s1 = new Space(new MapConvexObject(createBox(
                new PointF(22.4f, 81.0f),
                new PointF(245.4f, 140f)
        )));

        s2 = new Space(new MapConvexObject(createBox(
                new PointF(245.4f, 81.0f),
                new PointF(284.9f, 384.5f)
        )));

        s3 = new Space(new MapConvexObject(createBox(
                new PointF(22.4f, 328f),
                new PointF(245.4f, 384.5f)
        )));

        s4 = new Space(new MapConvexObject(createBox(
                new PointF(22.4f, 140f),
                new PointF(81.4f, 328f)
        )));

        s5 = new Space(new MapConvexObject(createBox(
                new PointF(81.4f, 179.5f),
                new PointF(245.4f, 226f)
        )));

        s6 = new Space(new MapConvexObject(createBox(
                new PointF(81.4f, 243f),
                new PointF(245.4f, 290.5f)
        )));

        NavMeshBuilder.connectSpaces(s1, s4);
        NavMeshBuilder.connectSpaces(s1, s2);

        NavMeshBuilder.connectSpaces(s4, s5);
        NavMeshBuilder.connectSpaces(s4, s6);

        NavMeshBuilder.connectSpaces(s3, s4);
        NavMeshBuilder.connectSpaces(s3, s2);

        NavMeshBuilder.connectSpaces(s2, s5);
        NavMeshBuilder.connectSpaces(s2, s6);
    }

    //endregion

    /**
     * If createAxiesBoxSpaces is called, these are used
     */
    private Space space1;
    private Space space2;

    /**
     * If createComplexStrucutre is called, these are used
     */
    private Space s1;
    private Space s2;
    private Space s3;
    private Space s4;
    private Space s5;
    private Space s6;

    @Test
    public void can_link_axisbox_spaces() {
        createAxisBoxSpaces();
        NavMeshBuilder.connectSpaces(space1, space2);
        Assert.assertEquals(1, space1.getEdges().size());
        Assert.assertEquals(1, space2.getEdges().size());
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

    @Test
    public void edges_are_same_reference() {
        createAxisBoxSpaces();
        NavMeshBuilder.connectSpaces(space1, space2);
        Edge edge1 = space1.getNeighbourEdge(space2);
        Edge edge2 = space2.getNeighbourEdge(space1);
        Assert.assertTrue(edge1 == edge2);
    }

    @Test
    public void edges_reference_all_neighbouring_edges_on_connect() {
        createComplexStructure();
        Edge edgeToTest = s1.getNeighbourEdge(s4);

        //I expect this edge to have references to edge s4s5, s4s6, s4s3 and s1s2
        Assert.assertTrue(edgeToTest.getDiscoverables().contains(s4.getNeighbourEdge(s5)));
        Assert.assertTrue(edgeToTest.getDiscoverables().contains(s4.getNeighbourEdge(s6)));
        Assert.assertTrue(edgeToTest.getDiscoverables().contains(s4.getNeighbourEdge(s3)));
        Assert.assertTrue(edgeToTest.getDiscoverables().contains(s1.getNeighbourEdge(s2)));
    }
}
