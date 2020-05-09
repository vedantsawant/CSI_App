package com.example.csiapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;

public class SignIn extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button loginButton;
    TextView tv1, tv2,tvregister;
    private FirebaseAuth mAuth;
    AVLoadingIndicatorView avi;

    private String admins[] = {"swapnilgore029@gmail.com", "test", "vedant.sawant.2604@gmail.com"};
    private TextView verifyLink, forgotPassword;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);

        tvregister = findViewById(R.id.tvregister);

        verifyLink = findViewById(R.id.verify);
        forgotPassword = findViewById(R.id.forgot_password);

        mAuth = FirebaseAuth.getInstance();
       // progressBar = findViewById(R.id.progressbar);
        avi = findViewById(R.id.progressbar);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordResetDialog passwordResetDialog = new passwordResetDialog(mAuth);
                passwordResetDialog.show(getSupportFragmentManager(), "forgot password");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate();
            }
        });

        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, registerUser.class);
                startActivity(intent);
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

    public void authenticate() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //String emailregex = "^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.(?:[A-Z]{2,}|co)*(\\\\.(?:[A-Z]{2,}|in)*))+$";
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        int n = email.indexOf("@");
        System.out.println("HERE " + n);
        int len = email.length();
        System.out.println("HERE " + len);
        if(!email.substring(n+1).equals("ves.ac.in") && !checkAdmin(email)){
            Toast.makeText(this, "Please enter a valid VES id", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        avi.setVisibility(View.VISIBLE);
        avi.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                avi.setVisibility(View.GONE);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (task.isSuccessful()) {
                    checkIfEmailVerified();
                } else {
                    avi.hide();
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    public void checkIfEmailVerified(){
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            avi.hide();
            startActivity(intent);
            finish();
            Toast.makeText(SignIn.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.

            Toast.makeText(this, "You need to verify your email before you can proceed", Toast.LENGTH_SHORT).show();
            verifyLink.setVisibility(View.VISIBLE);
            verifyLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendVerificationEmail();
                }
            });

            if(user != null) {
                FirebaseAuth.getInstance().signOut();
            }
            //restart this activity
        }
    }

    public void sendVerificationEmail(){
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            Toast.makeText(SignIn.this, "Please check your email id for verification", Toast.LENGTH_SHORT).show();
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
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }


}
