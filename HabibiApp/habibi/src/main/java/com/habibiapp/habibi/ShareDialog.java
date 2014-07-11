package com.habibiapp.habibi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by habibi on 7/10/14.
 */
public class ShareDialog extends DialogFragment {
        public static final String TEXT = "text";
        public static final String TAG = ShareDialog.class.getSimpleName();

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final String text = this.getArguments().getString("text");
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(text)
                    .setPositiveButton("Copy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            final ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("label", text);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.copied),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Share", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
                            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
}
