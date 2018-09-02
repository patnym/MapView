package com.onlylemi.mapview.library.navigation;

import java.util.ArrayList;

/**
 * Created by patnym on 2018-05-06.
 */

public class NavMeshBuilder {

    public static void connectSpaces(Space space1, Space space2) {
        Edge edge = space1.findCommonEdge(space2);

        if(edge == null) {
            throw new RuntimeException("Could not find a common edge");
        }

        ArrayList<Edge> edges = new ArrayList<>();
        edges.addAll(space1.getEdges());
        edges.addAll(space2.getEdges());

        for (Edge e : edges) {
            e.addNeighbour(edge);
        }
        edge.setNeighbours(edges);
        space1.addNeighbour(space2, edge);
        space2.addNeighbour(space1, edge);
    }

}
