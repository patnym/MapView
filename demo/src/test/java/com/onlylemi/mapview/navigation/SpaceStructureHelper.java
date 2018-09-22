package com.onlylemi.mapview.navigation;

import com.onlylemi.mapview.library.navigation.NavMeshBuilder;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.utils.collision.MapAxisBox;

import java.util.ArrayList;
import java.util.List;

import static com.onlylemi.mapview.TestHelper.point;

public class SpaceStructureHelper {

    /**
     * This creates a space structure that looks like this
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
    public static List<Space> createSimpleSpaceStruct() {
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

}
