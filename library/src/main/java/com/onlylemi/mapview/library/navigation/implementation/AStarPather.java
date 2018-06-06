package com.onlylemi.mapview.library.navigation.implementation;

import com.onlylemi.mapview.library.navigation.IPather;
import com.onlylemi.mapview.library.navigation.Space;

import java.util.List;

/**
 * Created by patnym on 2018-05-27.
 */

public class AStarPather implements IPather{
    protected List<Space> spaceStruct;


    //region getset

    @Override
    public void setSpaceStruct(List<Space> spaceStruct) {
        this.spaceStruct = spaceStruct;
    }

    public List<Space> getSpaceStruct() {
        return spaceStruct;
    }

    //endregion
}
