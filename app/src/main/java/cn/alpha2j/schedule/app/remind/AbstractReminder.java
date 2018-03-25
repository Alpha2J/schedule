package cn.alpha2j.schedule.app.remind;

import android.content.Context;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.app.ui.entity.ReminderSetting;
import cn.alpha2j.schedule.app.ui.helper.ApplicationSettingHelper;

/**
 * @author alpha
 *         Created on 2018/3/25.
 */
public abstract class AbstractReminder implements Reminder {

    protected ReminderSetting mReminderSetting;
    protected Context mContext;

    public AbstractReminder() {
        mReminderSetting = ApplicationSettingHelper.getReminderSetting();
        mContext = MyApplication.getContext();
    }

    public AbstractReminder(ReminderSetting reminderSetting, Context context) {
        this.mReminderSetting = reminderSetting;
        this.mContext = context;
    }
}
