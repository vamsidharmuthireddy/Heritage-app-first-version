package com.example.harshil.anew;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class startscreen extends AppCompatActivity {

    private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    int count=0;

    private Handler mHandler = new Handler();

    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_startscreen);
    }
   /*     mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        time();
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 10) {

                    mProgressStatus = doWork();

                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
                Intent intent=new Intent(startscreen.this,downloader.class);
                startActivity(intent);
            }

            private int doWork() {


                return count;
            }
        }).start();
    }

    private void time() {
        final Handler h = new Handler();
        final int delay = 10; //milliseconds

        h.postDelayed(new Runnable(){
            public void run(){
                if(count<100) {
                    count = count + 1;
                    Log.e(String.valueOf(count), "count");

                    h.postDelayed(this, delay);
                }
            }
        }, delay);
    }
*/
    public void next(View view) {
        if(view.getId()==R.id.starter)
        {
            Intent intent=new Intent(startscreen.this,downloader.class);
            startActivity(intent);
        }
    }
}
