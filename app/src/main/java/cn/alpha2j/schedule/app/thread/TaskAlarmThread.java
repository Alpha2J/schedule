package cn.alpha2j.schedule.app.thread;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.LinkedList;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.app.util.TaskAlarm;
import cn.alpha2j.schedule.data.entity.TaskEntity;

/**
 * @author alpha
 */
public class TaskAlarmThread extends Thread {

    private static final String TAG = "TaskAlarmThread";

    private TaskAlarm taskAlarm;
    private Context context;

    public TaskAlarmThread(TaskAlarm taskAlarm, Context context) {
        this.taskAlarm = taskAlarm;
        this.context = context;
    }

    @Override
    public void run() {
//        while(true) {
//            Log.d(TAG, "run: 循环内, 未开始");
//            if(taskAlarm.isTaskEmpty()) {
//                Log.d(TAG, "run: 循环内, 进行为空判断");
//                try {
//                    Thread.sleep(120*1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Log.d(TAG, "run: 开始设置定时任务");
//            LinkedList<TaskEntity> resultList = taskAlarm.getTaskListAndRemove();
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent(Constants.TASK_TIME_OUT_RECEIVER_ACTION);
//            PendingIntent pendingIntent;
//
//            for (TaskEntity task : resultList) {
//                //这里第二个参数必须不同, 这样才能为不同的任务设置不同的提醒
//                pendingIntent = PendingIntent.getBroadcast(context, (int)task.getId(), intent, 0);
//                intent.putExtra("task", task);
//
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getAl, pendingIntent);
//            }
//        }
    }
}
