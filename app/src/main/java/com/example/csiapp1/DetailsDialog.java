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

import java.util.ArrayList;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatDialogFragment;


public class DetailsDialog extends AppCompatDialogFragment {

    String workhopDetails;
    String workshopName;
    String mURL;
    String mDate;
    String mEmail;
    String mSelectKey;
    private DatabaseReference databaseReference, databaseReference2;
    private  String userInfo[] = new String[3];
    String uploadId;

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
                        uploadId = "" +  mEmail;
                        String regx = ".";
                        char[] ca = regx.toCharArray();
                        for (char c : ca) {
                            uploadId = uploadId.replace(""+c, "");
                        }
                        databaseReference = FirebaseDatabase.getInstance().getReference("csi_uploads/workshops/"+mSelectKey);
                        databaseReference2 = FirebaseDatabase.getInstance().getReference("users/");

                        databaseReference.child("registrations").child(uploadId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    Toast.makeText(getActivity(), "Already Registered", Toast.LENGTH_SHORT).show();
                                }else {
                                    databaseReference = FirebaseDatabase.getInstance().getReference("csi_uploads/workshops");
                                    databaseReference2.child(uploadId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            int count = 0;
                                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                userInfo[count] = data.getValue().toString();
                                                count++;
                                            }
                                            if(mEmail.equals("swapnilgore029@gmail.com")){
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("name").setValue("admin1");
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("email id").setValue(userInfo[0]);
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("phone").setValue(userInfo[2]);
                                            }else if(mEmail.equals("vedant.sawant.2604@gmail.com")){
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("name").setValue("admin2");
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("email id").setValue(userInfo[0]);
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("phone").setValue(userInfo[2]);
                                            } else {
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("name").setValue(userInfo[1]);
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("email id").setValue(userInfo[0]);
                                                databaseReference.child(mSelectKey).child("registrations").child(uploadId).child("phone").setValue(userInfo[2]);
                                            }
                                            }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
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
