package cn.alpha2j.schedule.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author alpha
 */
public class DateUtils {

    /**
     * 获取当天 00 : 00 : 00 距离1970年1月1日 00 : 00 : 00 的秒数
     * @return
     */
    public static long generateSecondsForToday() {
        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        return today.getTimeInMillis() / 1000;
    }

    /**
     * 获取指定日期的 00 : 00 : 00 距离1970年1月1日00 : 00 : 00 的秒数
     * @param date
     * @return 如果date为空, 返回-1
     */
    public static long generateSecondsForDate(Date date) {
        if(date == null) {
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis() / 1000;
    }
}
