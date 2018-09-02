package com.onlylemi.mapview.library.navigation;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import com.onlylemi.mapview.library.exceptions.InvalidNeighbourException;
import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.collision.BaseCollisionMesh;
import com.onlylemi.mapview.library.utils.math.Line;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by patnym on 2018-05-06.
 */

public class Space {

    protected HashMap<Space, Edge> neighbours;
    protected BaseCollisionMesh shape;

    public Space(BaseCollisionMesh shape) {
        this();
        this.shape = shape;
    }

    public Space() {
        neighbours = new HashMap();
    }

    public boolean isPointInside(PointF point) {
        return shape.isPointInside(point);
    }

    public void addNeighbour(Space space, Edge edge) {
        if(neighbours.containsKey(space)) {
            throw new InvalidNeighbourException(
                    "A space cannot reference the same neighbour twice");
        }
        neighbours.put(space, edge);
    }

    public Edge findCommonEdge(Space space) {
        Line l = shape.findCommonEdge(space.shape);
        if(l == null) {
            return null;
        }
        return new Edge(l);
    }

    public Set<Space> getNeighbours() {
        return neighbours.keySet();
    }

    public Collection getEdges() {
        return neighbours.values();
    }

    public Edge getNeighbourEdge(Space space) {
        return neighbours.get(space);
    }

    public void setShape(BaseCollisionMesh shape) {
        this.shape = shape;
    }

    public PointF getPosition() {
        return shape.getPosition();
    }

    public void debugDraw(Matrix m, Canvas canvas) {
        shape.debugDraw(m, canvas);
        for (Edge edge : neighbours.values()) {
            edge.debugDraw(m, canvas);
        }
    }
}
