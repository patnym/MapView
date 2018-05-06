package com.onlylemi.mapview.collision;


import com.onlylemi.mapview.library.utils.collision.MapAxisBox;
import com.onlylemi.mapview.library.utils.math.Line;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.onlylemi.mapview.TestHelper.point;
import static com.onlylemi.mapview.TestHelper.testPointsAreLine;

/**
 * Created by patnym on 2018-05-06.
 */

@RunWith(RobolectricTestRunner.class)
public class MapAxisBoxTest {

    @Test
    public void can_find_common_edge_between_boxes() {
        MapAxisBox box1 = new MapAxisBox(point(0, 0), 2, 2);
        MapAxisBox box2 = new MapAxisBox(point(2, 0), 2, 2);
        Line line = box1.findCommonEdge(box2);
        testPointsAreLine(line, point(1, -1), point(1, 1));
    }
}
