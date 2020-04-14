package com.example.csiapp1;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutFragment extends Fragment {

    FloatingActionButton logout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.about_fragment, container, false);
        String des = "The CSI-VESIT council is an amalgamation of various people who collectively work to create and host events which are enthralling as well as beneficial to the students of our college. This includes a plethora of technical events and educational workshops.\nThe council works on improving the quality of interaction with each one of its society members.";
        simulateDayNight(1);
        View aboutPage = new AboutPage(getActivity())

                .isRTL(false)
                .setImage(R.drawable.csi2)
                .setDescription(des)
                .addGroup("Connect with us")
                .addEmail("csi.vesit@ves.ac.in")
                .addFacebook("csivesit")
                .addInstagram("csi_vesit")
                .addWebsite("http://www.csivesit.org/")
                .create();

        viewGroup.addView(aboutPage);
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        simulateDayNight(1);
        logout = getView().findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    mAuth.signOut();
                    Intent intent = new Intent(getActivity(), SignIn.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        //final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }
        //else if (currentSetting == FOLLOW_SYSTEM) {
        //    AppCompatDelegate.setDefaultNightMode(
        //           AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        //}

}}
