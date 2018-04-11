package cn.alpha2j.schedule.app.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.remind.RemindManager;
import cn.alpha2j.schedule.app.ui.activity.MainActivity;

/**
 * @author alpha
 */
public class AlarmService extends Service {

    private static final String TAG = "AlarmService";

    public AlarmService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "alarmServiceNotification")
                .setContentTitle("hello")
                .setContentText("content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.mipmap.ic_launcher_round))
                .setContentIntent(pendingIntent)
                .build();

        startForeground(-1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "run: refreshing remind manager");
                RemindManager.refreshRemind();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
