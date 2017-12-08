package cn.alpha2j.schedule.time;

import android.support.annotation.NonNull;

import org.joda.time.Instant;
import org.joda.time.LocalDateTime;

/**
 * 表示项目中的时间类, 不能改变日期和时间.
 *
 * @author alpha
 */
public class ScheduleDateTime {
    private Instant mInstant;

    /**
     * 不允许直接用new的方式生成对象
     *
     * @param instant
     */
    private ScheduleDateTime(Instant instant) {
        mInstant = instant;
    }

    @NonNull
    public static ScheduleDateTime now() {
        Instant instant = Instant.now();

        return new ScheduleDateTime(instant);
    }

    @NonNull
    public static ScheduleDateTime of(long epochMilliseconds) {
        Instant instant = new Instant(epochMilliseconds);

        return new ScheduleDateTime(instant);
    }

    /**
     * 以秒为时间表示当前的scheduleDateTime对象
     *
     * @return 从1970-01-01T00:00:00Z 距离现在的时间秒数
     */
    public long getEpochSecond() {
        return mInstant.getMillis() / 1000;
    }

    /**
     * 以毫秒为时间表示当前的scheduleDateTime对象
     *
     * @return 从1970-01-01T00:00:00Z 距离现在的时间毫秒数
     */
    public long getEpochMillisecond() {
        return mInstant.getMillis();
    }

    /**
     * 获取该scheduleDateTime所在时区的年份
     *
     * @return 所在时区的年份
     */
    public int getYear() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getYear();
    }

    /**
     * 获取该scheduleDateTime所在时区的月份
     *
     * @return 所在时区的月份
     */
    public int getMonthOfYear() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getMonthOfYear();
    }

    /**
     * 获取该scheduleDateTime所在时区的日期
     *
     * @return 所在时区的日期
     */
    public int getDayOfMonth() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getDayOfMonth();
    }

    /**
     * 获取该scheduleDateTime所在时区的当前日期的第几个小时
     *
     * @return 所在时区的小时
     */
    public int getHourOfDay() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getHourOfDay();
    }

    /**
     * 获取该scheduleDateTime所在时区的当前时间中的第几分钟
     *
     * @return 所在时区的分钟
     */
    public int getMinuteOfHour() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getMinuteOfHour();
    }

    /**
     * 获取该scheduleDateTime所在时区的当前时间中的第几秒
     *
     * @return 所在时区的秒数
     */
    public int getSecondOfMinute() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getSecondOfMinute();
    }

    /**
     * 获取该scheduleDateTime所在时区的当前时间中的第几毫秒
     *
     * @return 所在时区的月份
     */
    public int getMillisOfSecond() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getMillisOfSecond();
    }

    @Override
    public String toString() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());
        return localDateTime.toString();
    }
}
