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

public class registerUser extends AppCompatActivity {

    private Button registerButton;
    private EditText email, password, phone;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String admins[] = {"swapnilgore029@gmail.com", "test", "vedant.sawant.2604@gmail.com"};

    private boolean checkAdmin(String emailC){
        boolean isAdmin = false;
        for(int i = 0; i < admins.length; i++){
            if(admins[i].equals(emailC)){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerButton = findViewById(R.id.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        mAuth = FirebaseAuth.getInstance();



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    public void register(){
        String emailS = email.getText().toString().trim();
        String passwordS = password.getText().toString().trim();
        String phoneS = phone.getText().toString().trim();


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

        if(phoneS.isEmpty()){
            phone.setError("Phone number is required");
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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
}
