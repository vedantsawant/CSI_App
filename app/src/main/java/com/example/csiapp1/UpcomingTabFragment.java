package com.example.csiapp1;
//not working properly

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingTabFragment extends Fragment implements ImageAdapter.onItemClickListener{


    private FloatingActionButton fb1;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference databaseReference;
    private ValueEventListener mDBlistener;
    private List<Upload> mUploads;
    private FirebaseAuth mAuth;
    private String admins[] = {"swapnilgore029@gmail.com", "test", "vedant.sawant.2604@gmail.com"};
    public boolean isAdmin = false;

    public UpcomingTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_tab, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fb1 = getActivity().findViewById(R.id.Event_admin);
        mAuth = FirebaseAuth.getInstance();



        FirebaseUser user = mAuth.getCurrentUser();
        Toast.makeText(getActivity(), "Welcome, " + user.getEmail(), Toast.LENGTH_SHORT).show();


        for(int i = 0; i < admins.length; i++){
            if(admins[i].equals(user.getEmail())){
                isAdmin = true;
            }
        }

        if(!isAdmin){
            fb1.hide();
        }

        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Event_admin.class);
                startActivity(intent);
            }
        });

        mRecyclerView = getActivity().findViewById(R.id.recycler_view_event);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProgressCircle = getActivity().findViewById(R.id.progress_circle_event);

        mUploads = new ArrayList<>();


        mAdapter = new ImageAdapter(getActivity(), mUploads);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(UpcomingTabFragment.this);


        mStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("csi_uploads/workshops");

        mDBlistener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    String date = upload.getDate();
                    //Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
                    // int eventYear = Integer.parseInt(date.substring(8).trim());
                    // int eventMonth = Integer.parseInt(date.substring(0,4).trim());
                    // int eventDate = Integer.parseInt(date.substring(4,6).trim());
                    // System.out.println("HERE " + date);

                    int Wyear, Wmon, Wday = 0;

                    if(date.indexOf("-") == -1){

                     Wyear = Integer.parseInt(date.substring(date.length() - 4).trim());

                    String month = date.substring(0, 4).trim();
                    Wmon = getWMonth(month);
                    Wday = Integer.parseInt(date.substring(3, date.indexOf(',')).trim());
                    }else{
                        Wyear = Integer.parseInt(date.substring(date.length() - 4).trim());
                        Wday = Integer.parseInt(date.substring(0, date.indexOf('-')).trim());
                        String month = date.substring(date.indexOf('-')+1, date.indexOf('-') + 4).trim();
                        Wmon = getWMonth(month);
                    }

                    Calendar c = new GregorianCalendar();
                    int mon = c.get(Calendar.MONTH);

                    int year = c.get(Calendar.YEAR);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    boolean upcoming = true;

                    if (year > Wyear) {
                        upcoming = false;
                    } else {
                        if (mon > Wmon) {
                            upcoming = false;
                        } else {
                            if (mon == Wmon) {
                                if (day > Wday) {
                                    upcoming = false;
                                }
                            }
                        }
                    }

                    // System.out.println("HERE Upcoming event ? : " + upcoming);
                    if (upcoming) {
                        upload.setmKey(postSnapshot.getKey());
                        mUploads.add(upload);
                    }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    public boolean checkAdmin(FirebaseAuth nAuth){
        FirebaseUser user = nAuth.getCurrentUser();
        boolean isAdmin = false;
        for(int i = 0; i < admins.length; i++){
            if(admins[i].equals(user.getEmail())){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    private int getWMonth(String month){
        int Wmonth = 0;
        try {
            Date Wdate = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(Wdate);
            Wmonth = cal.get(Calendar.MONTH);
        }catch(Exception e){
            e.printStackTrace();
        }
        return Wmonth;
    }



    @Override
    public void onItemClick(int position, String details, String name, String url, String date) {
        DetailsDialog detailsDialog = new DetailsDialog(details, name, url, date);
        detailsDialog.show(getFragmentManager(), "details dialog");
    }

    @Override
    public void onEditClick(int position) {
        Upload selectItem = mUploads.get(position);
        final String selectKey = selectItem.getmKey();
        Intent intent = new Intent(getActivity(), EditEvent.class);
        intent.putExtra("selectKey", selectKey);
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectItem = mUploads.get(position);
        final String selectKey = selectItem.getmKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectItem.getMimageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectKey).removeValue();
                Toast.makeText(getActivity(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Deletion Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
