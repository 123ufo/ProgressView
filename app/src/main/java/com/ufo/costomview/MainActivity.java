package com.ufo.costomview;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.ufo.costomview.view.ProgressView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressView progressView = (ProgressView) findViewById(R.id.scanView);

        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i <= 100; i++) {
                    SystemClock.sleep(100);
                    publishProgress(i);
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                if(values[0] >50){
                    progressView.setTextColor(Color.BLACK);
                }
                progressView.setProgress(values[0]);
            }
        }.execute();


    }


}
