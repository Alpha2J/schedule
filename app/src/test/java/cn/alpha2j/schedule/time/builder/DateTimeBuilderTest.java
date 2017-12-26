package cn.alpha2j.schedule.time.builder;

import org.junit.Test;

import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateTimeBuilder;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleTimeBuilder;

/**
 * @author alpha
 */
public class DateTimeBuilderTest {
    @Test
    public void firstTest() {
        ScheduleDateTime scheduleDateTime = DefaultScheduleDateTimeBuilder.now().getResult();
        System.out.println(scheduleDateTime.getYear() + " " + scheduleDateTime.getMonthOfYear() + " " + scheduleDateTime.getDayOfMonth()
                + " " + scheduleDateTime.getHourOfDay() + " " + scheduleDateTime.getMinuteOfHour() + " " + scheduleDateTime.getSecondOfMinute() + " " + scheduleDateTime.getMillisOfSecond());

        System.out.println("getEpochSecond(): " + scheduleDateTime.getEpochSecond());
        System.out.println("getEpochMillisecond(): " + scheduleDateTime.getEpochMillisecond());
        System.out.println("scheduleDateTime: " + scheduleDateTime);


        scheduleDateTime = DefaultScheduleDateTimeBuilder.of(scheduleDateTime).toDateTime(2017, 03, 22, 13, 22, 10).getResult();
        System.out.println(scheduleDateTime.getYear() + " " + scheduleDateTime.getMonthOfYear() + " " + scheduleDateTime.getDayOfMonth()
                + " " + scheduleDateTime.getHourOfDay() + " " + scheduleDateTime.getMinuteOfHour() + " " + scheduleDateTime.getSecondOfMinute() + " " + scheduleDateTime.getMillisOfSecond());

        System.out.println("getEpochSecond(): " + scheduleDateTime.getEpochSecond());
        System.out.println("getEpochMillisecond(): " + scheduleDateTime.getEpochMillisecond());
        System.out.println("scheduleDateTime: " + scheduleDateTime);
    }

    @Test
    public void testDateTimeBuilder() {
        ScheduleDateTimeBuilder dateTimeBuilder = DefaultScheduleDateTimeBuilder.now();
        System.out.println(dateTimeBuilder.getResult());

        ScheduleDateTime scheduleDateTime = dateTimeBuilder.toDateTime(1996, 07, 11, 02, 10, 10).getResult();
        System.out.println(scheduleDateTime);

        ScheduleDateBuilder scheduleDateBuilder = dateTimeBuilder.buildDate();
        ScheduleTimeBuilder scheduleTimeBuilder = dateTimeBuilder.buildTime();
        System.out.println(scheduleDateBuilder.getResult());
        System.out.println(scheduleTimeBuilder.getResult());
    }

    @Test
    public void testDateBuilder() {
        ScheduleDateBuilder scheduleDateBuilder = DefaultScheduleDateBuilder.now();
        ScheduleDateTime scheduleDateTime = scheduleDateBuilder.toDateBegin().getResult();
        System.out.println(scheduleDateTime.getEpochMillisecond());
        System.out.println(scheduleDateTime.getEpochSecond());
        System.out.println(scheduleDateTime);
    }

    @Test
    public void testTimeBuilder() {
        ScheduleTimeBuilder scheduleTimeBuilder = DefaultScheduleTimeBuilder.now();
        ScheduleDateTime scheduleDateTime = scheduleTimeBuilder.toHourStart().getResult();
        System.out.println(scheduleDateTime);
        scheduleDateTime = scheduleTimeBuilder.toMinute(10).getResult();
        System.out.println(scheduleDateTime);
    }
}
