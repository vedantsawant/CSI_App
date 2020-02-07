package com.example.csiapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TE_council extends AppCompatActivity {

    private FloatingActionButton fb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_council);

        fb1 = findViewById(R.id.TEcouncil_admin);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TE_council.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
