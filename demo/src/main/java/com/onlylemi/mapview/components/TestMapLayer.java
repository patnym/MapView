package com.onlylemi.mapview.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.graphics.IBackground;
import com.onlylemi.mapview.library.graphics.implementation.Backgrounds.ColorBackground;
import com.onlylemi.mapview.library.layer.BaseMapLayer;

/**
 * Created by patnym on 2018-06-06.
 */

public class TestMapLayer extends BaseMapLayer {

    private RectF modelRect;
    private RectF tRect;
    private Paint paint;

    private IBackground background;

    public TestMapLayer(MapView mapView) {
        super(mapView);
    }

    public TestMapLayer(MapView mapView, int width, int height) {
        super(mapView);
        this.width = width;
        this.height = height;
        modelRect = new RectF(0, 0, width, height);
        tRect = new RectF();
        paint = new Paint();
        paint.setColor(Color.BLUE);

        background = new ColorBackground(Color.BLACK);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void onTouch(float x, float y) {

    }

    @Override
    public boolean update(Matrix currentMatrix, long deltaTime) {
        return hasChanged;
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, long deltaTime) {
        currentMatrix.mapRect(tRect, modelRect);
        background.draw(canvas);
        canvas.drawRect(tRect, paint);
        super.draw(canvas, currentMatrix, currentZoom, deltaTime);
    }

    @Override
    public void debugDraw(Canvas canvas, Matrix currentMatrix) {

    }
}
