package com.codingcamels.habibitime.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

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
