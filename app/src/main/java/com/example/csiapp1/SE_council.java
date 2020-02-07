package com.example.csiapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SE_council extends AppCompatActivity {

    private FloatingActionButton fb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_council);

        fb1 = findViewById(R.id.SEcouncil_admin);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SE_council.this, SE_admin.class);
                startActivity(intent);
            }
        });
    }
}
