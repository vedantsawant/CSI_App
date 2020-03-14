package com.example.csiapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.csiapp1.Adapter.EventPageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class EventFragment extends Fragment {

    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myFragment =  inflater.inflate(R.layout.event_fragment,container,false);

        viewPager = myFragment.findViewById(R.id.event_view_pager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Upcoming Workshops"));
        tabLayout.addTab(tabLayout.newTab().setText("Past Workshops"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager){
        EventPageAdapter adapter = new EventPageAdapter(getChildFragmentManager());
        adapter.AddFragment(new UpcomingTabFragment(), "Upcoming Workshops");
        adapter.AddFragment(new PastTabFragment(),"Past Workshops");

        viewPager.setAdapter(adapter);
    }
}
