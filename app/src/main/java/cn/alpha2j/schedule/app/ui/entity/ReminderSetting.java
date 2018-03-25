package cn.alpha2j.schedule.app.ui.entity;

/**
 * 表示提醒的设置, 虽然提醒时间部分使用了ReminderWrapper类,
 * 而且这样设计有点不合理, 但是没办法了, 不想改了. 以后有兴趣再改吧.
 *
 * @author alpha
 *         Created on 2018/3/25.
 */
public class ReminderSetting {

    private boolean remind;

    private int num;

    private String remindTimeType;

    private int remindType;

    private String remindNotificationRingtone;

    private boolean remindVibrate;

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRemindTimeType() {
        return remindTimeType;
    }

    public void setRemindTimeType(String remindTimeType) {
        this.remindTimeType = remindTimeType;
    }

    public int getRemindType() {
        return remindType;
    }

    public void setRemindType(int remindType) {
        this.remindType = remindType;
    }

    public String getRemindNotificationRingtone() {
        return remindNotificationRingtone;
    }

    public void setRemindNotificationRingtone(String remindNotificationRingtone) {
        this.remindNotificationRingtone = remindNotificationRingtone;
    }

    public boolean isRemindVibrate() {
        return remindVibrate;
    }

    public void setRemindVibrate(boolean remindVibrate) {
        this.remindVibrate = remindVibrate;
    }
}
