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
public class DefaultScheduleTimeBuilder implements ScheduleTimeBuilder{

    private ScheduleDateTime mScheduleDateTime;

    private DefaultScheduleTimeBuilder() {

    }

    private DefaultScheduleTimeBuilder(ScheduleDateTime scheduleDateTime) {
        mScheduleDateTime = scheduleDateTime;
    }

    public static ScheduleTimeBuilder now() {
        return new DefaultScheduleTimeBuilder(ScheduleDateTime.now());
    }

    public static ScheduleTimeBuilder of(ScheduleDateTime scheduleDateTime) {
        return new DefaultScheduleTimeBuilder(scheduleDateTime);
    }

    @Override
    public ScheduleTimeBuilder toTime(int hourOfDay, int minuteOfHour, int secondOfMinute) {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), hourOfDay, minuteOfHour, secondOfMinute);

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleTimeBuilder toHourStart() {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), mScheduleDateTime.getHourOfDay(), 0, 0, 0);

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleTimeBuilder toHour(int hour) {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), hour, mScheduleDateTime.getMinuteOfHour(), mScheduleDateTime.getSecondOfMinute(), mScheduleDateTime.getMillisOfSecond());

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleTimeBuilder toMinuteStart() {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), mScheduleDateTime.getHourOfDay(), mScheduleDateTime.getMinuteOfHour(), 0, 0);

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleTimeBuilder toMinute(int minute) {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), mScheduleDateTime.getHourOfDay(), minute, mScheduleDateTime.getSecondOfMinute(), mScheduleDateTime.getMillisOfSecond());

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleTimeBuilder toSecondStart() {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), mScheduleDateTime.getHourOfDay(), mScheduleDateTime.getMinuteOfHour(), mScheduleDateTime.getSecondOfMinute(), 0);

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleTimeBuilder toSecond(int second) {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), mScheduleDateTime.getHourOfDay(), mScheduleDateTime.getMinuteOfHour(), second, mScheduleDateTime.getMillisOfSecond());

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleDateTimeBuilder buildDateAndTime() {
        return DefaultScheduleDateTimeBuilder.of(mScheduleDateTime);
    }

    @Override
    public ScheduleDateBuilder buildDate() {
        return DefaultScheduleDateBuilder.of(mScheduleDateTime);
    }

    @Override
    public ScheduleTimeBuilder buildTime() {
        return this;
    }

    @Override
    public ScheduleDateTime getResult() {
        if (mScheduleDateTime == null) {
            throw new NothingWasBuildException("没有调用任何建造方法. 在调用该方法前应该调用建造方法建造对象.");
        }

        return mScheduleDateTime;
    }
}
