package com.onlylemi.mapview.navigation;

import android.graphics.PointF;

import com.onlylemi.mapview.library.navigation.IPather;
import com.onlylemi.mapview.library.navigation.NavMesh;
import com.onlylemi.mapview.library.navigation.NavMeshBuilder;
import com.onlylemi.mapview.library.navigation.PathInfo;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.navigation.AStarPather;
import com.onlylemi.mapview.library.utils.collision.MapAxisBox;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static com.onlylemi.mapview.TestHelper.point;

/**
 * Created by patnym on 2018-05-27.
 */

@RunWith(RobolectricTestRunner.class)
public class NavMeshTest {

    private List<Space> navMeshStruct;
    private NavMesh navMesh;

    @Before
    public void setupSpaceStruct() {
        navMeshStruct = SpaceStructureHelper.createSimpleSpaceStruct();
        navMesh = new NavMesh(navMeshStruct, new AStarPather());
    }

    //region Help methods

    public NavMesh createSimpleNavMesh() {
        return new NavMesh(navMeshStruct);
    }

    //endregion

    @Test
    public void can_create_navmesh() {
        NavMesh navMesh = new NavMesh();
        Assert.assertNotNull(navMesh);
    }

    @Test
    public void can_add_struct_to_navmesh() {
        NavMesh navMesh = new NavMesh(navMeshStruct);
        Assert.assertNotNull(navMesh);
    }

    @Test
    public void can_find_space_from_position() {
        NavMesh navMesh = createSimpleNavMesh();
        Space space = navMesh.findSpaceFromPoint(point(2f, 5f));
        Assert.assertTrue(space.getPosition().equals(point(0, 0)));
    }

    @Test
    public void can_find_space_from_position_2() {
        NavMesh navMesh = createSimpleNavMesh();
        Space space = navMesh.findSpaceFromPoint(point(4f, 8f));
        Assert.assertTrue(space.getPosition().equals(point(3, 6)));
    }

    @Test
    public void return_null_if_position_not_in_navmesh() {
        NavMesh navMesh = createSimpleNavMesh();
        Space space = navMesh.findSpaceFromPoint(point(4f, 4f));
        Assert.assertNull(space);
    }

    @Test
    public void can_add_path_algorithm() {
        IPather pather = new AStarPather();
        NavMesh navMesh = new NavMesh(navMeshStruct, pather);
        Assert.assertNotNull(navMesh);
    }

    @Test
    public void navmesh_adds_spacestruct_to_ipather() {
        AStarPather pather = new AStarPather();
        new NavMesh(navMeshStruct, pather);
        Assert.assertNotNull(pather.getSpaceStruct());
    }

    @Test
    public void navmesh_add_spacestruct_to_ipather_from_setter() {
        AStarPather pather = new AStarPather();
        navMesh.setPather(pather);
        Assert.assertNotNull(pather.getSpaceStruct());
    }

    @Test
    public void fails_if_start_outside_space() {
        PathInfo path = navMesh.findPath(point(-10, -10), point(2, 2));
        Assert.assertEquals(false, path.isPathFound());
    }

    @Test
    public void fails_if_destination_outside_space() {
        PathInfo path = navMesh.findPath(point(1, 1), point(500, 1200));
        Assert.assertEquals(false, path.isPathFound());
    }

    @Test
    public void fails_if_destination_and_start_outside_space() {
        PathInfo path = navMesh.findPath(point(-10, -10), point(500, 1200));
        Assert.assertEquals(false, path.isPathFound());
    }
}
