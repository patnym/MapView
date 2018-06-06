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
public abstract class BaseMapLayer extends BaseLayer {

    protected int width;
    protected int height;

    public BaseMapLayer(MapView mapView) {
        super(mapView);
    }

    public abstract int getWidth();

    public abstract int getHeight();
}
