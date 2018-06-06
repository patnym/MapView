package com.onlylemi.mapview.library;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.onlylemi.mapview.library.graphics.IBackground;
import com.onlylemi.mapview.library.graphics.implementation.Backgrounds.ColorBackground;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.layer.BaseLayer;
import com.onlylemi.mapview.library.layer.BaseMapLayer;

/**
 * Created by patnym on 26/12/2017.
 */

public class MapViewSetupHandler {

    private MapView view;
    private MapViewRenderer renderer;

    private LocationUser currentUser;

    public MapViewSetupHandler(MapView view, MapViewRenderer renderer) {
        this.view = view;
        this.renderer = renderer;
    }


    public void createMap(Bitmap bmp) {
        createMap(bmp, new ColorBackground(Color.BLACK));
    }

    /**
     * Creates a map from a bitmap
     * @param bmp - map image
     * @param background
     */
    public void createMap(Bitmap bmp, IBackground background) {
        BaseMapLayer baseMapLayer = new BaseMapLayer(view, bmp, background);
        addLayer(baseMapLayer);
        renderer.setMapLayer(baseMapLayer);
    }

    /**
     * Creates a empty canvas map
     * @param width
     * @param height
     */
    //// TODO: 2018-03-31 Implement this
    public void createMap(int width, int height) {

    }

    public void setTrackedUser(LocationUser user) {
        currentUser = user;
    }

    public void addLayer(BaseLayer layer) {
        layer.createHandler(renderer);
        renderer.addLayer(layer);
    }

    public LocationUser getUser() {
        return currentUser;
    }
}
