package com.example.csiapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.wang.avi.AVLoadingIndicatorView;

public class Spash_Screen extends AppCompatActivity {

    private ProgressBar splash_progress_bar;
    private  AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash__screen);


        splash_progress_bar = findViewById(R.id.splash_progress);
        avi = findViewById(R.id.splash_progress2);
        avi.show();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(splash_progress_bar.getProgress() != 100){
                    splash_progress_bar.incrementProgressBy(20);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(Spash_Screen.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
        t1.start();
    }
}
