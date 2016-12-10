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

    static void setNotification(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC, fiveSeconds().getTime(), pendingIntent);

    }

    static void cancelNotification(Context context) {

        Intent intent = new Intent(context, MyReceiver.class);
        PendingIntent.getBroadcast(context, 0, intent, 0).cancel();

    }

    private static String getMessage(Context context) {
        String[] messages = context.getResources().getStringArray(R.array.notifications);
        SharedPreferences sharedPreferences = new SharedPreferences();

        String message;
        if (sharedPreferences.getNotificationCount() >= messages.length) {
            sharedPreferences.resetNotificationCount();
            message = messages[sharedPreferences.getNotificationCount()];
        } else {
            message = messages[sharedPreferences.getNotificationCount()];
            sharedPreferences.addNotificationCount();
        }
        return message;
    }

    static Date randomDate(){

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        c.set(Calendar.HOUR, 9);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.AM_PM, 0);
        c.add(Calendar.DATE, 1);

        Date startDate = c.getTime();

        c.add(Calendar.HOUR, 12);

        Date endDate = c.getTime();

        long startDateLong = startDate.getTime();
        long endDateLong = endDate.getTime();
        Random r = new Random();
        long number = startDateLong+((long)(r.nextDouble()*(endDateLong-startDateLong)));

        return new Date(number);
    }

    static Date fiveSeconds(){

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        c.add(Calendar.SECOND, 5);

        return c.getTime();
    }

    private static void notification(Context context) {

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

        setNotification(context);

    }

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

    public static class MyReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Intent service = new Intent(context, MyAlarmService.class);
            context.startService(service);

        }
    }
}
