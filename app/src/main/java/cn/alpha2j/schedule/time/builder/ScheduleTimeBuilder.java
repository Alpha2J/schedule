package cn.alpha2j.schedule.time.builder;

/**
 * 对ScheduleDateTime的时间部分进行操作
 *
 * @author alpha
 */
public interface ScheduleTimeBuilder extends BaseScheduleDateTimeBuilder {

    /**
     * 将时间设置为指定值
     *
     * @param hourOfDay
     * @param minuteOfHour
     * @param secondOfMinute
     * @return 当前对象
     */
    ScheduleTimeBuilder toTime(int hourOfDay, int minuteOfHour, int secondOfMinute);

    /**
     * 将时间设置为小时的开始. 比如: xx:00:00 000
     *
     * @return 当前对象
     */
    ScheduleTimeBuilder toHourStart();

    /**
     * 将时间的小时设置为指定值
     *
     * @param hour 需要设置的小时值
     * @return 当前对象
     * @throws IllegalArgumentException 如果参数的值小于0或者大于23
     */
    ScheduleTimeBuilder toHour(int hour);

    /**
     * 将时间设置为分钟的开始. 比如xx:xx:00 000
     *
     * @return 当前对象
     */
    ScheduleTimeBuilder toMinuteStart();

    /**
     * 将时间的分钟设置为指定值
     *
     * @param minute 需要设定的分钟值
     * @return 当前对象
     * @throws IllegalArgumentException 如果参数的值小于0或者大于59
     */
    ScheduleTimeBuilder toMinute(int minute);

    /**
     * 将时间设置为秒的开始. 比如xx:xx:xx 000
     *
     * @return 当前对象
     */
    ScheduleTimeBuilder toSecondStart();

    /**
     * 将时间的秒设置为指定值
     *
     * @param second 需要设定的秒值
     * @return 当前对象
     * @throws IllegalArgumentException 如果参数的值小于0或者大于59
     */
    ScheduleTimeBuilder toSecond(int second);
}
