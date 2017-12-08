package cn.alpha2j.schedule.time.builder;

/**
 * @author alpha
 */
public interface ScheduleDateTimeBuilder extends BaseScheduleDateTimeBuilder {
    /**
     * 将时间和日期设置为指定值
     *
     * @param year 年
     * @param monthOfYear 月
     * @param dayOfMonth 日
     * @param hourOfDay 小时
     * @param minuteOfHour 分钟
     * @param secondOfMinute 秒
     * @return 当前对象
     * @throws IllegalArgumentException 如果参数不合法
     */
    ScheduleDateTimeBuilder toDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute);
}
