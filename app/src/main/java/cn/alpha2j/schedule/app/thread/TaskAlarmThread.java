package cn.alpha2j.schedule.app.thread;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.LinkedList;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.app.util.TaskAlarm;
import cn.alpha2j.schedule.entity.Task;

/**
 * @author alpha
 */
public class TaskAlarmThread extends Thread {

    private TaskAlarm taskAlarm;
    private Context context;

    public TaskAlarmThread(TaskAlarm taskAlarm, Context context) {
        this.taskAlarm = taskAlarm;
        this.context = context;
    }

    @Override
    public void run() {
        while(true) {
            if(taskAlarm.isTaskEmpty()) {
                try {
                    Thread.sleep(120*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            LinkedList<Task> resultList = taskAlarm.getTaskListAndRemove();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(Constants.TASK_TIME_OUT_RECEIVER_ACTION);
            PendingIntent pendingIntent;

            for (Task task : resultList) {
                //这里第二个参数必须不同, 这样才能为不同的任务设置不同的提醒
                pendingIntent = PendingIntent.getBroadcast(context, task.getId(), intent, 0);
                intent.putExtra("task", task);

                alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getAlarmDateTime().getTime(), pendingIntent);
            }
        }
    }
}
