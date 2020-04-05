package com.example.csiapp1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  return inflater.inflate(R.layout.about_fragment,container,false);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.about_fragment, container, false);
        String des = "Desciption text";
        View aboutPage = new AboutPage(getActivity())
                .isRTL(false)
                .setImage(R.drawable.csi2)
                .setDescription(des)
                .addGroup("Connect with us")
                .addEmail("csi.vesit@ves.ac.in")
                .addFacebook("csivesit")
                .addInstagram("csi_vesit")
                .create();

        viewGroup.addView(aboutPage);
        return viewGroup;
    }


}
