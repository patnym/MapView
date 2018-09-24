package com.onlylemi.mapview.navigation;

import com.onlylemi.mapview.library.navigation.DiscoverableSet;
import com.onlylemi.mapview.library.navigation.Edge;
import com.onlylemi.mapview.library.navigation.IDiscoverable;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.onlylemi.mapview.TestHelper.point;

@RunWith(RobolectricTestRunner.class)
public class DiscoverableSetTest {

    private IDiscoverable testDiscoverable;
    private DiscoverableSet testSet;

    @Before
    public void SetupDiscoverable() {
        testDiscoverable = new Edge(
                point(0, 0),
                point(2, 0)
        );

        testSet = new DiscoverableSet();
    }

    @Test
    public void can_add_and_get_element() {
        testSet.add(testDiscoverable);
        Assert.assertEquals(testDiscoverable, testSet.pop());
    }

    @Test
    public void pop_when_empty_returns_null() {
        Assert.assertNull(testSet.pop());
    }

    @Test
    public void pop_removes_element() {
        testSet.add(testDiscoverable);
        Assert.assertEquals(1, testSet.size());
        testSet.pop();
        Assert.assertEquals(0, testSet.size());
    }

    @Test
    public void can_get_size() {
        testSet.add(testDiscoverable);
        Assert.assertEquals(1, testSet.size());
    }

    @Test
    public void ignores_same_object_added_twice() {
        testSet.add(testDiscoverable);
        testSet.add(testDiscoverable);
        Assert.assertEquals(1, testSet.size());
    }

    @Test
    public void set_sorted_by_lowest_cost_first() {
        Edge e1 = new Edge();
        e1.setCost(11.0f);
        Edge e2 = new Edge();
        e2.setCost(8.0f);
        testSet.add(e1);
        testSet.add(e2);
        Assert.assertEquals(8.0f, testSet.pop().getCost());
    }

}
