package com.onlylemi.mapview.library.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.MapViewRenderer;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.layer.handlers.BaseLayerHandler;
import com.onlylemi.mapview.library.messages.ICommand;
import com.onlylemi.mapview.library.messages.MessageDefenitions;
import com.onlylemi.mapview.library.navigation.NavMesh;
import com.onlylemi.mapview.library.navigation.PathInfo;
import com.onlylemi.mapview.library.navigation.Space;

import java.util.List;

/**
 * LocationLayer
 *
 * @author: onlylemi
 */
public class LocationLayer extends MapBaseLayer {
    private static final String TAG ="LocationLayer";

    //user
    private LocationUser user;

    private Paint locationPaint;
    public List<Space> test;
    private NavMesh navMesh;

    public void setNavMesh(NavMesh navMesh) {
        test = navMesh.getSpaceStruct();
        this.navMesh = navMesh;
    }

    //Outside usage use this handler to interact with the user
    private UserHandler handler;

    public LocationLayer(MapView mapView, LocationUser user) {
        super(mapView);
        this.user = user;
        initLayer();
    }

    private void initLayer() {
        locationPaint = new Paint();
        locationPaint.setAntiAlias(true);
        locationPaint.setFilterBitmap(true);
        locationPaint.setDither(true);
        locationPaint.setStrokeWidth(10.0f);
        locationPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onTouch(float x, float y)
    {

    }
    @Override
    public boolean update(Matrix currentMatrix, long deltaTime) {
        hasChanged = user.update(currentMatrix, deltaTime);


        return hasChanged;
    }
    PathInfo p;
    Path path;
    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, long deltaTime) {
        if (isVisible) {
            user.draw(canvas, locationPaint);
//            p = navMesh.findPath(user.getPosition(), new PointF(25f, 92f));
//            path = new Path();
//            path.moveTo(user.position.x, user.position.y);
//            for (int i = 0; i < p.getPath().size(); i++) {
//                PointF pos = p.getPath().get(i);
//                path.lineTo(pos.x, pos.y);
//            }
//            //path.transform(currentMatrix);
//            if(path != null) {
//                Path tPath = new Path();
//                path.transform(currentMatrix, tPath);
//                canvas.drawPath(tPath, locationPaint);
//            }
        }
        super.draw(canvas, currentMatrix, currentZoom, deltaTime);
    }

    @Override
    public void debugDraw(Canvas canvas, Matrix currentMatrix) {
        if(isVisible) {
            user.debugDraw(currentMatrix, canvas);
        }
    }

    /**
     * This gets called from the mapview once rendering is initiated. DO ATTEMPT TO CREATE YOUR OWN
     * @param renderThread
     */
    @Override
    public void createHandler(@NonNull MapViewRenderer renderThread) {
        this.handler = new UserHandler(renderThread.getHandler(), this, user);
    }

    public UserHandler getUserHandler() {
        if(this.handler == null) {
            throw new RuntimeException("Can't get handler until the rendering has started" +
                    ". Must be called inside or after the \" On rendering started\" has occurred");
        }
        return this.handler;
    }

    public class UserHandler extends BaseLayerHandler {

        private LocationUser user;

        public UserHandler(Handler renderHandler, LocationLayer layer, LocationUser user) {
            super(renderHandler, layer);
            this.user = user;
        }

        public void moveUser(final List<PointF> destinationsQueue,final float duration,final boolean appendToOldList) {
            runOnRenderThread(new ICommand() {
                @Override
                public void execute() {
                    user.move(destinationsQueue, duration, appendToOldList);
                }
            });
        }

        public void moveUser(final PointF position,final float duration) {
            runOnRenderThread(new ICommand() {
                @Override
                public void execute() {
                    user.move(position, duration);
                }
            });
        }

        public void rotateUser(final PointF direction, final float duration) {
            runOnRenderThread(new ICommand() {
                @Override
                public void execute() {
                    user.setLookAt(direction, duration);
                }
            });
        }
    }
}
