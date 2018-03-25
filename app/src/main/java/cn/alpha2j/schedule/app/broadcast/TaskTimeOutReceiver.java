package cn.alpha2j.schedule.app.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.remind.CommonReminder;
import cn.alpha2j.schedule.app.remind.Reminder;
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


//        这种方式在安卓7.0是获取不到值的
//        Task task = (Task) intent.getSerializableExtra("task");
//        用这种方式获取
        Bundle bundle = intent.getBundleExtra("bundle");
        Task task = (Task) bundle.getSerializable("task");
        if (task == null) {
            Log.d(TAG, "onReceive: 获取不到task对象, 是否出现问题了. 方法结束, 不进行提醒.");
        } else {
            new CommonReminder(context).remind(task);
        }
    }
}
