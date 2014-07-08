package com.habibiapp.habibi;

/**
 * Created by habibi on 6/30/14.
 */
public class StringUtil {

    public static boolean isEmpty(String string) {
        if (string == null || "".equals(string)) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }
}
