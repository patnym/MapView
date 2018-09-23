package com.onlylemi.mapview.library.navigation;

import android.graphics.PointF;
import android.util.Log;

import com.onlylemi.mapview.library.navigation.IPather;
import com.onlylemi.mapview.library.navigation.Space;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by patnym on 2018-05-27.
 */

public class AStarPather implements IPather{

    protected List<Space> spaceStruct;
    private HashMap<IDiscoverable, Float> costMap;
    private HashMap<IDiscoverable, IDiscoverable> parentMap;

    //region getset

    @Override
    public void setSpaceStruct(List<Space> spaceStruct) {
        this.spaceStruct = spaceStruct;
    }

    public List<Space> getSpaceStruct() {
        return spaceStruct;
    }

    //endregion


    @Override
    public PathInfo findPath(IDiscoverable source, IDiscoverable target) {
        costMap = new HashMap<>();
        parentMap = new HashMap<>();

        long time = System.nanoTime();

        TreeSet<IDiscoverable> unvisitedNodes = new TreeSet(new Comparator<IDiscoverable>() {
            @Override
            public int compare(IDiscoverable o, IDiscoverable t1) {
                int result = 0;
                if(o == t1) {
                    result = 0;
                }
                if(costMap.get(o) <= costMap.get(t1)) {
                    result = -1;
                } else if(costMap.get(o) > costMap.get(t1)) {
                    result = 1;
                }
                return result;
            }
        });

        IDiscoverable currentNode = source;
        costMap.put(currentNode, 0.0f);
        currentNode.setDiscovered();

        do {
            evaluateNeighbours(currentNode, unvisitedNodes, target);

            currentNode = unvisitedNodes.pollFirst();
            if(currentNode != null) {
                currentNode.setDiscovered();

                //Check if we are looking at an end node
                if (currentNode == target) {
                    Log.e("ASTAR", "Found path in: " + ((System.nanoTime() - time) / 1000000.0f) + " miliseconds");
                    List<PointF> pathToAdd = new ArrayList<>();
                    while (parentMap.containsKey(currentNode)) {
                        pathToAdd.add(0, currentNode.getPosition());
                        currentNode = parentMap.get(currentNode);
                    }
                    reset();
                    return new PathInfo(pathToAdd);
                }
            }
        } while (currentNode != null);
        reset();
        return new PathInfo(new ArrayList<PointF>());
    }

    private void reset() {
        for (IDiscoverable d : costMap.keySet()) {
            d.reset();
        }
    }

    /**
     * Check and evaluate all neighbours
     * @return Neighbour node if on goal rail otherwise null
     */
    private void evaluateNeighbours(IDiscoverable currentEvaluatingDiscoverable, Set<IDiscoverable> unvisitedNodes, IDiscoverable destination) {
        for (IDiscoverable neighbour : currentEvaluatingDiscoverable.getDiscoverables()) {
            //If this neighbour has already been visisted continue to next
            if (!neighbour.isDiscovered()) {

                //Cost of moving from this neighbour to the node we are currently looking at
                float cost = neighbour.getDiscoverableCost(currentEvaluatingDiscoverable) +
                        costMap.get(currentEvaluatingDiscoverable);

                //Astar, we add the cost of moving from the current node to the destination
                cost += destination.getDiscoverableCost(currentEvaluatingDiscoverable);

                if (costMap.containsKey(neighbour)) {
                    if (cost < costMap.get(neighbour)) {
                        costMap.put(neighbour, cost);
                        parentMap.put(neighbour, currentEvaluatingDiscoverable);
                    }
                } else {
                    costMap.put(neighbour, cost);
                    parentMap.put(neighbour, currentEvaluatingDiscoverable);
                }
                unvisitedNodes.add(neighbour);
            }
        }
    }
}
