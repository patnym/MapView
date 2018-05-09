package com.onlylemi.mapview.collision;

import android.graphics.PointF;

import com.onlylemi.mapview.library.utils.collision.MapConvexObject;
import com.onlylemi.mapview.library.utils.math.Line;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static com.onlylemi.mapview.TestHelper.point;

/**
 * Created by patnym on 2018-05-06.
 */

@RunWith(RobolectricTestRunner.class)
public class MapConvexObjectTest {

    //region Help Methods

    private void verifyPointsOutside(PointF[] points, MapConvexObject cObject) {
        for (PointF point : points) {
            Assert.assertFalse(cObject.isPointInside(point));
        }
    }

    private void verifyPointsInside(PointF[] points, MapConvexObject cObject) {
        for (PointF point : points) {
            Assert.assertTrue(cObject.isPointInside(point));
        }
    }

    //endregion

    //region TestData

    public void offsetPoints(PointF[] points, PointF offset) {
        for (PointF point : points) {
            point.x += offset.x;
            point.y += offset.y;
        }
    }

    public List<Line> collisionShapeFromPoints(List<PointF> shape) {
        List<Line> collisionShape = new ArrayList();
        PointF prev = shape.get(shape.size() - 1);
        for(int i = 0; i < shape.size(); i++) {
            PointF cur = shape.get(i);
            collisionShape.add(new Line(
                    new PointF(
                            prev.x,
                            prev.y
                    ),  new PointF(
                    cur.x,
                    cur.y
            )));
            prev = cur;
        }
        return collisionShape;
    }

    public List<PointF> triangleShape() {
        List<PointF> returnObject = new ArrayList();
        returnObject.add(point(0, 0));
        returnObject.add(point(0, 1));
        returnObject.add(point(1, 0));
        return returnObject;
    }

    public List<PointF> hexagonShape() {
        List<PointF> returnObject = new ArrayList();
        returnObject.add(point(0, 1));
        returnObject.add(point(0.75f, 0.75f));
        returnObject.add(point(1, 0));
        returnObject.add(point(0.75f, -0.75f));
        returnObject.add(point(0, -1));
        returnObject.add(point(-0.75f, -0.75f));
        returnObject.add(point(-1.0f, 0.0f));
        returnObject.add(point(-0.75f, 0.75f));
        return returnObject;
    }

    public List<PointF> nonConvexShape() {
        List<PointF> nonConvexShape = new ArrayList();
        nonConvexShape.add(point(0, 0));
        nonConvexShape.add(point(0, 1));
        nonConvexShape.add(point(1, 1));
        nonConvexShape.add(point(1, 0));
        nonConvexShape.add(point(2, 0));
        nonConvexShape.add(point(2, -1));
        nonConvexShape.add(point(0, -1));
        return nonConvexShape;
    }

    public PointF[] triangle_inside_points() {
        return new PointF[]{
                new PointF(0, 0),
                new PointF(0.4f, 0.4f),
                new PointF(0.1f, 0.1f),
                new PointF(0.5f, 0.5f),
                new PointF(0, 1)
        };
    }

    public PointF[] triangle_outside_points() {
        return new PointF[]{
                new PointF(-1, 0),
                new PointF(1, 1),
                new PointF(0.6f, 0.6f),
                new PointF(1.1f, 0.0f),
                new PointF(0, 1.1f),
                new PointF(-0.5f, -0.5f)
        };
    }

    public PointF[] hexagon_inside_points() {
        return new PointF[]{
                new PointF(0, 0),
                new PointF(0.4f, 0.4f),
                new PointF(0.1f, 0.1f),
                new PointF(0.5f, 0.5f),
                new PointF(0, 1)
        };
    }

    public PointF[] hexagon_outside_points() {
        return new PointF[] {
                new PointF(50, 30),
                new PointF(1.1f, 1),
                new PointF(-0.99f, 0.75f),
                new PointF(-10, 0.0f),
                new PointF(-0.5f, -1.1f),
                new PointF(5324, 21312)
        };
    }

    //endregion

    @Test
    public void creation_is_correct() {
        PointF position = new PointF(3, 3);
        MapConvexObject cObject = new MapConvexObject(position, triangleShape());
        Assert.assertNotNull(cObject);
    }

    @Test
    public void points_are_inside_triangle() {
        PointF position = point(0, 0);
        MapConvexObject cObject = new MapConvexObject(position, triangleShape());
        verifyPointsInside(triangle_inside_points(), cObject);
    }

    @Test
    public void points_are_outside_triangle() {
        PointF position = point(0, 0);
        MapConvexObject cObject = new MapConvexObject(position, triangleShape());
        verifyPointsOutside(triangle_outside_points(), cObject);
    }

    @Test
    public void points_are_inside_hexagon() {
        PointF position = point(0, 0);
        MapConvexObject cObject = new MapConvexObject(position, hexagonShape());
        verifyPointsInside(hexagon_inside_points(), cObject);
    }

    @Test
    public void points_are_outside_hexagon() {
        PointF position = point(0, 0);
        MapConvexObject cObject = new MapConvexObject(position, hexagonShape());
        verifyPointsOutside(hexagon_outside_points(), cObject);
    }

    @Test
    public void points_are_inside_triangle_after_move() {
        PointF position = point(500, 300);
        PointF[] points = triangle_inside_points();
        offsetPoints(points, position);
        MapConvexObject cObject = new MapConvexObject(position, triangleShape());
        verifyPointsInside(points, cObject);
    }

    @Test
    public void points_are_outside_triangle_after_move() {
        PointF position = point(-200, 300);
        PointF[] points = triangle_outside_points();
        offsetPoints(points, position);
        MapConvexObject convexObject = new MapConvexObject(position, triangleShape());
        verifyPointsOutside(points, convexObject);
    }

    @Test
    public void points_are_inside_hexagon_after_move() {
        PointF position = point(10, 20);
        PointF[] points = hexagon_inside_points();
        offsetPoints(points, position);
        MapConvexObject convexObject = new MapConvexObject(position, hexagonShape());
        verifyPointsInside(points, convexObject);
    }

    @Test
    public void points_are_outside_hexagon_after_move() {
        PointF position = point(42342, 3425325);
        PointF[] points = hexagon_outside_points();
        offsetPoints(points, position);
        MapConvexObject convexObject = new MapConvexObject(position, hexagonShape());
        verifyPointsOutside(points, convexObject);
    }

    @Test
    public void can_verify_if_object_is_convex() {
        Assert.assertTrue(MapConvexObject.isShapeConvex(collisionShapeFromPoints(hexagonShape())));
    }

    @Test
    public void can_verify_if_object_is_not_convex() {
        Assert.assertFalse(MapConvexObject.isShapeConvex(collisionShapeFromPoints(nonConvexShape())));
    }

    @Test
    public void object_is_convex() {
        MapConvexObject cObject = new MapConvexObject(point(0, 0), hexagonShape());
        Assert.assertNotNull(cObject);
    }

    @Test(expected = IllegalArgumentException.class)
    public void object_throws_if_not_convex() {
        new MapConvexObject(point(0, 0), nonConvexShape());
    }
}
