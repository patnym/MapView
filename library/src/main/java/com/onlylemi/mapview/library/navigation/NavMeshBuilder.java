package com.onlylemi.mapview.library.navigation;

/**
 * Created by patnym on 2018-05-06.
 */

public class NavMeshBuilder {

    public static void connectSpaces(Space space1, Space space2) {
        Edge edge = space1.findCommonEdge(space2);
        space1.addNeighbour(space2, edge);
        space2.addNeighbour(space1, (Edge) edge.clone());
    }

}
