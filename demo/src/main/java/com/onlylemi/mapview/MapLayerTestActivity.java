package com.onlylemi.mapview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.MapViewListener;
import com.onlylemi.mapview.library.graphics.BaseGraphics;
import com.onlylemi.mapview.library.graphics.BaseMark;
import com.onlylemi.mapview.library.graphics.implementation.LocationUser;
import com.onlylemi.mapview.library.graphics.implementation.ProximityMark;
import com.onlylemi.mapview.library.layer.LocationLayer;
import com.onlylemi.mapview.library.layer.MarkLayer;
import com.onlylemi.mapview.library.utils.MapMath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapLayerTestActivity extends AppCompatActivity {

    private static final String TAG = "MapLayerTestActivity";

    private MapView mapView;

    private LocationUser user;

    private Matrix transformMatrix;
    private PointF position = new PointF(0, 0);

    private LocationLayer locationLayer;
    private MarkLayer markLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_layer_test);

        mapView = (MapView) findViewById(R.id.mapview);

        mapView.setCanvasBackgroundColor(Color.RED);

        try {
            // TODO: 2017-02-22 get from net
            Bitmap map = BitmapFactory.decodeStream(getAssets().open("backendroom.png"));

            transformMatrix = MapMath.createMappingMatrix(map, 7, 5, new PointF(0, 0), new PointF(633, 500));   //<--------- THIS IS FOR THE BACKEND ROOM PNG

            mapView.loadMap(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapView.setMapViewListener(new MapViewListener() {
            @Override
            public void onMapLoadSuccess() {

                try {
                    user = new LocationUser(BitmapFactory.decodeStream(getAssets().open("marker.png")), MapMath.transformPoint(transformMatrix, position), new PointF(1, 0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                locationLayer = new LocationLayer(mapView, user);

                markLayer = new MarkLayer(mapView, user);
                markLayer.setMarkIsClickListener(new MarkLayer.MarkIsClickListener() {
                    @Override
                    public void markIsClick(BaseMark iMark, int i) {
                        //Handle click. You get a reference to the IMark and the index in the mark array
                        Log.d(TAG, "Clicked a mark");
                    }
                });

                markLayer.setMarkTriggeredListener(new MarkLayer.MarkIsTriggered() {
                    @Override
                    public void onEnter(ProximityMark mark, int index) {

                        mark.setVisible(true);

                        Log.d(TAG, "Trigered mark");
                    }

                    @Override
                    public void onExit(ProximityMark mark, int index) {
                        Log.d(TAG, "Exited mark");

                        mark.setVisible(false);

                    }
                });

                mapView.addLayer(locationLayer);
                mapView.addLayer(markLayer);

                mapView.setUser(user);
            }

            @Override
            public void onMapLoadFail() {
                Log.e(TAG, "Failed to load map");
            }
        });

    }

    //region DEBUGGING

    //Enable or disable freelooking
    private boolean freeLook = false;

    private boolean debug = false;

    private int index = 0;

    private ArrayList<ProximityMark> bs = new ArrayList();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        boolean handled = false;

        if (mapView.isMapLoadFinish()) {
            if (keyCode == KeyEvent.KEYCODE_W) {
                position.y -= 0.5f;
                user.setLookAt(new PointF(0.0f, 1.0f), 0.3f);
                handled = true;
            } else if (keyCode == KeyEvent.KEYCODE_A) {
                position.x -= 0.5f;
                user.setLookAt(new PointF(-1, 0), 0.3f);
                handled = true;
            } else if (keyCode == KeyEvent.KEYCODE_S) {
                position.y += 0.5f;
                user.setLookAt(new PointF(0.0f, -1.0f), 0.3f);
                handled = true;
            } else if (keyCode == KeyEvent.KEYCODE_D) {
                position.x += 0.5f;
                user.setLookAt(new PointF(1, 0), 0.3f);
                handled = true;
            }

            if (keyCode == KeyEvent.KEYCODE_H) {

                if (!freeLook) {
                    mapView.setTrackingMode(MapView.TRACKING_MODE.ZOOM_WITHIN_POINTS);
                    freeLook = !freeLook;
                } else {
                    mapView.setTrackingMode(MapView.TRACKING_MODE.FREE);
                    freeLook = !freeLook;
                }

            }

            if (keyCode == KeyEvent.KEYCODE_K) {


                mapView.setTrackingMode(MapView.TRACKING_MODE.FOLLOW_USER);

                //mapView.setDebug(!debug);

                debug = !debug;

            }

            if (keyCode == KeyEvent.KEYCODE_P) {

                try {
                    Bitmap markBm = BitmapFactory.decodeStream(getAssets().open("marker.png"));

                    bs.add(new ProximityMark(markBm, TestData.getMarks().get(index), markBm.getHeight() * 2, true, false));
                    markLayer.setProximityMarks(bs);

                    index++;

                    mapView.setZoomPoints(bs, 5.0f, true);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            //If continious is true it will keep the mark array as a reference
            //mapView.zoomWithinPoints(posis);

            user.move(MapMath.transformPoint(transformMatrix, position), 0.1f);
        }
        return handled;
    }

    //endregion


}
