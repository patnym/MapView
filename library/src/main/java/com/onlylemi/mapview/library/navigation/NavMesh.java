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

    //public List<PointF> findPath()

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
