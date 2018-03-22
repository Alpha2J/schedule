package cn.alpha2j.schedule.app.remind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.data.Task;

/**
 * 需要提醒的任务会添加到这个队列中保存, 但是不会立即添加到AlarmManager中, 等到距离提醒时间还有15 秒的时候才会添加到AlarmManager进行提醒.
 *
 * @author alpha
 *         Created on 2018/3/22.
 */
public class RemindManager {

    private static final String TAG = "RemindManager";

    private static volatile LinkedList<Task> sTasksNotAddedToAlarm = new LinkedList<>();
    private static volatile LinkedList<Task> sTasksAddedToAlarm = new LinkedList<>();

    /**
     * 将一个任务添加到管理器
     *
     * @param task 被添加的任务
     */
    public synchronized static void add(Task task) {

        if (task == null || !task.isRemind() || task.getRemindTime() == null) {
            Log.d(TAG, "add: object task illegal, quit adding to the queue.");
            return;
        }

//        如果提醒时间已经过期, 那么不添加
        if (task.getRemindTime().getEpochMillisecond() < System.currentTimeMillis()) {
            Log.d(TAG, "add: remind time of task has been expired, quit adding to the queue.");
            return;
        }

//        直接添加到管理器中
        if (!sTasksNotAddedToAlarm.contains(task) && !sTasksAddedToAlarm.contains(task)) {
            sTasksNotAddedToAlarm.add(task);
            Log.d(TAG, "add: a new task has been added to the queue. id is: " + task.getId());
        }
    }

    /**
     * 将任务从提醒管理器中删除
     * 如果任务存在于 sTasksNotAddedToAlarm 中, 那么直接删除
     * 如果任务存在于 sTasksAddedToAlarm 中, 那么需要取消提醒, 然后再删除
     *
     * @param task 需要移除提醒的任务
     */
    public synchronized static void remove(Task task) {

        if (task == null) {
            Log.d(TAG, "remove: an empty object was passed in, nothing has been removed.");
            return;
        }

        if (sTasksNotAddedToAlarm.contains(task)) {
            int point = -1;
            for (int i = 0; i < sTasksNotAddedToAlarm.size(); i++) {
                if (sTasksNotAddedToAlarm.get(i).equals(task)) {
                    point = i;
                    break;
                }
            }
            sTasksNotAddedToAlarm.remove(point);
            Log.d(TAG, "remove: a task was removed from sTasksNotAddedToAlarm Queue. id is: " + task.getId());
        } else if (sTasksAddedToAlarm.contains(task)) {
//            如果存在于已添加到alarm manager的队列中, 那么还需要将它从manager中移除.
            int point = -1;
            for (int i = 0; i < sTasksAddedToAlarm.size(); i++) {
                if (sTasksAddedToAlarm.get(i).equals(task)) {
                    point = i;
                    break;
                }
            }

            removeFromAlarmManager(sTasksAddedToAlarm.remove(point));
            Log.d(TAG, "remove: a task was removed from sTasksAddedToAlarm Queue. id is: " + task.getId());
        }
    }

    /**
     * 如果当前时间距离task的提醒时间在20秒内, 那么将task添加到AlarmManager中.
     * 如果task的提醒时间超过了当前时间, 那么将该task从管理器中移除
     */
    public synchronized static void refreshRemind() {

        List<Task> addedTasks = new ArrayList<>();
        List<Task> expiredTasks = new ArrayList<>();
        for (Task t : sTasksNotAddedToAlarm) {
            long currentTime = System.currentTimeMillis();
            long distance = t.getRemindTime().getEpochMillisecond() - currentTime;
            if (distance > 0 && distance < 20 * 1000) {
                addToAlarmManager(t);
                addedTasks.add(t);
                break;
            } else if (distance < 0) {
//                如果提醒时间已经过期了, 那么保存到待移除队列, 稍后移除
                expiredTasks.add(t);
            }
        }

        if (addedTasks.size() != 0) {
            Log.d(TAG, "refreshRemind: " + addedTasks.size() + " tasks were added to the alarm manager.");
            sTasksNotAddedToAlarm.removeAll(addedTasks);
            sTasksAddedToAlarm.addAll(addedTasks);
        }

        if (expiredTasks.size() != 0) {
            Log.d(TAG, "refreshRemind: " + expiredTasks.size() + " tasks were expired.");
            sTasksNotAddedToAlarm.removeAll(expiredTasks);
        }
    }

    private static void addToAlarmManager(Task task) {
        AlarmManager alarmManager = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Reminder.TASK_TIME_OUT_RECEIVER_ACTION);
//        如果用这种方式传对象过去根本行不通, 广播接收器获取不到值, google了半天, 发现是安卓7.0通病
//        intent.putExtra("task", task);
//        所以要用这种方式传值
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        intent.putExtra("bundle", bundle);

//        这里第二个参数必须不同, 这样才能为不同的任务设置不同的提醒
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), task.getId().intValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, task.getRemindTime().getEpochMillisecond(), pendingIntent);
        Log.d(TAG, "addToAlarmManager: a task has been added to alarm manager. task id is : " + task.getId());
    }

    private static void removeFromAlarmManager(Task task) {

        AlarmManager alarmManager = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Reminder.TASK_TIME_OUT_RECEIVER_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), task.getId().intValue(), intent, 0);
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "removeFromAlarmManager: a task has been removed from alarm manager. task id is : " + task.getId());
    }
}
