package cn.alpha2j.schedule.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author alpha
 */
public class DateUtils {

    /**
     * 生成调用这个方法的日期的当天 00:00:00
     * @return
     */
    public static Date generateDateBeginForToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return today.getTime();
    }

    /**
     * 获取这个date对象距离1970年1月1日00 : 00 : 00 的秒数
     * @param date
     * @return 如果参数为空, 返回-1
     */
    public static long generateSecondsForDate(Date date) {
        if(date == null) {
            return -1;
        }

        return date.getTime() / 1000;
    }

    /**
     * 将传入的date对象设置为这个date的当天 00:00:00
     * @param date
     * @return
     */
    public static Date transformDateToDateBegin(Date date) {
        if(date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

}
