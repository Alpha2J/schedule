package cn.alpha2j.schedule.app.remind;

import android.content.Context;
import android.util.Log;

import cn.alpha2j.schedule.app.ui.entity.RemindType;
import cn.alpha2j.schedule.app.ui.entity.ReminderSetting;
import cn.alpha2j.schedule.app.ui.helper.ApplicationSettingHelper;
import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 *         Created on 2018/3/25.
 */
public class CommonReminder implements Reminder {

    private static final String TAG = "CommonReminder";

    private Context mContext;

    public CommonReminder(Context context) {
        this.mContext = context;
    }

    @Override
    public void remind(Task task) {

        Reminder reminder;

        ReminderSetting reminderSetting = ApplicationSettingHelper.getReminderSetting();
        switch (reminderSetting.getRemindType()) {
            case RemindType.NOTIFICATION:
                Log.d(TAG, "remind: 使用系统通知.");
                reminder = new NotificationReminder(reminderSetting, mContext);
                break;
            case RemindType.SYSTEM_CLOCK:
                Log.d(TAG, "remind: 使用闹铃通知");
                reminder = new AlarmReminder(reminderSetting, mContext);
                break;
            default:
                reminder = new NotificationReminder(reminderSetting, mContext);
                break;
        }

        reminder.remind(task);
    }
}
