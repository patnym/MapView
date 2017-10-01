package com.onlylemi.mapview.library.camera.modes.implementation;

import android.graphics.Matrix;
import android.graphics.PointF;

import com.onlylemi.mapview.library.camera.MapViewCamera;
import com.onlylemi.mapview.library.camera.modes.ICameraState;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.utils.MapMath;

/**
 * Copyright (C) 10/1/17 nyman (patnym) - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the MIT license.
 * <p>
 * You should have received a copy of the MIT license with
 * this file. If not, please write to: patrik911217@gmail.com
 * <p>
 * If you wanna contribute add your name below and please
 * assign yourself a cool nickname.
 */

public class ContainUserCameraState implements ICameraState {

    private float defualtContainZoom;

    private float viewPortWidth;
    private float viewPortHeight;

    private float mapImageWidth;
    private float mapImageHeight;

    private MapViewCamera camera;

    private LocationUser user;

    public ContainUserCameraState(MapViewCamera camera) {
        this.viewPortWidth = camera.getViewPortWidth();
        this.viewPortHeight = camera.getViewPortHeight();
        this.user = camera.getUser();
        this.camera = camera;
        this.mapImageWidth = camera.getMapView().getMapWidth();
        this.mapImageHeight = camera.getMapView().getMapHeight();
    }

    @Override
    public void init(MapViewCamera camera) {
        calculateOnContainUserZoom();

        //Should calculate translation and zoom speeds here
    }

    @Override
    public void update(Matrix m, long deltaTimeNano) {
        float zoom = defualtContainZoom;
        float d = zoom - camera.getCurrentZoom();
        int sign = (int) (d / Math.abs(d));
        d = d * sign; //Absolute distance
        float zVelocity = camera.getMapView().getMapModeOptions().zoomPerNanoSecond * sign * deltaTimeNano;
        d -= Math.abs(zVelocity);

        //move towards target using velocity
        if (d <= 0.0f) {
            camera.setCurrentZoom(zoom);
        } else {
            camera.setCurrentZoom(camera.getCurrentZoom() + zVelocity);
        }
        //This is a copy of follow user, this fucking shit needs to get cleaned up soon!
        //My point on the view coordinate system
        PointF dst = new PointF();
        dst.set(user.getPosition());
        float[] b = {dst.x, dst.y};
        m.mapPoints(b);

        //My point in view coords
        dst.x = b[0];
        dst.y = b[1];

        //Mid point of the view coordinate system
        PointF trueMid = new PointF(viewPortWidth / 2, viewPortHeight / 2);

        //Direction - NOTE we are going from the mid towards our point because graphics yo
        PointF desti = new PointF(trueMid.x - b[0], trueMid.y - b[1]);

        //Now check if this would put the camera out of bounds
        //// TODO: 2017-09-19 (Nyman): Optimize this shit, do we really need to trasnform the point to find out if its outside the bounds?
        //We actually might since its fucking annoying when we zoom, could this perhaps be done outside world space? Dont bother fix this if it aint laggin!
        Matrix translationMatrix = new Matrix(m);
        translationMatrix.postTranslate(desti.x, desti.y);
        PointF cameraPosition = MapMath.transformPoint(translationMatrix, new PointF(0,0));
        PointF cameraBotRight = MapMath.transformPoint(translationMatrix, new PointF(mapImageWidth, mapImageHeight));
        //Check X axis
        if (cameraPosition.x > 0.0f) { //Left side
            PointF currentCameraTopLeft = MapMath.transformPoint(m, new PointF(0, 0));
            desti.x = 0 - currentCameraTopLeft.x;
        } else if(cameraBotRight.x < viewPortWidth) { //Right side
            PointF currentCameraBotRight = MapMath.transformPoint(m, new PointF(mapImageWidth, 0));
            desti.x =  viewPortWidth - currentCameraBotRight.x;
        }
        //Check Y axis
        if (cameraPosition.y > 0.0f) {
            PointF currentCameraTopLeft = MapMath.transformPoint(m, new PointF(0, 0));
            desti.y = 0 - currentCameraTopLeft.y;
        } else if(cameraBotRight.y < viewPortHeight) {
            PointF currentCameraBotRight = MapMath.transformPoint(m, new PointF(0, mapImageHeight));
            desti.y = viewPortHeight - currentCameraBotRight.y;
        }

        //This is also the distance from our point to the middle
        float distance = desti.length();


        PointF dir = new PointF();
        dir.x = desti.x / distance;
        dir.y = desti.y / distance;
        distance -= camera.getMapView().getMapModeOptions().translationsPixelsPerNanoSecond * deltaTimeNano;

        if (distance <= 0.0f) {
            m.postTranslate(desti.x, desti.y);
        } else {
            m.postTranslate(dir.x * camera.getMapView().getMapModeOptions().translationsPixelsPerNanoSecond * deltaTimeNano, dir.y * camera.getMapView().getMapModeOptions().translationsPixelsPerNanoSecond * deltaTimeNano);
        }
    }

    @Override
    public void onViewPortChanged(float width, float height, MapViewCamera camera) {
        viewPortWidth = width;
        viewPortHeight = height;
        this.camera = camera;
    }

    public void calculateOnContainUserZoom() {
        //Calculate ratios and use the highest
        float widthRatio = viewPortWidth / mapImageWidth;
        float heightRatio = viewPortHeight / mapImageHeight;

        if(widthRatio > heightRatio) {
            defualtContainZoom = widthRatio;
        } else {
            defualtContainZoom = heightRatio;
        }
    }
}
