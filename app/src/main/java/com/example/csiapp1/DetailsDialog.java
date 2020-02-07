package com.example.csiapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DetailsDialog extends AppCompatDialogFragment {

    String workhopDetails;
    String workshopName;

    public DetailsDialog(String mworkhopDetails, String mName){
        workhopDetails = mworkhopDetails;
        workshopName = mName;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(workshopName)
                .setMessage(workhopDetails)
                .setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close
                    }
                });
        return builder.create();
    }
}
