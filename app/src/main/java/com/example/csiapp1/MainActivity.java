package com.example.csiapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(btmnavListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EventFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener btmnavListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selected =null;
            switch (menuItem.getItemId()){
                case R.id.events:
                    selected= new EventFragment();
                    break;
                case R.id.council:
                    selected= new CouncilFragment();
                    break;
                case R.id.contact:
                    selected= new ContactFragment();
                    break;
                case R.id.about:
                    selected= new AboutFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    ,selected).commit();
            return true;
        }
    };
}
