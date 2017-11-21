package cn.alpha2j.schedule.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author alpha
 */
public class AlarmDateTimeGenerator {

    /**
     * 一天的中午, 表示12点
     */
    private static int NOON = 12;
    /**
     * 一天的傍晚, 表示下午6点
     */
    private static int SUNSET = 18;

    /**
     * 生成当天的提醒时间, 如果当前时间小于中午12点, 那么提醒时间设置为中午12点;
     * 如果当前时间为中午12点过后到傍晚6点, 那么提醒时间设置为傍晚6点;
     * 如果当前时间为傍晚6点之后, 那么不设置提醒时间
     * @return 返回设置好的时间, 如果当前时间为傍晚6点之后, 那么返回null
     */
    public static Date generateAlarmDateTime() {
        Calendar today = Calendar.getInstance();

        int hourOfNow = today.get(Calendar.HOUR_OF_DAY);
        if(hourOfNow < NOON) {
            today.set(Calendar.HOUR_OF_DAY, NOON);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);

            return today.getTime();
        } else if(hourOfNow > NOON && hourOfNow < SUNSET) {
            today.set(Calendar.HOUR_OF_DAY, SUNSET);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);

            return today.getTime();
        } else {
            return null;
        }
    }
}
