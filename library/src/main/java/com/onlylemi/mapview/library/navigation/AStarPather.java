package com.onlylemi.mapview.library.navigation;

import android.graphics.PointF;
import android.util.Log;

import com.onlylemi.mapview.library.navigation.IPather;
import com.onlylemi.mapview.library.navigation.Space;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by patnym on 2018-05-27.
 */

public class AStarPather implements IPather{
    private final static String TAG = "AStarPather";

    protected List<Space> spaceStruct;
    private HashMap<IDiscoverable, IDiscoverable> parentMap;
    private Set<IDiscoverable> discoverablesToReset;
    private int laps;

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
        parentMap = new HashMap<>();
        discoverablesToReset = new HashSet();
        long time = System.nanoTime();
        laps = 0;

        DiscoverableSet unvisitedNodes = new DiscoverableSet();
        IDiscoverable currentNode = source;
        currentNode.setCost(0.0f);
        currentNode.setDiscovered();

        do {
            long dTime = System.nanoTime();
            evaluateNeighbours(currentNode, unvisitedNodes, target);
            Log.e(TAG, "Evaluated neighbours in: " + ((System.nanoTime() - dTime) / 1000000.0f) + " miliseconds");

            currentNode = unvisitedNodes.pop();
            if(currentNode != null) {
                currentNode.setDiscovered();

                //Check if we are looking at an end node
                if (currentNode == target) {
                    Log.e(TAG, "Found path in: " + ((System.nanoTime() - time) / 1000000.0f) + " miliseconds");
                    List<PointF> pathToAdd = new ArrayList<>();
                    while (parentMap.containsKey(currentNode)) {
                        pathToAdd.add(0, currentNode.getPosition());
                        currentNode = parentMap.get(currentNode);
                    }
                    reset();
                    Log.d(TAG, "Found path in " + laps + " laps");
                    return new PathInfo(pathToAdd);
                }
            }
        } while (currentNode != null);
        reset();
        return new PathInfo(new ArrayList<PointF>());
    }

    private void reset() {
        for (IDiscoverable d : discoverablesToReset) {
            d.reset();
        }
    }

    /**
     * Check and evaluate all neighbours
     * @return Neighbour node if on goal rail otherwise null
     */
    private void evaluateNeighbours(IDiscoverable currentEvaluatingDiscoverable, DiscoverableSet unvisitedNodes, IDiscoverable destination) {
        int dLaps = 0;
        int discoveredLaps = 0;
        for (IDiscoverable neighbour : currentEvaluatingDiscoverable.getDiscoverables()) {
            //If this neighbour has already been visisted continue to next
            if (!neighbour.isDiscovered()) {

                //Cost of moving from this neighbour to the node we are currently looking at
                float cost = neighbour.getDiscoverableCost(currentEvaluatingDiscoverable) +
                        currentEvaluatingDiscoverable.getCost();

                //Astar, we add the cost of moving from the current node to the destination
                cost += destination.getDiscoverableCost(currentEvaluatingDiscoverable);

                if(cost <  neighbour.getCost()) {
                    neighbour.setCost(cost);
                    parentMap.put(neighbour, currentEvaluatingDiscoverable);
                }
                discoverablesToReset.add(neighbour);
                unvisitedNodes.add(neighbour);
                discoveredLaps++;
            }
            dLaps++;
        }
        Log.d(TAG, "Finished evaluating neighbours in " + dLaps + " laps, where " + discoveredLaps +
            " were discovered laps");
        laps += dLaps;
    }
}
