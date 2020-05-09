package com.example.csiapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {
    public EditText mEditTextSubject;
    public EditText mEditTextMessage;
    public TextView t;
    public Button buttonSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_fragment);
        t = findViewById(R.id.contactinfo);
        t.setText("Soham Sapkal(Chairperson): 9769897408\nTarun Dadlani(Sr. Secretary): 8390106634\nAjay More(Sr. Treasurer): 7718965189\nEmail: csi.vesit@ves.ac.in");
        mEditTextSubject = (EditText) findViewById(R.id.edit_text_subject);
        mEditTextMessage = (EditText) findViewById(R.id.edit_text_message);

        buttonSend = (Button) findViewById(R.id.sendbutton);
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
