package com.example.csiapp1;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;


public class ProfileFragment extends Fragment {
    public TextView tvname,tvemail,tvcontact;
    public ImageView profilepicture;
    public MaterialCardView contactcard,magazinecard,webcard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment,container,false);
    }

    //   public void onCreate(Bundle savedInstanceState) {

    //   }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvname = getView().findViewById(R.id.tvname);
        tvemail = getView().findViewById(R.id.tvemail);
        tvname = getView().findViewById(R.id.tvname);
        profilepicture = getView().findViewById(R.id.profilepicture);
        contactcard= getView().findViewById(R.id.contactcard);
        magazinecard= getView().findViewById(R.id.magazinecard);
        webcard= getView().findViewById(R.id.webcard);

        contactcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ContactActivity.class);
                startActivity(intent);
            }
        });
        magazinecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MagazineActivity.class);
                startActivity(intent);
            }
        });

    }

}
