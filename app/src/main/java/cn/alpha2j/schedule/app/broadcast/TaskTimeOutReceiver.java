package cn.alpha2j.schedule.app.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.Task;

/**
 * 任务的提醒时间到了就会发送一条广播, 用这个广播接收器来进行接收.
 *
 * @author alpha
 * Created on 2017/11/14.
 */
public class TaskTimeOutReceiver extends BroadcastReceiver {

    private static final String TAG = "TaskTimeOutReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if(intent == null) {
            Log.d(TAG, "onReceive: intent为null, 结束方法.");
            return;
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
//        这种方式在安卓7.0是获取不到值的
//        Task task = (Task) intent.getSerializableExtra("task");
//        用这种方式获取
        Bundle bundle = intent.getBundleExtra("bundle");
        Task task = (Task) bundle.getSerializable("task");
        if (task == null) {
            notification = new NotificationCompat.Builder(context, "0")
                    .setContentTitle("出问题了? title")
                    .setContentText("text")
                    .setSmallIcon(R.drawable.ic_account_circle)
                    .build();
            notificationManager.notify(0, notification);
        } else {
            notification = new NotificationCompat.Builder(context, String.valueOf(task.getId()))
                    .setContentTitle("任务时间到了, 你完成了吗")
                    .setContentText(task.getTitle())
                    .setSmallIcon(R.drawable.ic_account_circle)
                    .build();
            notificationManager.notify(task.getId().intValue(), notification);
        }
    }
}
