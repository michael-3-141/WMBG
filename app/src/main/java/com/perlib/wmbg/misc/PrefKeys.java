package com.perlib.wmbg.misc;

import android.content.SharedPreferences;

public class PrefKeys {
    public static final String EMIAL_MESSAGE = "emailMessage";
    public static final String CONFIRM_DELETE = "doConfirmDelete";
    public static final String SWIPE_MODE = "swipeMode";

    public static final int MODE_DELETE_ITEM = 0;
    public static final int MODE_RETURN_ITEM = 1;
    public static final int MODE_NOTHING = 2;

    public static String getEmailMessage(SharedPreferences prefs)
    {
        return prefs.getString(EMIAL_MESSAGE, "");
    }

    public static boolean isConfirmDelete(SharedPreferences prefs)
    {
        return prefs.getBoolean(CONFIRM_DELETE, true);
    }

    public static int getSwipeMode(SharedPreferences prefs)
    {
        return prefs.getInt(SWIPE_MODE, 0);
    }
}
