package com.example.csiapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class TE_admin extends AppCompatActivity {

    private ImageView MyimageView;
    private Button imgBtn;
    private Button uploadBtn;
    private EditText workshopName, workshopDetails, contactInfo;
    private ProgressBar progressBar;

    private Uri imaageUri;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask uploadTask;


    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_te_admin);

        MyimageView = findViewById(R.id.image_view);
        imgBtn = findViewById(R.id.choose_img);
        uploadBtn = findViewById(R.id.upload_button);
        workshopName = findViewById(R.id.workshop_name);
        workshopDetails = findViewById(R.id.workshop_details);
        progressBar = findViewById(R.id.progress_bar);
        contactInfo = findViewById(R.id.contactText);

        storageReference = FirebaseStorage.getInstance().getReference("/csi_uploads/te_council");
        databaseReference = FirebaseDatabase.getInstance().getReference("/csi_uploads/te_council");

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(TE_admin.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] Permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(Permissions, PERMISSION_CODE);
                    }else{
                        pickImageFromGallery();
                    }
                }else{
                    pickImageFromGallery();
                }
            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(imaageUri != null){
            progressBar.setVisibility(View.VISIBLE);
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imaageUri));
            uploadTask = fileReference.putFile(imaageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 0);
                            Toast.makeText(TE_admin.this, "Upload Successfull!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            /*Upload upload = new Upload(workshopName.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);
                             */
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            Upload upload = new Upload(workshopName.getText().toString().trim(),
                                    workshopDetails.getText().toString().trim(),downloadUrl.toString(), contactInfo.getText().toString());

                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);

                        }

                    }).addOnFailureListener(new OnFailureListener() {

                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TE_admin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }

                    });
        }else{
            Toast.makeText(this, "No File selected!", Toast.LENGTH_SHORT).show();
        }
    }

    public void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch ((requestCode)){
            case PERMISSION_CODE :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imaageUri = data.getData();
            Picasso.with(this).load(imaageUri).into(MyimageView);
            //MyimageView.setImageURI(data.getData());
        }
    }

    private void openImagesActivity(){
        Intent intent = new Intent(this, imageAct.class);
        startActivity(intent);
    }


}

