package com.onlylemi.mapview.library.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import com.onlylemi.mapview.library.MapView;

/**
 * This class is used if you generate your map using whatever position system you have
 * Created by patny on 11/20/2017.
 */
//// TODO: 2018-03-31 Rewrite this layer, maybe we even want to not have this?
@Deprecated
public class EmptyMapLayer extends BaseMapLayer {

    private RectF worldRect;

    public EmptyMapLayer(MapView mapView) {
        super(mapView);
        worldRect = new RectF();
    }

    public EmptyMapLayer(MapView mapView, int width, int height) {
        this(mapView);
        createMap(width, height);
    }

    public void createMap(int width, int height) {
//        dimensions = new RectF(0, 0, width, height);
//        if (mapView.getWidth() == 0) {
//            ViewTreeObserver vto = mapView.getViewTreeObserver();
//            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                public boolean onPreDraw() {
//                    if (!hasMeasured) {
//                        initMapLayer();
//                        hasMeasured = true;
//                        paint.setColor(Color.WHITE);
//                    }
//                    return true;
//                }
//            });
//        } else {
//            initMapLayer();
//            paint.setColor(Color.WHITE);
//        }
    }


    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, long deltaTime) {
//        if(dimensions != null) {
//            Paint p = new Paint();
//            p.setColor(Color.WHITE);
//            currentMatrix.mapRect(worldRect, dimensions);
//            canvas.drawRect(worldRect, p);
//        }
    }
}
