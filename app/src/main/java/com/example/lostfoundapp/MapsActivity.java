package com.example.lostfoundapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;
    ItemsDatabase db;
    private List<Item> lostAndFoundItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        db = new ItemsDatabase(this);
        lostAndFoundItems = db.getItems();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onMapReady);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i=0;i<lostAndFoundItems.size();i++) {
            Item advert = lostAndFoundItems.get(i);
            LatLng latLng = new LatLng(advert.getLat(),advert.getLon());
            mMap.addMarker(new MarkerOptions().position(latLng).title(advert.getItemName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }



}