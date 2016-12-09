package com.compilation.demos.notification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.compilation.R;

public class NotificationSystem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_system);
    }

    public void startNotifying(View v){
        Notification.setNotification(this);
    }

    public void stopNotifying(View v){
        Notification.cancelNotification(this);
    }
}
