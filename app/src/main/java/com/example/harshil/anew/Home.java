package com.example.harshil.anew;

import android.Manifest;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;

import java.util.Collections;

/**
 * Created by Harshil on 6/21/2016.
 */
public class Home extends MainActivity {
    private LocationManager locationManager;
    private LocationListener listener;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {


            @Override
            public void onLocationChanged(Location location) {
                loclat = location.getLatitude();
                loclong = location.getLongitude();
                final TextView locationshower = (TextView) findViewById(R.id.ln_show1);
//--
                locationshower.setText("(" + loclat + "," + loclong + ")");
                Stacklist min1 = set1.get(0);
                Stacklist min2 = set1.get(1);
                Stacklist min3 = set1.get(2);

                for (int i = 0; i < set1.size(); i++) {
                    double dlon = loclong - set1.get(i).getLongtd();
                    double dlat = loclat - set1.get(i).getLatd();
                    double si1 = Math.sin(dlat / 2);
                    double si2 = Math.sin(dlon / 2);
                    double a = (si1) * (si1) + Math.cos(loclat) * Math.cos(set1.get(i).getLatd()) * si2 * si2;
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                    set1.get(i).setDistance(6371000 * c);

                }
                findnn();
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        getlocation();

        //      ----------------------------------------------------------
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getlocation();
                }
                return;
        }
    }

    private void getlocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            }
        } else {
            locationManager.requestLocationUpdates("gps", 1000, 0, listener);
        }
    }

    private void findnn() {
        Collections.sort(set1, new di());

    }
}
