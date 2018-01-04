package com.fxiaoke.autoclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class ClockService extends Service {
    int time;

    @Override
    public void onCreate() {
        super.onCreate();
        Calendar calendar1 = Calendar.getInstance();
        time = calendar1.get(Calendar.HOUR_OF_DAY);
        if (time < 10) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                    (Calendar.DAY_OF_MONTH), 9, 50, 10);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent2 = new Intent(this, ClockActivity.class);
            intent2.setAction("clock1");

            PendingIntent pi2 = PendingIntent.getActivity(this, 11, intent2, 0);

            if (alarmManager != null) {
                alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi2);
            }
            Toast.makeText(this, "定时时间今天 9：50", Toast.LENGTH_SHORT).show();
        } else if (time >= 10 && time < 19) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                    (Calendar.DAY_OF_MONTH), 19, 1, 10);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent2 = new Intent(this, ClockActivity.class);
            intent2.setAction("clock1");

            PendingIntent pi2 = PendingIntent.getActivity(this, 22, intent2, 0);

            if (alarmManager != null) {
                alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi2);
            }
            Toast.makeText(this, "定时时间今天 19：01", Toast.LENGTH_SHORT).show();
        } else if (time >= 19) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                    (Calendar.DAY_OF_MONTH), 9, 50, 10);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent2 = new Intent(this, ClockActivity.class);
            intent2.setAction("clock1");

            PendingIntent pi2 = PendingIntent.getActivity(this, 33, intent2, 0);

            if (alarmManager != null) {
                alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pi2);
            }
            Toast.makeText(this, "定时时间明天 9：50", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
