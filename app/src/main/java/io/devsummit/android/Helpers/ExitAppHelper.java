package io.devsummit.android.Helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import io.devsummit.android.R;

/**
 * Created by wlisrausr on 10/3/17.
 */

public class ExitAppHelper {
    public static void exitTheApp(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setTitle(R.string.title_app_exit)
                .setMessage(R.string.message_app_exit)
                .setPositiveButton(R.string.exit_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton(R.string.undo_exit_button_text, null)
                .show();
    }
}
