package cn.alpha2j.schedule;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class Constants {
    /**
     * bing每日图片
     */
    public static final String BING_DAILY_PICTURE_URL = "http://cn.bing.com/az/hprichbg/rb/HallstattAustria_ROW10550526522_1920x1080.jpg";

    /**
     * 数据库的名字
     */
    public static final String DATABASE_NAME = "Schedule.db";

    /**
     * 数据库的版本
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Task 实体的数据表名
     */
    public static final String TABLE_NAME_TASK = "Schedule_Task";

    /**
     * 任务到期后发出一个广播, 这个就是广播接收器的action的名字
     */
    public static final String TASK_TIME_OUT_RECEIVER_ACTION = "cn.alpha2j.schedule.receiver.TASK_TIME_OUT";
}
