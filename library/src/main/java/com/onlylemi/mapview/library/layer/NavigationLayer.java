package com.onlylemi.mapview.library.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Log;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.camera.MapViewCamera;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.navigation.NavMesh;
import com.onlylemi.mapview.library.navigation.PathInfo;
import com.onlylemi.mapview.library.navigation.Space;
import com.onlylemi.mapview.library.utils.MapMath;

import java.util.List;

public class NavigationLayer extends MapBaseLayer {

    private MapViewCamera camera;
    private LocationUser user;

    private NavMesh navMesh;
    private PathInfo pathInfo;
    private PointF navigatingTo;

    private Paint paint;

    //region debug

    private List<Space> navMeshSpaces;

    //endregion

    public NavigationLayer(MapView mapView, NavMesh navMesh, LocationUser user) {
        super(mapView);
        this.navMesh = navMesh;
        this.user = user;
        this.pathInfo = new PathInfo();
        this.navMeshSpaces = navMesh.getSpaceStruct();
        configurePaint();
    }

    private void configurePaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(5);
        paint.setColor(Color.parseColor("#2bd869"));
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onTouch(float x, float y) {
        float[] point = renderer.getCamera().convertMapXYToScreenXY(x, y);
        navigateTo(point[0], point[1]);
    }

    @Override
    public boolean update(Matrix currentMatrix, long deltaTime) {

        //Check the user and if he's moved anything

        //For now we just generate the path we wanna walk
        if(hasDestination()) {
            long time = System.nanoTime();
            pathInfo = navMesh.findPath(user.getPosition(), navigatingTo);
            Log.d("NavigationLayer", "Ran pathing in: " + ((System.nanoTime() - time) / 1000000.0f) + " miliseconds");
            navigatingTo = null;
        }

        return pathInfo.isPathFound();
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, long deltaTime) {
        Path graphicsPath = createGraphicsPath(pathInfo);
        graphicsPath.transform(currentMatrix);
        canvas.drawPath(graphicsPath, paint);
        super.draw(canvas, currentMatrix, currentZoom, deltaTime);
    }

    @Override
    public void debugDraw(Canvas canvas, Matrix currentMatrix) {
        for(int i = 0; i < navMeshSpaces.size(); i++) {
            navMeshSpaces.get(i).debugDraw(currentMatrix, canvas);
        }
    }

    private Path createGraphicsPath(PathInfo pathInfo) {
        Path graphicsPath = new Path();
        PointF start = user.getPosition();
        graphicsPath.moveTo(start.x, start.y);
        List<PointF> path = pathInfo.getPath();
        for(int i = 0; i < path.size(); i++) {
            PointF currentPoint = path.get(i);
            graphicsPath.lineTo(currentPoint.x, currentPoint.y);
        }
        return graphicsPath;
    }

    private boolean hasDestination() {
        return navigatingTo != null;
    }

    public void navigateTo(PointF destination) {
        navigatingTo = destination;
    }

    public void navigateTo(float x, float y) {
        navigatingTo = new PointF(x, y);
    }
}
