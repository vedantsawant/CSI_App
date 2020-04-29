package com.example.csiapp1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;




public class CouncilDialog extends AppCompatDialogFragment {

    String councilPost;
    String councilName;
    String mURL;

    public CouncilDialog(String mcouncilPost, String mCouncilName, String url){
        councilName = mCouncilName;
        councilPost = mcouncilPost;
        mURL = url;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Toast.makeText(getActivity(), mURL, Toast.LENGTH_SHORT).show();
        builder.setTitle(councilName)
                .setMessage(councilPost)
                .setPositiveButton("Contact", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Registration link uri
                       // Uri uriUrl = Uri.parse(mURL);
                        //Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        //startActivity(launchBrowser);
                        String recipients = mURL;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("mailto:" + recipients + "?&subject=" + "CSI enquiry" + "&body=" + "Need assistance regarding, "));
                        //intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"csi.vesit@ves.ac.in"});
                        //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        //intent.putExtra(Intent.EXTRA_TEXT, message);
                        startActivity(Intent.createChooser(intent, "Send Email"));
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
