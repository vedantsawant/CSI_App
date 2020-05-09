package com.example.csiapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

public class registerUser extends AppCompatActivity {

    private Button registerButton;
    private EditText email, password, phone;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    AVLoadingIndicatorView avi;
    private String admins[] = {"swapnilgore029@gmail.com", "test", "vedant.sawant.2604@gmail.com"};
    private DatabaseReference databaseReference;
    private String emailS, passwordS, phoneS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerButton = findViewById(R.id.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        avi = findViewById(R.id.progressbarR);
        databaseReference = FirebaseDatabase.getInstance().getReference("/users");

        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    public void register(){
        emailS = email.getText().toString().trim();
        passwordS = password.getText().toString().trim();
        phoneS = phone.getText().toString().trim();


        if (emailS.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(passwordS.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(phoneS.isEmpty() || phone.length() != 10){
            phone.setError("Please enter valid phone number");
            phone.requestFocus();
            return;
        }
        if(!checkAdmin(emailS)) {
            int n = emailS.indexOf("@");
            System.out.println("HERE " + n);
            int len = emailS.length();
            System.out.println("HERE " + len);
            if (!emailS.substring(n + 1).equals("ves.ac.in")) {
                Toast.makeText(registerUser.this, "Please enter a valid vesit id", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        avi.setVisibility(View.VISIBLE);
        avi.show();
        mAuth.createUserWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                sendVerificationEmail();
                        }else{
                            Toast.makeText(registerUser.this, "Opps! Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void sendVerificationEmail(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String uploadId = user.getEmail();
                        String regx = ".";
                        char[] ca = regx.toCharArray();
                        for (char c : ca) {
                            uploadId = uploadId.replace("" + c, "");
                        }
                        if (!checkAdmin(emailS)) {
                            int start = 0;
                        while (start < uploadId.length()) {
                            if (!Character.isDigit(uploadId.charAt(start))) {
                                break;
                            }
                            start++;
                        }
                        int Fstart = 0;
                        while (Fstart < user.getEmail().length()) {
                            if (!Character.isDigit(user.getEmail().charAt(Fstart)) && user.getEmail().charAt(Fstart) != '.') {
                                break;
                            }
                            Fstart++;
                        }
                        int Sstart = Fstart;
                        while (Sstart < user.getEmail().length()) {
                            if (user.getEmail().charAt(Sstart) == '.') {
                                break;
                            }
                            Sstart++;
                        }
                        String FirstName = user.getEmail().substring(Fstart, Sstart);
                        String lastName = user.getEmail().substring(Sstart + 1, uploadId.indexOf('@'));
                        databaseReference.child(uploadId).child("name").setValue(FirstName + " " + lastName);
                    }else{
                            databaseReference.child(uploadId).child("name").setValue("swapnil gore");
                        }

                        databaseReference.child(uploadId).child("emailID").setValue(emailS);
                        databaseReference.child(uploadId).child("phone").setValue(phoneS);
                        avi.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            Toast.makeText(registerUser.this, "Please check your email id for verification and login", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(registerUser.this, SignIn.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    }
                });
    }
    private boolean checkAdmin(String emailC){
        boolean isAdmin = false;
        for(int i = 0; i < admins.length; i++){
            if(admins[i].equals(emailC)){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

}
