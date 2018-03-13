package cn.alpha2j.schedule.app.ui.helper;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.app.ui.entity.ReminderWrapper;

/**
 * 获取应用的各种设置.
 *
 * @author alpha
 *         Created on 2018/3/13.
 */
public class ApplicationSettingHelper {

    /**
     * 是否从服务器获取数据
     */
    public static final String GET_DATA_FROM_SERVER = "get_data_from_server";
    /**
     * 默认开启提醒
     */
    public static final String OPEN_REMINDER_WHEN_NEW_TASK = "open_reminder_when_new_task";
    /**
     * 提醒提前时间
     */
    public static final String BEFORE_TIME = "before_time";
    /**
     * 提醒方式
     */
    public static final String REMIND_TYPE = "remind_type";
    /**
     * 提醒铃声
     */
    public static final String REMIND_RINGTONE = "remind_ringtone";
    /**
     * 是否震动
     */
    public static final String REMIND_VIBRATE = "remind_vibrate";


    public static ReminderWrapper getReminderSetting() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

        ReminderWrapper reminderWrapper = new ReminderWrapper();
        reminderWrapper.setRemind(sharedPreferences.getBoolean(OPEN_REMINDER_WHEN_NEW_TASK, false));
//        从设置中生成的reminderWrapper的提醒类型都是分钟为单位
        reminderWrapper.setTimeType(ReminderWrapper.TimeType.MINUTE);
        String numStr = sharedPreferences.getString(REMIND_TYPE, "1");
        try {
            reminderWrapper.setNum(Integer.valueOf(numStr));
        } catch (RuntimeException e) {
            reminderWrapper.setNum(1);
        }

        return reminderWrapper;
    }
}
