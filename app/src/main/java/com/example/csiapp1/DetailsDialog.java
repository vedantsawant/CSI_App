package com.example.csiapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;


public class DetailsDialog extends AppCompatDialogFragment {

    String workhopDetails;
    String workshopName;
    String mURL;
    String mDate;
    String mEmail;
    String mSelectKey;
    String uploadID;
    private DatabaseReference databaseReference;

    public DetailsDialog(String mworkhopDetails, String mName, String url, String date, String email, String selectKey){
        workhopDetails = mworkhopDetails;
        workshopName = mName;
        mURL = url;
        mDate = date;
        mEmail = email;
        mSelectKey = selectKey;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(workshopName + " ( " + mDate + " )")
                .setMessage(workhopDetails)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Registration link uri
                        //Uri uriUrl = Uri.parse(mURL);
                        //Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        //startActivity(launchBrowser);
                        String uploadId = mEmail;
                        String regx = ".";
                        char[] ca = regx.toCharArray();
                        for (char c : ca) {
                            uploadId = uploadId.replace(""+c, "");
                        }
                        databaseReference = FirebaseDatabase.getInstance().getReference("csi_uploads/workshops/"+mSelectKey);
                        databaseReference.child("registrations").child(uploadId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Toast.makeText(getActivity(), "Already Registered", Toast.LENGTH_SHORT).show();
                                }else {
                                    databaseReference = FirebaseDatabase.getInstance().getReference("csi_uploads/workshops");
                                    String uploadId = mEmail;
                                    String regx = ".";
                                    char[] ca = regx.toCharArray();
                                    for (char c : ca) {
                                        uploadId = uploadId.replace("" + c, "");
                                    }
                                    databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("email id").setValue(mEmail);
                                    Toast.makeText(getActivity(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                })
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close
                    }
                });
        return builder.create();
    }
}
