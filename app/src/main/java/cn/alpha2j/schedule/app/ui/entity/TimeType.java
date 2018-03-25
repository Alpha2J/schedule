package cn.alpha2j.schedule.app.ui.entity;

/**
 * 表示时间类型的类
 *
 * @author alpha
 *         Created on 2018/3/25.
 */
public class TimeType {

    public static final String MINUTE = "分钟";

    public static final String HOUR = "小时";

    public static final String DAY = "天";

    public static boolean isTimeType(String timeType) {

        return !(timeType.equals(MINUTE) && timeType.equals(HOUR) && timeType.equals(DAY));
    }
}