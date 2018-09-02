package com.onlylemi.mapview.library.navigation;

import android.graphics.PointF;

import java.util.Collection;

/**
 * Created by patnym on 2018-09-02.
 */

public interface IDiscoverable {

    /**
     * Returns all neighbouring discoverables
     * @return
     */
    Collection<IDiscoverable> getDiscoverables();

    /**
     * The cost of moving from this discoverable to the input discoverable
     * @return cost
     */
    float getDiscoverableCost(IDiscoverable discoverable);

    /**
     * Returns the position of this discoverable
     * @return
     */
    PointF getPosition();

    /**
     * Adds a temporary discoverable to the neighbour list.
     * This discoverable WILL get reset between each pathing run
     * @param discoverable
     */
    void addDisposableDiscoverable(IDiscoverable discoverable);

    /**
     * Set this node as discovered, which means we will not examine it again
     */
    void setDiscovered();

    /**
     * Returns a boolean that indicates if this node has been discovered
     * @return
     */
    boolean isDiscovered();

    /**
     * Resets the discoverable to the default state.
     * Must clear any temporary set variables used during pathing
     */
    void reset();
}
