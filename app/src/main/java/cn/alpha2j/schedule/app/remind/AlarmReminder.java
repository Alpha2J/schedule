package cn.alpha2j.schedule.app.remind;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.app.ui.entity.ReminderSetting;
import cn.alpha2j.schedule.data.Task;

/**
 * 使用系统闹钟进行提醒
 *
 * @author alpha
 *         Created on 2018/3/22.
 */
public class AlarmReminder extends AbstractReminder implements Reminder {

    private static final String TAG = "AlarmReminder";

    public AlarmReminder() {

    }

    public AlarmReminder(ReminderSetting reminderSetting, Context context) {
        super(reminderSetting, context);
    }

    @Override
    public void remind(Task task) {

        String des = "";
        if(task.getDescription() != null) {
            if(task.getDescription().length() > 10) {
                des = task.getDescription() + "...";
            } else {
                des = task.getDescription();
            }
        }

        String remindStr = task.getTitle() + "(" + des + ")";

        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_MESSAGE, remindStr)
                .putExtra(AlarmClock.EXTRA_LENGTH, 3)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);

        mContext.startActivity(intent);
    }
}
