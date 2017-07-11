package com.onlylemi.mapview.library;

/**
 * MapViewListener
 *
 * @author: onlylemi
 */
public interface MapViewListener {

    /**
     * when mapview load complete to callback
     */
    void onMapLoadSuccess();

    /**
     * when mapview load error to callback
     */
    void onMapLoadFail();

    /**
     * when rendering begins (Everything is loaded)
     */
    void onBeginRender();
}
