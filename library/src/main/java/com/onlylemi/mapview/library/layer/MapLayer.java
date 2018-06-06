package com.onlylemi.mapview.library.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.graphics.IBackground;

/**
 * Created by patnym on 2018-06-06.
 */
public class MapLayer extends BaseMapLayer {
    private static final String TAG = "MapLayer";

    private Bitmap bmp;

    protected Paint paint;
    protected IBackground background;

    public MapLayer(MapView mapView) {
        super(mapView);
    }

    public MapLayer(MapView mapView, Bitmap bmp, IBackground background) {
        super(mapView);
        this.background = background;
        setBmp(bmp);
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
    }

    @Override
    public void onTouch(float x, float y) {

    }

    @Override
    public void onViewChanged(int width, int height) {
        background.onSurfaceChanged(width, height);
        super.onViewChanged(width, height);
    }

    @Override
    public boolean update(Matrix currentMatrix, long deltaTime) {
        return hasChanged;
    }

    @Override
    public void draw(Canvas canvas, Matrix currentMatrix, float currentZoom, long deltaTime) {
        background.draw(canvas);
        canvas.drawBitmap(bmp, currentMatrix, paint);
        super.draw(canvas, currentMatrix, currentZoom, deltaTime);
    }

    @Override
    public void debugDraw(Canvas canvas, Matrix currentMatrix) {

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
