package cn.alpha2j.schedule.time.builder;

/**
 * 对ScheduleDateTime的日期部分进行操作
 *
 * @author alpha
 */
public interface ScheduleDateBuilder extends BaseScheduleDateTimeBuilder {
    /**
     * 将日期设置为指定值
     *
     * @param year 年
     * @param monthOfYear 月
     * @param dayOfMonth 日
     * @return 当前对象
     *
     * @throws IllegalArgumentException 传递了不合法的参数
     */
    ScheduleDateBuilder toDate(int year, int monthOfYear, int dayOfMonth);

    /**
     * 将时间设置为当天的开始. 比如: 2017-12-08T00:00:00.000+08:00
     *
     * @return 当前对象
     */
    ScheduleDateBuilder toDateBegin();

    /**
     * 将年份设置为指定值
     *
     * @param year 年
     * @return 当前对象
     *
     * @throws IllegalArgumentException year参数不合法
     */
    ScheduleDateBuilder toYear(int year);

    /**
     * 将月份设置为指定值
     *
     * @param monthOfYear 需要设置的月份, 1 到 12
     * @return 当前对象
     *
     * @throws IllegalArgumentException monthOfYear参数不合法
     */
    ScheduleDateBuilder toMonthOfYear(int monthOfYear);

    /**
     * 将日期设置为指定值
     *
     * @param dayOfMonth 需要设置的日期, 范围为1 到 31, 根据月份不同相应不同
     * @return 当前对象
     *
     * @throws IllegalArgumentException dayOfMonth参数不合法
     */
    ScheduleDateBuilder toDayOfMonth(int dayOfMonth);
}
