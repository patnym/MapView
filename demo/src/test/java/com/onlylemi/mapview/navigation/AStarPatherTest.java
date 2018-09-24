package com.onlylemi.mapview.navigation;

import com.onlylemi.mapview.library.navigation.AStarPather;
import com.onlylemi.mapview.library.navigation.NavMesh;
import com.onlylemi.mapview.library.navigation.PathInfo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.onlylemi.mapview.TestHelper.point;

@RunWith(RobolectricTestRunner.class)
public class AStarPatherTest {

    private NavMesh navMesh;

    /**
     * @see {@link SpaceStructureHelper#createSimpleSpaceStruct()} for reference what this structure looks like
     * I have named each space from s1 to s5.
     * I will be referencing these to explain how I'm attempting to navigate in this test
     *  |----------|
     *  |  | s2 |  |
     *  |  -----|  |
     *  |  |////|  |
     *  |  |----|  |
     *  |s1| s4 |s3|
     *  |  |----|  |
     *  |  |////|  |
     *  |  |----|  |
     *  |  | s5 |  |
     *  ------------
     */
    @Before
    public void setupNavmesh() {
        navMesh = new NavMesh(
                SpaceStructureHelper.createSimpleSpaceStruct(),
                new AStarPather()
        );
    }

    @Test
    public void can_navigate_from_s1_to_s5() {
        PathInfo path = navMesh.findPath(point(1, 1), point(4, 13));
        Assert.assertEquals(point(3, 13), path.getPath().get(0));
        Assert.assertEquals(point(4, 13), path.getPath().get(1));
    }

    @Test
    public void can_navigate_from_s1_to_s3() {
        PathInfo path = navMesh.findPath(point(1, 1), point(9, 3));
        Assert.assertEquals(point(3, 1), path.getPath().get(0));
        Assert.assertEquals(point(7, 1), path.getPath().get(1));
        Assert.assertEquals(point(9, 3), path.getPath().get(2));
    }

    @Test
    public void can_navigate_twice() {
        can_navigate_from_s1_to_s5();
        can_navigate_from_s1_to_s5();
    }

    @Test
    public void can_navigate_on_same_space() {
        PathInfo path = navMesh.findPath(point(1, 1), point(2, 2));
        Assert.assertEquals(point(2, 2), path.getPath().get(0));
    }

}
