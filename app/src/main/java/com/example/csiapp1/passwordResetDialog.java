package com.example.csiapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

public class passwordResetDialog extends AppCompatDialogFragment {

    FirebaseAuth mAuth;
    String email = "";
    boolean success = false;

    public passwordResetDialog(FirebaseAuth firebaseAuth){
        mAuth = firebaseAuth;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText editTextEmail = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        editTextEmail.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editTextEmail);
        builder.setTitle("")
                .setMessage("Enter your email id to reset password")
                .setPositiveButton("Send password reset mail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        success = false;
                        email = editTextEmail.getText().toString();
                        if (email.isEmpty()) {
                            Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int n = email.indexOf("@");
                        System.out.println("HERE " + n);
                        int len = email.length();
                        System.out.println("HERE " + len);
                        if(!email.substring(n+1).equals("ves.ac.in")){
                            Toast.makeText(getActivity(), "Please enter a valid VES id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            //Toast.makeText(getContext(), "Password reset link has been sent to your email id, please check!", Toast.LENGTH_SHORT).show();
                                        } else{

                                        }
                                    }
                                });

                        Toast.makeText(getActivity(), "Password reset link has been sent to your email!", Toast.LENGTH_SHORT).show();
                        
                    }
                }).setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //close
            }
        });
        return builder.create();
    }
}
