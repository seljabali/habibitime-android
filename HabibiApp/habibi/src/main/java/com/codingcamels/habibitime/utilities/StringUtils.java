package com.codingcamels.habibitime.utilities;

import android.widget.TextView;

/**
 * Created by habibi on 6/30/14.
 */
public class StringUtils {

    public static boolean isEmpty(String string) {
        if (string == null || "".equals(string.trim())) {
            return true;
        }
        return false;
    }

    public static String valueOrEmpty(String string) {
        if (isEmpty(string)) {
            return "";
        }
        return string;
    }

    public static String getBIfAEmpty(String strA, String strB) {
        if (isEmpty(strA)) {
            return strB;
        }
        return strA;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isTextViewEmpty(TextView textView) {
        if (textView == null || textView.getText() == null || textView.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isTextViewNotEmpty(TextView textView) {
        return !isTextViewEmpty(textView);
    }
}
