package cn.alpha2j.schedule.app.ui.entity;

/**
 *
 * @author alpha
 *         Created on 2018/3/13.
 */
public class ReminderWrapper {

    private boolean remind;
    private int num;
    private TimeType mTimeType;

    public ReminderWrapper() {
//            默认为不提醒, 且时间类型为分钟
        remind = false;
        num = 0;
        mTimeType = TimeType.MINUTE;
    }

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

    public TimeType getTimeType() {
        return mTimeType;
    }

    public void setTimeType(TimeType timeType) {
        mTimeType = timeType;
    }

    /**
     * 如果设置了提醒, 那么返回提醒的类型和数量构成的毫秒数
     */
    public long getResultAsEpochMills() {
//            如果不提醒那么返回0
        if(remind) {
            switch (mTimeType) {
                case MINUTE:
                    return num * 60 * 1000;
                case HOUR:
                    return num * 60 * 60 * 1000;
                case DAY:
                    return num * 24 * 60 * 60 * 1000;
                default:
                    return num * 60 * 1000;
            }
        } else {
            return 0;
        }
    }

    public enum TimeType {
        MINUTE("分钟"),
        HOUR("小时"),
        DAY("天");

        private String mName;

        TimeType(String name) {
            this.mName = name;
        }

        public String getName() {
            return mName;
        }
    }
}
