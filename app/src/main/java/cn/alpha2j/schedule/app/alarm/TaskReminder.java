package cn.alpha2j.schedule.app.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public void remindeTask(Task task) {

        AlarmManager alarmManager = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(TaskReminderConstant.TASK_TIME_OUT_RECEIVER_ACTION);
        //这里第二个参数必须不同, 这样才能为不同的任务设置不同的提醒
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), (int) task.getId(), intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getTaskAlarmDateTime().getEpochMillisecond(), pendingIntent);
        Log.d(TAG, "remindeTask: 添加了新的任务提醒");
    }

    public void cancelRemindeTask(Task task) {

        AlarmManager alarmManager = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(TaskReminderConstant.TASK_TIME_OUT_RECEIVER_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), (int) task.getId(), intent, 0);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "cancelRemindeTask: 取消了任务的提醒");
    }
}
