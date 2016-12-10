package com.compilation.demos.notification;

import android.os.Bundle;
import android.view.View;

import com.compilation.R;
import com.compilation.mainApp.HolderActivity;

public class Activity extends HolderActivity {

    /**
     * Demonstration of a repeating scheduled notification with a message from resources
     * Everything is in the Notification class
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_system);
    }

    //just two simple buttons to start and stop the notification system

    public void startNotifying(View v){
        Notification.setNotification(this);
    }

    public void stopNotifying(View v){
        Notification.cancelNotification(this);
    }
}
