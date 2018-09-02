package com.onlylemi.mapview.library.navigation;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;

import com.onlylemi.mapview.library.exceptions.InvalidNeighbourException;
import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.math.Line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by patnym on 2018-05-06.
 */

public class Edge implements IDiscoverable {

    protected Collection<IDiscoverable> neighbours = new ArrayList<>();
    protected Collection<IDiscoverable> tempNeighbours = new ArrayList<>();
    private Collection<IDiscoverable> currentNeighbours = new ArrayList<>();
    protected Line line;

    public Edge() {
    }

    public Edge(PointF start, PointF stop) {
        this.line = new Line(start, stop);
    }

    public Edge(Line line) {
        this.line = line;
    }

    public PointF getMidpoint() {
        return line.getMidPoint();
    }

    public void debugDraw(Matrix m, Canvas canvas) {
        line.debugDraw(m, canvas);
    }

    public void setNeighbours(Collection<? extends IDiscoverable> neighbours) {
        this.neighbours = (Collection<IDiscoverable>) neighbours;
    }

    public void addNeighbour(IDiscoverable neighbour) {
        if(neighbours.contains(neighbour)) {
            throw new InvalidNeighbourException("Cannot add the same neighbour twice");
        }
        neighbours.add(neighbour);
    }

    @Override
    public Collection<IDiscoverable> getDiscoverables() {
        return currentNeighbours;
    }

    @Override
    public float getDiscoverableCost(IDiscoverable discoverable) {
        return MapMath.getDistanceBetweenTwoPoints(getPosition(), discoverable.getPosition());
    }

    @Override
    public PointF getPosition() {
        return getMidpoint();
    }

    @Override
    public void addDisposableDiscoverable(IDiscoverable discoverable) {
        tempNeighbours.add(discoverable);

        currentNeighbours.clear();
        currentNeighbours.addAll(neighbours);
        currentNeighbours.addAll(tempNeighbours);
    }
}
