package com.example.harshil.anew;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by Harshil on 6/21/2016.
 */
public class List extends MainActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
       LinearLayout layout = (LinearLayout) findViewById(R.id.take);

        for (int  j = 0; j < set.size(); j++) {
            //       Toast.makeText(this,String.valueOf(j),Toast.LENGTH_SHORT).show();
            View ip = getLayoutInflater().inflate(R.layout.template, null);
            final int  num=j;
            final String str = set.get(j).getTitle();
            String cap = set.get(j).getCaptn();
            ImageButton img = (ImageButton) ip.findViewById(R.id.ip_image);
            img.setImageResource(getResId(set.get(j).getImgUrl(), R.drawable.class));
            TextView textview = (TextView) ip.findViewById(R.id.ip_id);
            textview.setText(Integer.toString(set.get(j).getId()));
            img.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    View ros= (View)v.getParent();
                    Intent intent =new Intent(List.this,Spacex.class);
                    TextView abc= (TextView)ros.findViewById(R.id.ip_id);
                    Toast.makeText(List.this, abc.getText().toString(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("index",abc.getText().toString());
                    startActivity(intent);
                }
            });            TextView tv1 = (TextView) ip.findViewById(R.id.ip_title);
            tv1.setText(str);
            TextView tv2 = (TextView) ip.findViewById(R.id.ip_captn);
            tv2.setText(cap);
            layout.addView(ip);
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






}



