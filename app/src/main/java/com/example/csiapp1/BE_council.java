package com.example.csiapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BE_council extends AppCompatActivity implements ImageAdapter.onItemClickListener{

    private FloatingActionButton fb1;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;
    private FirebaseStorage mStorage;
    private DatabaseReference databaseReference;
    private ValueEventListener mDBlistener;
    private List<Upload> mUploads;
    UpcomingTabFragment upcomingTabFragment = new UpcomingTabFragment();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_council);

        fb1 = findViewById(R.id.BEcouncil_admin);
        if(!upcomingTabFragment.checkAdmin(mAuth))
            fb1.hide();

        fb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BE_council.this, BE_admin.class);
                startActivity(intent);
            }
        });
        mRecyclerView = findViewById(R.id.recycler_view_be);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle_be);

        mUploads = new ArrayList<>();


        mAdapter = new ImageAdapter(BE_council.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(BE_council.this);

        mStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("csi_uploads/be_council");

        mDBlistener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setmKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BE_council.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }



    @Override
    public void onItemClick(int position, String details, String name) {
        DetailsDialog detailsDialog = new DetailsDialog(details, name);
        detailsDialog.show(getSupportFragmentManager(), "details dialog");
    }

    @Override
    public void onEditClick(int position) {
        Toast.makeText(this, "Edit at position " + position, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(BE_council.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BE_council.this, "Deletion Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBlistener);
    }
}
