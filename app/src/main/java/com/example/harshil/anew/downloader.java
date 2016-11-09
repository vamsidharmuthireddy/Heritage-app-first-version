package com.example.harshil.anew;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class downloader extends AppCompatActivity {
        /**
        *First downloads an index file containing the names and download paths of required files.
        *Then dowloads the files listed in index.txt file and saves them into external storage
        *Downloaded xml and images are same as in trial package
        *When all the downloading part is done then an intent is called for MainActivity
        */

        ArrayList<String> iname=new ArrayList();
        ArrayList<String> ipath=new ArrayList();

        ImageView my_image;
        private static String file_url = "http://web.iiit.ac.in/~ashwin.sudhir/parent/index.txt";

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_downloader);

        }

        public void ondownload(View v) {
            if (v.getId() == R.id.download) {
                new DownloadFileFromURL().execute(file_url);
            }
        }

    public void oncontinue(View v) {
        if (v.getId() == R.id.cont){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
        class DownloadFileFromURL extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread
             * Show Progress Bar Dialog
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            /**
             * Downloading file in background thread
             */
            @Override
            protected String doInBackground(String... f_url) {
                int count;
                try {

                    URL u = new URL("http://web.iiit.ac.in/~ashwin.sudhir/parent/index.txt");
                    HttpURLConnection c = (HttpURLConnection) u.openConnection();
                    c.setRequestMethod("GET");
                    c.setDoOutput(true);
                    c.connect();



                    FileOutputStream f = new FileOutputStream(new File(Environment.getExternalStorageDirectory(),"index.txt"));

                    InputStream in = c.getInputStream();

                    byte[] buffer = new byte[1024];
                    int len1 = 0;
                    while ( (len1 = in.read(buffer)) > 0 ) {
                        f.write(buffer,0, len1);
                    }

                    f.close();
                    String thisLine=new String();
                    FileReader fr=new FileReader(Environment.getExternalStorageDirectory()+"/index.txt");
                    BufferedReader br = new BufferedReader(fr);

                    while ((thisLine = br.readLine()) != null) {

                        String temp=thisLine;
                        String[] parts = temp.split(" ");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        iname.add(part1);
                        ipath.add(part2);
                        Log.e("entr",part2);
                        // Toast.makeText(MainActivity.this,part1, LENGTH_SHORT).show();
                    }
                    //Reading from file index.txt completed

           /*     for(int i=0;i<iname.size();i++)
                {


                }*/

                    makecall();



                } catch (Exception e) {
                    Log.e("Error: E", e.getMessage());
                }

                return null;
            }

            private void makecall() throws MalformedURLException {

                for(int j=0;j<iname.size();j++) {


                    try {
                        URL u = new URL("http://web.iiit.ac.in/~ashwin.sudhir/parent"+ipath.get(j));

                        HttpURLConnection c = null;
                        c = (HttpURLConnection) u.openConnection();
                        c.setRequestMethod("GET");
                        c.setDoOutput(true);
                        c.connect();

                        File filei = new File(Environment.getExternalStorageDirectory(),ipath.get(j));
                        if (filei.getParentFile() != null) {
                            filei.getParentFile().mkdirs();
                        }
                        filei.createNewFile();

                        FileOutputStream f = new FileOutputStream(new File(Environment.getExternalStorageDirectory(),ipath.get(j)));

                        InputStream in = c.getInputStream();

                        byte[] buffer = new byte[1024];
                        int len1 = 0;
                        while ((len1 = in.read(buffer)) > 0) {
                            f.write(buffer, 0, len1);
                        }

                        f.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("Ghost","Here");
                    }
                }



            }

            protected void onProgressUpdate(String... progress) {
                // setting progress percentage
            }

            /**
             * After completing background task
             * Dismiss the progress dialog
             **/
            @Override
            protected void onPostExecute(String file_url) {
                Log.e("Check Pass","Clear");

                Intent intent =new Intent(downloader.this,MainActivity.class);
                startActivity(intent);

                // dismiss the dialog after the file was downloaded

                // Displaying downloaded image into image viewile.j
                //          String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile1.jpg";

                //         my_image.setImageDrawable(Drawable.createFromPath(imagePath));
                // setting downloaded into image view
            }

        }
    }
