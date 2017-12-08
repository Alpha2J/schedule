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
     * 获取scheduleDateTime的秒数
     * @return 从1970-01-01T00:00:00Z 距离现在的时间秒数
     */
    public long getEpochSecond() {
        return mInstant.getMillis() / 1000;
    }

    /**
     * 获取该scheduleDateTime的毫秒数
     * @return 从1970-01-01T00:00:00Z 距离现在的时间毫秒数
     */
    public long getEpochMillisecond() {
        return mInstant.getMillis();
    }

    /**
     * 获取该scheduleDateTime所在时区的年份
     * @return 所表示的年份
     */
    public int getYear() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getYear();
    }

    public int getMonthOfYear() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getMonthOfYear();
    }

    public int getDayOfMonth() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getDayOfMonth();
    }

    public int getHourOfDay() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getHourOfDay();
    }

    public int getMinuteOfHour() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getMinuteOfHour();
    }

    public int getSecondOfMinute() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getSecondOfMinute();
    }

    public int getMillisOfSecond() {
        LocalDateTime localDateTime = new LocalDateTime(getEpochMillisecond());

        return localDateTime.getMillisOfSecond();
    }
}
