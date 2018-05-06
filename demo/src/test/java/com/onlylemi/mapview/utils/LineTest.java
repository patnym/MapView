package com.onlylemi.mapview.utils;

import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.math.Line;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.Collection;

import static com.onlylemi.mapview.TestHelper.line;
import static com.onlylemi.mapview.TestHelper.point;
import static com.onlylemi.mapview.TestHelper.testPointsAreLine;

/**
 * Created by patnym on 2018-05-06.
 */

@RunWith(RobolectricTestRunner.class)
public class LineTest {

    //region Data

    private Collection<Object[]> pointOnLineTestData() {
        Object[][] obj = new Object[][] {
            new Object[] {
                line(point(0, 0), point(2, 0)),
                point(1, 0),
                true
            },
            new Object[] {
                line(point(0, 0), point(2, 2)),
                point(1, 1),
                true
            },
            new Object[] {
                line(point(0, 1), point(0, -3)),
                point(0, -1),
                true
            },
            new Object[] {
                line(point(-1, 0), point(3, 0)),
                point(1, 1),
                false
            },
            new Object[] {
                line(point(0, 0), point(2, 0)),
                point(0, 0),
                true
            }
        };
        return Arrays.asList(obj);
    }

    private Collection<Object[]> lineOnLineTestData() {
        Object[][] obj = new Object[][] {
            new Object[] {
                line(point(0, 1), point(0, -1)),
                line(point(0, 1), point(0, -1)),
                point(0, 1),
                point(0, -1),
                true
            },
            new Object[] {
                line(point(0, 1), point(0, -1)),
                line(point(0, 1), point(0, 2)),
                null,
                null,
                false
            },
            new Object[] {
                line(point(0, 0), point(1, 1)),
                line(point(0.5f, 0.5f), point(2, 2)),
                point(0.5f, 0.5f),
                point(1, 1),
                true
            },
            new Object[] {
                line(point(1, 0), point(2, 0)),
                line(point(0, 0), point(4, 0)),
                point(1, 0),
                point(2, 0),
                true
            },
            new Object[] {
                line(point(0, 0), point(2, 2)),
                line(point(0, 2), point(2, 0)),
                null,
                null,
                false
            }
        };
        return Arrays.asList(obj);
    }


    //endregion

//    @Test
//    public void identical_lines_share_common_line() {
//perpendicular_line_is_not_common
//    }

    @Test
    public void is_points_on_line() {
        Collection<Object[]> testData = pointOnLineTestData();
        for (Object[] obj : testData) {
            test_point_on_line((Line) obj[0],
                    (PointF) obj[1], (boolean) obj[2]);
        }
    }

    public void test_point_on_line(Line line, PointF point, boolean expected) {
        Assert.assertTrue(line.isPointInside(point) == expected);
    }

    @Test
    public void identical_lines_share_common_line() {
        Collection<Object[]> testData = lineOnLineTestData();
        for (Object[] data : testData) {
            Line common = ((Line) data[0]).findCommonEdge((Line) data[1]);
            if((boolean) data[4]) {
                testPointsAreLine(common, (PointF) data[2],
                        (PointF) data[3]);
            } else {
                Assert.assertNull(common);
            }
        }
    }

    @Test
    public void can_get_midpoint() {
        Line line = line(point(0, 0), point(2, 2));
        Assert.assertEquals(point(1, 1), line.getMidPoint());
    }

 }
