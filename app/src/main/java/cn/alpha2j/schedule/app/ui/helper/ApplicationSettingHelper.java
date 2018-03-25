package cn.alpha2j.schedule.app.ui.helper;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.app.ui.entity.ReminderSetting;
import cn.alpha2j.schedule.app.ui.entity.TimeType;

/**
 * 获取应用的各种设置.
 *
 * @author alpha
 *         Created on 2018/3/13.
 */
public class ApplicationSettingHelper {

//    pref_settings.xml文件对应的str
    /**
     * 默认开启提醒
     */
    private static final String SETTING_STR_DEFAULT_OPEN_REMIND = "default_open_remind";
    /**
     * 提醒提前时间
     */
    private static final String SETTING_STR_AHEAD_TIME = "ahead_time";
    /**
     * 提醒方式的类型
     */
    private static final String SETTING_STR_REMIND_TYPE = "remind_type";
    /**
     * 状态栏提醒铃声
     */
    private static final String SETTING_STR_REMIND_NOTIFICATION_RINGTONE = "remind_notification_ringtone";
    /**
     * 是否震动
     */
    private static final String SETTING_STR_REMIND_VIBRATE = "remind_vibrate";
    /**
     * 只在wifi下加载
     */
    public static final String SETTING_STR_DATA_ONLY_WIFI = "data_only_wifi";

//    一般str
    /**
     * 提醒时间的类型, 分钟
     */
    public static final String DEFAULT_REMIND_TIME_TYPE = TimeType.MINUTE;

    public static ReminderSetting getReminderSetting() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

        ReminderSetting reminderSetting = new ReminderSetting();
        reminderSetting.setRemind(sharedPreferences.getBoolean(SETTING_STR_DEFAULT_OPEN_REMIND, false));
        reminderSetting.setRemindTimeType(DEFAULT_REMIND_TIME_TYPE);
        String numStr = sharedPreferences.getString(SETTING_STR_AHEAD_TIME, "1");
        try {
            reminderSetting.setNum(Integer.valueOf(numStr));
        } catch (RuntimeException e) {
            reminderSetting.setNum(1);
        }

//        默认是状态栏通知
        String typeStr = sharedPreferences.getString(SETTING_STR_REMIND_TYPE, "1");
        try {
            reminderSetting.setRemindType(Integer.valueOf(typeStr));
        } catch (RuntimeException e) {
            reminderSetting.setRemindType(1);
        }
        reminderSetting.setRemindNotificationRingtone(sharedPreferences.getString(SETTING_STR_REMIND_NOTIFICATION_RINGTONE, "content://settings/system/notification_sound"));
        reminderSetting.setRemindVibrate(sharedPreferences.getBoolean(SETTING_STR_REMIND_VIBRATE, false));

        return reminderSetting;
    }
}
