package cn.alpha2j.schedule.app.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
public class TaskReminder {

    public interface TaskReminderConstant {

        String TASK_TIME_OUT_RECEIVER_ACTION = "cn.alpha2j.schedule.receiver.TASK_TIME_OUT";
    }

    private static final String TAG = "TaskReminder";

    public void remindTask(Task task) {

        AlarmManager alarmManager = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(TaskReminderConstant.TASK_TIME_OUT_RECEIVER_ACTION);
//        如果用这种方式传对象过去根本行不通, 广播接收器获取不到值, google了半天, 发现是安卓7.0通病
//        intent.putExtra("task", task);
//        所以要用这种方式传值
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        intent.putExtra("bundle", bundle);

        //这里第二个参数必须不同, 这样才能为不同的任务设置不同的提醒
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), task.getId().intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getRemindTime().getEpochMillisecond(), pendingIntent);
        Log.d(TAG, "remindTask: 添加了新的任务提醒");
    }

    public void cancelRemindTask(Task task) {

        AlarmManager alarmManager = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(TaskReminderConstant.TASK_TIME_OUT_RECEIVER_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), task.getId().intValue(), intent, 0);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "cancelRemindTask: 取消了任务的提醒");
    }
}
