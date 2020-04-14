package com.example.csiapp1;

import android.net.Uri;
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
        t.setText("Soham Sapkal(Chairperson): 9769897408\nTarun Dadlani(Sr. Secretary): 8390106634\nAjay More(Sr. Treasurer): 7718965189\nEmail: csi.vesit@ves.ac.in");
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

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:" + recipients + "?&subject=" + Uri.encode(subject) + "&body=" + Uri.encode(message)));
        //intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"csi.vesit@ves.ac.in"});
        //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
