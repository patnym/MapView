package com.onlylemi.mapview.navigation;

import android.graphics.PointF;

import com.onlylemi.mapview.library.navigation.Edge;
import com.onlylemi.mapview.library.navigation.IPather;
import com.onlylemi.mapview.library.navigation.NavMesh;
import com.onlylemi.mapview.library.navigation.NavMeshBuilder;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.navigation.implementation.AStarPather;
import com.onlylemi.mapview.library.utils.collision.MapAxisBox;

import junit.framework.Assert;

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

    //region Help methods

    public NavMesh createSimpleNavMesh() {
        List<Space> navMeshStruct = createSimpleSpaceStruct();
        return new NavMesh(navMeshStruct);
    }

    //endregion

    //region Data

    public List<Space> createSimpleSpaceStruct() {
        List<Space> spaceStruct = new ArrayList<>();
        spaceStruct.add(new Space(new MapAxisBox(
            point(0, 0), point(3f, 14f)
        )));

        spaceStruct.add(new Space(new MapAxisBox(
            point(3,0), point(7, 2)
        )));

        spaceStruct.add(new Space(new MapAxisBox(
                point(7, 0), point(10, 14)
        )));

        spaceStruct.add(new Space(new MapAxisBox(
                point(3, 6), point(7, 9)
        )));

        spaceStruct.add(new Space(new MapAxisBox(
                point(3, 12), point(7, 14)
        )));

        for(int i = 0; i < spaceStruct.size(); i++) {
            for(int y = i+1; y < spaceStruct.size(); y++) {
                if(spaceStruct.get(i).findCommonEdge(
                        spaceStruct.get(y)) != null) {
                    NavMeshBuilder.connectSpaces(spaceStruct.get(i),
                            spaceStruct.get(y));
                }
            }
        }

        return spaceStruct;
    }

    //endregion

    @Test
    public void can_create_navmesh() {
        NavMesh navMesh = new NavMesh();
        Assert.assertNotNull(navMesh);
    }

    @Test
    public void can_add_struct_to_navmesh() {
        List<Space> navMeshStruct = createSimpleSpaceStruct();
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
        List<Space> spaceStruct = createSimpleSpaceStruct();
        NavMesh navMesh = new NavMesh(spaceStruct, pather);
        Assert.assertNotNull(navMesh);
    }

    @Test
    public void navmesh_adds_spacestruct_to_ipather() {
        AStarPather pather = new AStarPather();
        List<Space> spaceStruct = createSimpleSpaceStruct();
        new NavMesh(spaceStruct, pather);
        Assert.assertNotNull(pather.getSpaceStruct());
    }

    @Test
    public void navmesh_add_spacestruct_to_ipather_from_setter() {
        AStarPather pather = new AStarPather();
        List<Space> spaceStruct = createSimpleSpaceStruct();
        NavMesh navMesh = new NavMesh(spaceStruct);
        navMesh.setPather(pather);
        Assert.assertNotNull(pather.getSpaceStruct());
    }
}
