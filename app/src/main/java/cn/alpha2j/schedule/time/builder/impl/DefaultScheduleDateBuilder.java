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
public class DefaultScheduleDateBuilder implements ScheduleDateBuilder {

    private ScheduleDateTime mScheduleDateTime;

    private DefaultScheduleDateBuilder() {

    }

    private DefaultScheduleDateBuilder(ScheduleDateTime scheduleDateTime) {
        mScheduleDateTime = scheduleDateTime;
    }

    public static DefaultScheduleDateBuilder now() {
        return new DefaultScheduleDateBuilder(ScheduleDateTime.now());
    }

    public static DefaultScheduleDateBuilder of(ScheduleDateTime scheduleDateTime) {
        return new DefaultScheduleDateBuilder(scheduleDateTime);
    }

    @Override
    public ScheduleDateBuilder toDateBegin() {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), 0, 0, 0);

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleDateBuilder toDate(int year, int monthOfYear, int dayOfMonth) {
        DateTime dateTime = new DateTime(year, monthOfYear, dayOfMonth, mScheduleDateTime.getHourOfDay(), mScheduleDateTime.getMinuteOfHour(), mScheduleDateTime.getSecondOfMinute(), mScheduleDateTime.getMillisOfSecond());

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleDateBuilder toYear(int year) {
        DateTime dateTime = new DateTime(year, mScheduleDateTime.getMonthOfYear(), mScheduleDateTime.getDayOfMonth(), mScheduleDateTime.getHourOfDay(), mScheduleDateTime.getMinuteOfHour(), mScheduleDateTime.getSecondOfMinute(), mScheduleDateTime.getMillisOfSecond());

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleDateBuilder toMonthOfYear(int monthOfYear) {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), monthOfYear, mScheduleDateTime.getDayOfMonth(), mScheduleDateTime.getHourOfDay(), mScheduleDateTime.getMinuteOfHour(), mScheduleDateTime.getSecondOfMinute(), mScheduleDateTime.getMillisOfSecond());

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleDateBuilder toDayOfMonth(int dayOfMonth) {
        DateTime dateTime = new DateTime(mScheduleDateTime.getYear(), mScheduleDateTime.getMonthOfYear(), dayOfMonth, mScheduleDateTime.getHourOfDay(), mScheduleDateTime.getMinuteOfHour(), mScheduleDateTime.getSecondOfMinute(), mScheduleDateTime.getMillisOfSecond());

        mScheduleDateTime = ScheduleDateTime.of(dateTime.getMillis());

        return this;
    }

    @Override
    public ScheduleDateTimeBuilder buildDateAndTime() {
        return DefaultScheduleDateTimeBuilder.of(mScheduleDateTime);
    }

    @Override
    public ScheduleDateBuilder buildDate() {
        return this;
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
