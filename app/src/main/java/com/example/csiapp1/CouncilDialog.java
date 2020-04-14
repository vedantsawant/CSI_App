package com.example.csiapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;




public class CouncilDialog extends AppCompatDialogFragment {

    String councilPost;
    String councilName;
    String mURL;

    public CouncilDialog(String mcouncilPost, String mCouncilName, String url){
        councilName = mCouncilName;
        councilPost = mcouncilPost;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(councilName)
                .setMessage(councilPost)
                .setPositiveButton("Contact", new DialogInterface.OnClickListener() {
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
