package com.compilation.demos.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.compilation.mainApp.MainActivity;
import com.compilation.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;

class Notification {

    /**
     * This class contains all of the implementations needed to send and reschedule a notification
     */

    static void setNotification(Context context) {

        //we use the alarm manager to trigger the notification
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //fiveSeconds is used to set the net alarm
        //randomDate could also be used for a random notification between two hours, daily
        alarmManager.set(AlarmManager.RTC, fiveSeconds().getTime(), pendingIntent);

    }

    //stop the notification
    static void cancelNotification(Context context) {

        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent.getBroadcast(context, 0, intent, 0).cancel();

    }

    //this function is used to get the content from res
    //a call to a server could be made here
    private static String getMessage(Context context) {

        //load the array
        String[] messages = context.getResources().getStringArray(R.array.notifications);
        SharedPreferences sharedPreferences = new SharedPreferences();

        String message;
        //check if current index is over the array size
        if (sharedPreferences.getNotificationCount() >= messages.length) {

            //reset
            sharedPreferences.resetNotificationCount();

            //load message
            message = messages[sharedPreferences.getNotificationCount()];
        } else {

            //load message
            message = messages[sharedPreferences.getNotificationCount()];

            //increase count
            sharedPreferences.addNotificationCount();
        }
        return message;
    }


    //random date generator with a 12 hours interval, each day
    static Date randomDate() {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());


        c.set(Calendar.HOUR, 9);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.AM_PM, 0);
        c.add(Calendar.DATE, 1);

        //starting date
        Date startDate = c.getTime();

        c.add(Calendar.HOUR, 12);

        //end date after 12 hours
        Date endDate = c.getTime();

        long startDateLong = startDate.getTime();
        long endDateLong = endDate.getTime();
        Random r = new Random();
        //generate random date between interval
        long number = startDateLong + ((long) (r.nextDouble() * (endDateLong - startDateLong)));

        return new Date(number);
    }

    //simple function to set a 5 second delay
    static Date fiveSeconds() {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        c.add(Calendar.SECOND, 5);

        return c.getTime();
    }

    //notification builder
    private static void notification(Context context) {

        //get messages from res, at current index
        String message = getMessage(context);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Hi!")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(new SharedPreferences().getNotificationCount(), builder.build());

        //reset the alarm manager to repeat the whole process
        setNotification(context);

    }

    //alarm service which triggers the notification
    public static class MyAlarmService extends Service {


        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            notification(this);

            return START_NOT_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }

    //receiver which triggers the alarm service
    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent service = new Intent(context, MyAlarmService.class);
            context.startService(service);

        }
    }
}
