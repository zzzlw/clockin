package com.fxiaoke.autoclock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClockActivity extends AppCompatActivity {
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(10);
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        wakeUpAndUnlock();
        int today = c.get(Calendar.DAY_OF_WEEK);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        Message message = new Message();
        message.what = hour;
        message.arg1 = minute;
        switch (today) {
            case 1:
                //星期日
                Log.e("zlw", "当前不是工作日,是星期" + (today - 1));
                Log.e("zlw", "当前时间:" + str);
                handlerHome.sendMessageDelayed(message, 10000);
                break;
            case 2:

            case 3:

            case 4:

            case 5:

            case 6:
                Log.e("zlw", "当前是星期" + (today - 1));
                Log.e("zlw", "当前时间:" + str);
                handlerfxiaoke.sendMessageDelayed(message, 5000);
                break;
            case 7:
                Log.e("zlw", "当前不是工作日，是星期" + (today - 1));
                Log.e("zlw", "当前时间:" + str);
                handlerHome.sendMessageDelayed(message, 10000);
                // 星期6
                break;
        }
    }

    public void wakeUpAndUnlock() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.SCREEN_DIM_WAKE_LOCK, "StartupReceiver");//最后的参数是LogCat里用的Tag
        wl.acquire();
        //屏幕解锁
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("StartupReceiver");//参数是LogCat里用的Tag
        kl.disableKeyguard();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static boolean writeFile(String filePathAndName, String fileContent) {

        try {

            File f = new File(filePathAndName);

            if (!f.exists()) {
                f.createNewFile();

            }
            OutputStreamWriter write = new OutputStreamWriter(

                    new FileOutputStream(f, true), "UTF-8"); // 追加文件

            BufferedWriter writer = new BufferedWriter(write);

            writer.write(fileContent);

            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

        return true;

    }

    @SuppressLint("HandlerLeak")
    Handler handlerfxiaoke = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fxiaoke://splash"));
            Log.e("zlw", "startfxiaoke");
            startActivity(intent);
            Message message = new Message();
            message.what = msg.what;
            message.arg1 = msg.arg1;
            handlerHome.sendMessageDelayed(message, 5000);
            writeFile(Environment.getExternalStorageDirectory() + File.separator + "recordfile.txt", "执行为时间今天" + msg.what + ":" + (msg.arg1 + 2));

        }
    };
    @SuppressLint("HandlerLeak")
    Handler handlerHome = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
            if (msg.what == 9) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                        (Calendar.DAY_OF_MONTH), 19, 1, 10);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent2 = new Intent(ClockActivity.this, ClockActivity.class);
                intent2.setAction("clock1");
                PendingIntent pi2 = PendingIntent.getActivity(ClockActivity.this, 22, intent2, 0);
                if (alarmManager != null) {
                    alarmManager.setWindow(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi2);
                }
            } else if (msg.what == 19) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                        (Calendar.DAY_OF_MONTH), 9, 50, 10);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent2 = new Intent(ClockActivity.this, ClockActivity.class);
                intent2.setAction("clock1");

                PendingIntent pi2 = PendingIntent.getActivity(ClockActivity.this, 11, intent2, 0);

                if (alarmManager != null) {
                    alarmManager.setWindow(AlarmManager.RTC_WAKEUP, (calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY), AlarmManager.INTERVAL_DAY, pi2);
                }
            }
            writeFile(Environment.getExternalStorageDirectory() + File.separator + "recordfile.txt", "定时时间为今天" + msg.what + ":" + (msg.arg1 + 2));

        }
    };
}
