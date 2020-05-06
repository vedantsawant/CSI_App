package com.example.csiapp1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private String eventDate;
    private EditText wsName;
    private Button selectDateBtn, uploadBtn;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        wsName = findViewById(R.id.wsNameEvent);
        selectDateBtn = findViewById(R.id.select_date);
        uploadBtn = findViewById(R.id.upload_button_event);


        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }
    public void update(){
        if(eventDate == null){
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            return;
        }
        String selectKey = getIntent().getStringExtra("selectKey");
        databaseReference = FirebaseDatabase.getInstance().getReference("/csi_uploads/workshops/" + selectKey);
        databaseReference.child("date").setValue(eventDate);
        databaseReference.child("mworkshopName").setValue(wsName.getText().toString());
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        eventDate = DateFormat.getDateInstance().format(c.getTime());
        //Toast.makeText(this, DateFormat.getDateInstance().format(c.getTime()), Toast.LENGTH_SHORT).show();
    }

}
