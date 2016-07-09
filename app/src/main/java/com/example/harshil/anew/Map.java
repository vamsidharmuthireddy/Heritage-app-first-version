package com.example.harshil.anew;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Harshil on 6/21/2016.
 */
public class Map extends MainActivity {
    LinearLayout layout;
    java.util.List<com.example.harshil.anew.Stacklist> set = new ArrayList<Stacklist>();

    java.util.List<com.example.harshil.anew.Stacklist> set1 = new ArrayList<Stacklist>();

    String back = null;
    com.example.harshil.anew.Stacklist monk = new com.example.harshil.anew.Stacklist();
    private LocationManager locationManager;
    private LocationListener listener;
    public double loclat1;
    public double loclong1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        layout= (LinearLayout) findViewById(R.id.map_layout);

        InputStream is = getResources().openRawResource(R.raw.heritage);

        XmlPullParser xpp = null;
        String world = "";
        String text = "";
        StringBuilder string = new StringBuilder();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();

            factory.setNamespaceAware(true);
            xpp.setInput(is, "UTF-8");
            int r = 0;
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                } else if (eventType == XmlPullParser.START_TAG) {
                    world = xpp.getName();
                } else if (eventType == XmlPullParser.END_TAG) {
                    r = r + 1;
                    Log.e("Hello", Integer.toString(r));
                    String check = xpp.getName();
                    if (world.equals("id")) {
                        monk.setId(Integer.parseInt(text));
                    } else if (world.equals("title")) {
                        monk.setTitle(text);
                    } else if (world.equals("lat"))
                        monk.setlatd(Double.parseDouble(text));

                    else if (world.equals("long"))
                        monk.setLongtd(Double.parseDouble(text));
                    else if (world.equals("caption")) {
                        monk.setCaptn(text);
                    } else if (world.equals("image")) {
                        monk.setImgUrl(text);
                    } else if (world.equals("info")) {
                        monk.setAbout(text);
                    }
                    if (check.equals("ip")) {
                        set.add(monk);

                        monk = new com.example.harshil.anew.Stacklist();
                        monk.setDistance(0);
                    }
                    world = new String();

                } else if (eventType == XmlPullParser.TEXT) {
                    //Toast.makeText(this, "Return", Toast.LENGTH_SHORT).show();
                    text = xpp.getText();

                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Log.w("End document", "EOL");
        set1 = set;

        //Layout

        final TextView locationshower = (TextView) findViewById(R.id.ln_show);
//-----------------------------------------------------------------------------
//GPS Activity

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //  Toast.makeText(Map.this, "lol", Toast.LENGTH_SHORT).show();

        Log.w("End document", "211");

        listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                Log.w("End document", "212");

      //          Toast.makeText(Map.this, "mon", Toast.LENGTH_SHORT).show();
                loclat1 = location.getLatitude();
                loclong1 = location.getLongitude();
                locationshower.setText("(" + loclat1 + "," + loclong1 + ")");


                for (int i = 0; i < set1.size(); i++) {
                    double dlon = loclong1 - set1.get(i).getLongtd();
                    double dlat = loclat1 - set1.get(i).getLatd();
                    Location loc1 = new Location("");
                    loc1.setLatitude(loclat1);
                    loc1.setLongitude(loclong1);

                    Location loc2 = new Location("");
                    loc2.setLatitude(set1.get(i).getLatd());
                    loc2.setLongitude(set1.get(i).getLongtd());

                    double distanceInMeters = loc1.distanceTo(loc2);
                    set1.get(i).setDistance(distanceInMeters);

                    /*
                    double si1 = Math.sin(dlat / 2);
                    double si2 = Math.sin(dlon / 2);

                    double a = (si1) * (si1) + Math.cos(loclat1) * Math.cos(set1.get(i).getLatd()) * si2 * si2;
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                    set1.get(i).setDistance(6371000 * c);
*/
                }
                findnn();
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

                locationshower.setText("(" + loclat1 + "," + loclong1 + ")");
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
        Log.e("lollol","sdasd") ;
        getlocation();

        final Handler h = new Handler();
        final int delay = 1000; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                updategps();
                getlocation();
                h.postDelayed(this, delay);
            }
        }, delay);
        //      ----------------------------------------------------------
    }


    public void updategps(){
        layout.removeAllViews();
        for(int k=0;k<3;k++)
        {
            View near = getLayoutInflater().inflate(R.layout.template,null);
            final String str = set1.get(k).getTitle();
            String cap = set1.get(k).getCaptn();
            ImageButton img = (ImageButton) near.findViewById(R.id.ip_image);
            img.setImageResource(getResId(set1.get(k).getImgUrl(), R.drawable.class));
            TextView textview = (TextView) near.findViewById(R.id.ip_id);
            textview.setText(Integer.toString(set1.get(k).getId()));
            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    View ros= (View)v.getParent();
                    Intent intent =new Intent(Map.this,Spacex.class);
                    TextView abc= (TextView)ros.findViewById(R.id.ip_id);
                    Toast.makeText(Map.this, abc.getText().toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("index",abc.getText().toString());
                    startActivity(intent);
                }
            });
            TextView tv1 = (TextView) near.findViewById(R.id.ip_title);
            tv1.setText(str);
            TextView tv2 = (TextView) near.findViewById(R.id.ip_captn);
            tv2.setText(cap);
            layout.addView(near);
        }

    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            Log.e("Wrong", resName);
            e.printStackTrace();
            return -1;
        }
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
        Log.e("Enter","Loop");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, listener);
        }
    }

    private void findnn() {
        Collections.sort(set1, new cmp());

    }


    public void onlist(View v) {
        if (v.getId() == R.id.list) {
            Intent intent = new Intent(Map.this, List.class);
            startActivity(intent);
        }
    }

    public void onmap(View v) {
        if (v.getId() == R.id.map2) {
            Intent intent = new Intent(Map.this, Map.class);
            startActivity(intent);
        }
    }

    public void onhome(View v) {
        if (v.getId() == R.id.home) {
            Intent intent = new Intent(Map.this, Home.class);
            startActivity(intent);
        }
    }

}

class cmp implements Comparator<Stacklist> {

    @Override
    public int compare(Stacklist e1, Stacklist e2) {
        if (e1.getDistance() < e2.getDistance()) {
            return 1;
        } else {
            return -1;
        }
    }

}



