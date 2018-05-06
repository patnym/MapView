package com.onlylemi.mapview.library.utils.collision;

import com.onlylemi.mapview.library.utils.math.Line;

import java.util.List;

/**
 * Created by patnym on 2018-05-06.
 */

public abstract class BaseCollisionMesh extends BaseCollision {

    protected List<Line> collisionLines;

    //// TODO: 2018-05-07 This carries an obvious bug where as if the boxes are fully intersecting
    ///                   but one line is ontop on the other would return a valid line
    public Line findCommonEdge(BaseCollisionMesh collision) {
        Line commonLine = null;
        for (Line myLine : collisionLines) {
            for (Line theirLine : collision.collisionLines) {
                commonLine = myLine.findCommonEdge(theirLine);
                if(commonLine != null) {
                    return commonLine;
                }
            }
        }
        return null;
    }
}
