package com.example.csiapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class faculty_incharge extends AppCompatActivity {

    private FloatingActionButton fb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_incharge);

        fb1 = findViewById(R.id.teacher_admin);
        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(faculty_incharge.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
