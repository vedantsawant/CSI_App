package com.example.csiapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditCouncil extends AppCompatActivity {

    private DatabaseReference databaseReference;
    EditText name, post, emailID;
    Button uploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_council);


        name = findViewById(R.id.name);
        post = findViewById(R.id.post);
        emailID = findViewById(R.id.contactText);
        uploadBtn = findViewById(R.id.upload_button);


        //
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectKey = getIntent().getStringExtra("selectKey");
                String councilName = getIntent().getStringExtra("councilName");
                databaseReference = FirebaseDatabase.getInstance().getReference("csi_uploads/"+councilName);
                String newEmail = emailID.getText().toString();
                String newPost = post.getText().toString();
                String newName = name.getText().toString();
                databaseReference.child(selectKey).child("regURL").setValue(newEmail);
                databaseReference.child(selectKey).child("mWorkshopDetails").setValue(newPost);
                databaseReference.child(selectKey).child("mworkshopName").setValue(newName);
            }
        });
    }
}
