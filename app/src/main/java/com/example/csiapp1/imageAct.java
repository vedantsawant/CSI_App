package com.example.csiapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class imageAct extends AppCompatActivity implements ImageAdapter.onItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;


    private AVLoadingIndicatorView avi;
   // private FirebaseStorage mStorage;
   private StorageReference mStorage;
    private DatabaseReference databaseReference;
    private ValueEventListener mDBlistener;
    private List<Upload> mUploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        avi = findViewById(R.id.progressbar);
        avi.show();
        mUploads = new ArrayList<>();


        mAdapter = new ImageAdapter(imageAct.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItemClickListener(imageAct.this);

       // mStorage = FirebaseStorage.getInstance().getReference();
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("csi_uploads");

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
                avi.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(imageAct.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

               avi.hide();
            }
        });
    }

    @Override
    public void onItemClick(int position, String details, String name, String Url, String date) {
        DetailsDialog detailsDialog = new DetailsDialog(details, name, Url, date);
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

        //StorageReference imageRef = mStorage.getReferenceFromUrl(selectItem.getMimageUrl());
        FirebaseStorage mStorageA = mStorage.getStorage();
        StorageReference imageRef = mStorageA.getReferenceFromUrl(selectItem.getMimageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference.child(selectKey).removeValue();
                Toast.makeText(imageAct.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(imageAct.this, "Deletion Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(mDBlistener);
    }
}
