package com.codingcamels.habibitime.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.content.ClipboardManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

/**
 * Created by habibi on 7/12/14.
 */
public class ViewUtil {

    public static float getScreenHeightDP(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;

//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        return displayMetrics.heightPixels / displayMetrics.density;
    }

    public static float getScreenWidthDP(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density;
    }
}
