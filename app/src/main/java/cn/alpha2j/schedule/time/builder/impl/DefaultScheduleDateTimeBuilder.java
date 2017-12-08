package cn.alpha2j.schedule.time.builder.impl;

import org.joda.time.DateTime;

import cn.alpha2j.schedule.exception.NothingWasBuildException;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.ScheduleDateBuilder;
import cn.alpha2j.schedule.time.builder.ScheduleDateTimeBuilder;
import cn.alpha2j.schedule.time.builder.ScheduleTimeBuilder;

/**
 * @author alpha
 */
public class DefaultScheduleDateTimeBuilder implements ScheduleDateTimeBuilder {

    private ScheduleDateTime mScheduleDateTime;

    /**
     * 不允许用new来生成对象.
     */
    private DefaultScheduleDateTimeBuilder() {}

    private DefaultScheduleDateTimeBuilder(ScheduleDateTime scheduleDateTime) {
        mScheduleDateTime = scheduleDateTime;
    }

    /**
     * 以当前时间为基础构建建造器
     *
     * @return 以当前时间为基础的ScheduleDateTimeBuilder对象
     */
    public static DefaultScheduleDateTimeBuilder now() {
        return new DefaultScheduleDateTimeBuilder(ScheduleDateTime.now());
    }

    public static DefaultScheduleDateTimeBuilder of(ScheduleDateTime scheduleDateTime) {
        return new DefaultScheduleDateTimeBuilder(scheduleDateTime);
    }

    @Override
    public ScheduleDateTimeBuilder toDateTime(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
        DateTime dateTime = new DateTime(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute);

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleDateTimeBuilder buildDateAndTime() {
        return this;
    }

    @Override
    public ScheduleDateBuilder buildDate() {
        return DefaultScheduleDateBuilder.of(mScheduleDateTime);
    }

    @Override
    public ScheduleTimeBuilder buildTime() {
        return DefaultScheduleTimeBuilder.of(mScheduleDateTime);
    }

    @Override
    public ScheduleDateTime getResult() {
        if (mScheduleDateTime == null) {
            throw new NothingWasBuildException("没有调用任何建造方法. 在调用该方法前应该调用建造方法建造对象.");
        }

        return mScheduleDateTime;
    }
}
