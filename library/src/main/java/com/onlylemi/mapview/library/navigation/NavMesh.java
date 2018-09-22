package com.onlylemi.mapview.library.navigation;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by patnym on 2018-05-27.
 */

public class NavMesh {

    protected List<Space> spaceStruct;
    protected IPather pather;

    public NavMesh() {

    }

    public NavMesh(List<Space> spaceStruct) {
        this.spaceStruct = spaceStruct;
    }

    public NavMesh(List<Space> spaceStruct, @NonNull IPather pather) {
        this.spaceStruct = spaceStruct;
        this.pather = pather;
        this.pather.setSpaceStruct(spaceStruct);
    }

    public Space findSpaceFromPoint(PointF position) {
        for (Space space : spaceStruct) {
            if(space.isPointInside(position)) {
                return space;
            }
        }
        return null;
    }

    public PathInfo findPath(PointF start, PointF destination) {

        Space startSpace = findSpaceFromPoint(start);
        Space destinationSpace = findSpaceFromPoint(destination);

        if(startSpace == null || destinationSpace == null) {
            return PathInfo.NoPathFound();
        }

        if(startSpace == destinationSpace) {
            return PathInfo.SinglePathTo(destination);
        }

        IDiscoverable startNode = createDiscoverableFromPosition(start, startSpace);
        IDiscoverable stopNode = createDiscoverableFromPosition(destination, destinationSpace);
        return pather.findPath(startNode, stopNode);
    }

    private IDiscoverable createDiscoverableFromPosition(PointF position, Space positionInSpace) {
        //Create edge which has midpoint at point
        IDiscoverable discoverable = new Edge(new PointF(position.x - 1, position.y),
                new PointF(position.x + 1, position.y));

        for(Edge edge : positionInSpace.getEdges()) {
            edge.addDisposableDiscoverable(discoverable);
            discoverable.addDisposableDiscoverable(edge);
        }

        return discoverable;
    }

    //region getset

    public List<Space> getSpaceStruct() {
        return spaceStruct;
    }

    public void setSpaceStruct(List<Space> spaceStruct) {
        this.spaceStruct = spaceStruct;
    }

    public IPather getPather() {
        return pather;
    }

    public void setPather(IPather pather) {
        this.pather = pather;
        this.pather.setSpaceStruct(spaceStruct);
    }

    //endregion
}
