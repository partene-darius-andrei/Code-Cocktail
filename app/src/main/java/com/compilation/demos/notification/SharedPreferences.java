package com.compilation.demos.notification;

import android.content.Context;
import com.compilation.mainApp.MyApplication;

class SharedPreferences {
    private android.content.SharedPreferences pref;
    private android.content.SharedPreferences.Editor editor;

    public static Context context = MyApplication.getContext();

    // Shared preferences file name
    private static final String PREF_NAME = "notificationSystem";
    private static final String NOTIFICATION_COUNT = "notification_count";

    SharedPreferences() {

        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    int getNotificationCount(){
        return pref.getInt(NOTIFICATION_COUNT, 0);
    }

    void addNotificationCount(){
        editor.putInt(NOTIFICATION_COUNT, pref.getInt(NOTIFICATION_COUNT, 0) + 1);
        editor.apply();
    }

    void resetNotificationCount(){
        editor.putInt(NOTIFICATION_COUNT, 0);
        editor.apply();
    }
}