package com.example.lino.map;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    private ImageButton TypeWater;
    private ImageButton TypeGite;
    private ImageButton TypeCamp;
    private ImageButton TypePDV;

    private ArrayList<PointInteret> List_PI = new ArrayList<PointInteret>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoibGx1eXNzZW4iLCJhIjoiY2poamN5NmtvMDEyYTMwczV1dHJ4aTlxbCJ9.8dIVicZvu-0T0L6LpS1HYw");
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mapboxMap.addOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(@NonNull LatLng point) {
                        openDialog(mapboxMap, point);
                    }
                });
            }
        });
    }

    public void AddPi(String n, String d, String t, Double Lo, Double La, Double a) {
        List_PI.add(new PointInteret(n, d, t, Lo, La, a));
    }

    public void openDialog(MapboxMap mapboxMap, LatLng point) {
        AddMarker addMarker = new AddMarker();
        addMarker.SetArgument(mapboxMap, point, List_PI);
        addMarker.show(getSupportFragmentManager(), "AddMarker");
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
