package com.example.csiapp1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {
    public EditText mEditTextSubject;
    public EditText mEditTextMessage;
    public TextView t;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_fragment,container,false);
    }

 //   public void onCreate(Bundle savedInstanceState) {

 //   }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.contact_fragment);
        t = getView().findViewById(R.id.contactinfo);
        t.setText("Soham Sapkal(Chairperson):\nTarun Dadlani(Sr. Secretary):\nAjay More(Sr. Treasurer):\nEmail: csi.vesit@ves.ac.in");
        mEditTextSubject = (EditText) getView().findViewById(R.id.edit_text_subject);
        mEditTextMessage = (EditText) getView().findViewById(R.id.edit_text_message);

        Button buttonSend = getView().findViewById(R.id.sendbutton);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    public void sendMail() {
        String recipients = "csi.vesit@ves.ac.in" ;


        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Select app"));
    }
}
