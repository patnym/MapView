package com.onlylemi.mapview.library.camera.modes;

import android.graphics.Matrix;

/**
 * Created by patny on 2017-08-14.
 */

public interface IBaseMode {
    Matrix update(Matrix transformMatrix, float currentZoom, long deltaTimeNano);
}
