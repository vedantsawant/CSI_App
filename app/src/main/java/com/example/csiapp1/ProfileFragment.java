package com.example.csiapp1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    public TextView tvname,tvemail,tvcontact;
    public ImageView profilepicture;
    public MaterialCardView contactcard,magazinecard,webcard;
    public FloatingActionButton logout;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference2;
    private String uploadId;
    private  String userInfo[] = new String[3];
    String mEmail;
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
        tvname = getActivity().findViewById(R.id.tvname);
        tvemail = getView().findViewById(R.id.tvemail);
        tvcontact = getActivity().findViewById(R.id.tvcontact);
        profilepicture = getView().findViewById(R.id.profilepicture);
        contactcard = getView().findViewById(R.id.contactcard);
        magazinecard = getView().findViewById(R.id.magazinecard);
        webcard = getView().findViewById(R.id.webcard);
        logout = getView().findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("users/");
        tvname.setText("name");
        tvcontact.setText("contact number");

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
        webcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        FirebaseUser user = mAuth.getCurrentUser();
        String emailId = user.getEmail();
        mEmail = user.getEmail();
        tvemail.setText(emailId);
        uploadId = "" + user.getEmail();
        String regx = ".";
        char[] ca = regx.toCharArray();
        for (char c : ca) {
            uploadId = uploadId.replace(""+c, "");
        }

        databaseReference2.child(uploadId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    userInfo[count] = data.getValue().toString();
                    count++;
                }
                String name = userInfo[1];
                tvname = getActivity().findViewById(R.id.tvname);
                tvcontact = getActivity().findViewById(R.id.tvcontact);
                tvcontact.setText(userInfo[2]);
                tvname.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //String phoneno = user.getPhoneNumber();
        //tvcontact.setText(phoneno);
       // String leftPart = emailId.substringBefore('@');
        //String[] leftPartSplitted = leftPart.split('\\.');
       // if(leftPartSplitted.size() == 3){
       //     String year = leftPartSplitted[0];
        //    String firstName = leftPartSplitted[1];
        //    String lastName = leftPartSplitted[2];
        //}else{
            //there is no dot or more than one dot in the substring
        //}

    }

}
