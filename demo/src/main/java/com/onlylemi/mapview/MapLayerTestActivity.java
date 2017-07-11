package com.onlylemi.mapview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.onlylemi.mapview.library.MapView;
import com.onlylemi.mapview.library.MapViewListener;
import com.onlylemi.mapview.library.graphics.IMark;
import com.onlylemi.mapview.library.graphics.implementation.BaseMark;
import com.onlylemi.mapview.library.graphics.implementation.BaseUser;
import com.onlylemi.mapview.library.layer.LocationLayer;
import com.onlylemi.mapview.library.layer.MarkLayer;
import com.onlylemi.mapview.library.utils.MapMath;
import com.onlylemi.mapview.library.utils.MapUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapLayerTestActivity extends AppCompatActivity {

    private static final String TAG = "MapLayerTestActivity";

    private MapView mapView;

    private LocationLayer locationLayer;
    private MarkLayer markLayer;

    private PointF position = new PointF(0,0);
    private Matrix mappingMatrix;

    private BaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_layer_test);

        mapView = (MapView) findViewById(R.id.mapview);
        Bitmap bitmap = null;
        try {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inScaled = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;


            bitmap = BitmapFactory.decodeStream(getAssets().open("backendroom.png"), null, options);
//
//            Log.d("Bitmap", "Bitmap: " + bitmap.getHeight());
//
//            bitmap = Bitmap.createScaledBitmap(bitmap, 800, 480, false);

            Log.d("MapActivity", bitmap.getConfig().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapView.loadMap(bitmap);
        mapView.setMapViewListener(new MapViewListener() {
            @Override
            public void onMapLoadSuccess() {
                Log.i(TAG, "onMapLoadSuccess");
                //Create the mapping matrix to map between coordinates
                mappingMatrix = MapMath.createMappingMatrix(mapView, 70, 183, new PointF(20, 75), new PointF(803, 1984));

                //Create user
                try {
                    user = new BaseUser(BitmapFactory.decodeStream(getAssets().open("marker.png")), MapMath.transformPoint(mappingMatrix, new PointF(0, 0)), new PointF(1, 0));
                } catch( IOException e) {
                    e.printStackTrace();
                }
                locationLayer = new LocationLayer(mapView, user);

                List<PointF> marks = TestData.getMarks();
                List<IMark> baseMarks = new ArrayList<>();
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

                for (PointF mark : marks) {
                    baseMarks.add(new BaseMark(bmp, MapMath.transformPoint(mappingMatrix, mark)));
                }

                //final List<String> marksName = TestData.getMarksName();
                markLayer = new MarkLayer(mapView);
                markLayer.setMarkIsClickListener(new MarkLayer.MarkIsClickListener() {
                    @Override
                    public void markIsClick(IMark obj, int index) {
                        BaseMark mark = ((BaseMark) obj);
                        mark.setBmp(BitmapFactory.decodeResource(getResources(), R.mipmap.mark_touch));
                        mapView.refresh();
                    }
               });
                markLayer.setMarks(baseMarks);

                mapView.addLayer(markLayer);
                mapView.addLayer(locationLayer);
                mapView.centerOnUser(user);
            }

            @Override
            public void onMapLoadFail() {
                Log.i(TAG, "onMapLoadFail");
            }

            @Override
            public void onBeginRender() {

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map_layer_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private boolean isP = true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(mapView.isMapLoadFinish()) {
            if (keyCode == KeyEvent.KEYCODE_W) {
                position.y-=1.0;
                //Look up
                user.setLookAt(new PointF(-0.5f, 0.5f));
            } else if (keyCode == KeyEvent.KEYCODE_A) {
                position.x-= 1.0f;
                user.setLookAt(new PointF(-1, 0));
            } else if (keyCode == KeyEvent.KEYCODE_S) {
                position.y+=1.0f;
                user.setLookAt(new PointF(0.5f, 0.5f));
            } else if (keyCode == KeyEvent.KEYCODE_D) {
                position.x+=1.0f;
                user.setLookAt(new PointF(1, 0));
            }

            if(keyCode == KeyEvent.KEYCODE_H) {

                if(isP) {
                    mapView.disableCenterOnUser();
                    isP = !isP;
                }
                else {
                    mapView.centerOnUser(user);
                    isP = !isP;
                }

            }
            //Log.d(TAG, "Raw position is: " + position.toString());

            user.setPosition(MapMath.transformPoint(mappingMatrix, position));

            //Log.d(TAG, "Transformed position is: " + user.getPosition().toString());

            mapView.refresh();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mapView.isMapLoadFinish()) {
            switch (item.getItemId()) {
                case R.id.map_layer_set_rotate:
                    int rotate = new Random().nextInt(360);
                    mapView.setCurrentRotateDegrees(rotate);
                    mapView.refresh();

                    Toast.makeText(this, "current rotate: " + rotate, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.map_layer_set_zoom1:
                    mapView.setCurrentZoom(mapView.getCurrentZoom() / 2);
                    mapView.refresh();
                    break;
                case R.id.map_layer_set_zoom2:
                    mapView.setCurrentZoom(mapView.getCurrentZoom() * 2);
                    mapView.refresh();
                    break;
                case R.id.map_layer_set_auto_rotate_and_scale:
                    if (mapView.isScaleAndRotateTogether()) {
                        item.setTitle("Set Rotate and Scale Together");
                    } else {
                        item.setTitle("Set Rotate and Scale Not Together");
                    }
                    mapView.setScaleAndRotateTogether(!mapView.isScaleAndRotateTogether());
                    break;
                default:
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
