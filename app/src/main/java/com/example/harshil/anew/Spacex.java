package com.example.harshil.anew;

/**
 * Created by Harshil on 7/2/2016.
 */

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


public class Spacex extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spacex);
        Bundle bundle=getIntent().getExtras();
        String id= bundle.getString("index");
        int a=Integer.parseInt(id);
        a=a-1;
        // String title = set.get(a-1).getTitle();
        //  Toast.makeText(this,title, Toast.LENGTH_SHORT).show();
        ImageView img= (ImageView)findViewById(R.id.backdrop);
        img.setImageResource(getResId(set.get(a).getImgUrl(),R.drawable.class));
        TextView title = (TextView) findViewById(R.id.space_title);
        String str=set.get(a).getTitle();
        Toast.makeText(Spacex.this,str, Toast.LENGTH_SHORT).show();
        title.setText(str);
        TextView captn= (TextView) findViewById(R.id.space_captn);
        String cap =set.get(a).getCaptn();
        captn.setText(cap);
        TextView info = (TextView) findViewById(R.id.space_info);
        String inf = set.get(a).getAbout();
        Toast.makeText(Spacex.this,inf, Toast.LENGTH_SHORT).show();
        info.setText(inf);

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

}
