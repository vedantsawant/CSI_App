package com.example.csiapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DetailsDialog extends AppCompatDialogFragment {

    String workhopDetails;
    String workshopName;
    String mURL;
    String mDate;

    public DetailsDialog(String mworkhopDetails, String mName, String url, String date){
        workhopDetails = mworkhopDetails;
        workshopName = mName;
        mURL = url;
        mDate = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(workshopName + " ( " + mDate + " )")
                .setMessage(workhopDetails)
                .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Registration link uri
                        Uri uriUrl = Uri.parse(mURL);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    }
                })
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close
                    }
                });
        return builder.create();
    }
}
