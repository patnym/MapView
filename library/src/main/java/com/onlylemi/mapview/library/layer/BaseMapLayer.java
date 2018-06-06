package com.onlylemi.mapview.library.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.graphics.IBackground;

/**
 * BaseMapLayer
 *
 * @author: onlylemi
 */
public class BaseMapLayer extends BaseLayer {

    private static final String TAG = "BaseMapLayer";

    private Bitmap bmp;

    private int width;
    private int height;

    protected Paint paint;
    protected IBackground background;

    public BaseMapLayer(MapView mapView) {
        super(mapView);
    }

    public BaseMapLayer(MapView mapView, Bitmap bmp, IBackground background) {
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
