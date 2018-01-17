package cn.alpha2j.schedule.time;

import org.junit.Test;

import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;

/**
 * @author alpha
 */
public class ScheduleDateTimeTest {

    @Test
    public void firstTest() {
        ScheduleDateTime scheduleDateTime = ScheduleDateTime.now();
        System.out.println(scheduleDateTime.getYear() + " " + scheduleDateTime.getMonthOfYear() + " " + scheduleDateTime.getDayOfMonth()
        + " " + scheduleDateTime.getHourOfDay() + " " + scheduleDateTime.getMinuteOfHour() + " " + scheduleDateTime.getSecondOfMinute() + " " + scheduleDateTime.getMillisOfSecond());

        System.out.println("getEpochSecond(): " + scheduleDateTime.getEpochSecond());
        System.out.println("getEpochMillisecond(): " + scheduleDateTime.getEpochMillisecond());
        System.out.println("scheduleDateTime: " + scheduleDateTime);

        System.out.println("--------------------divider--------------------------");

        ScheduleDateTime scheduleDateTime1 = ScheduleDateTime.of(scheduleDateTime.getEpochMillisecond());
        System.out.println("scheduleDateTime1.getEpochSecond(): " + scheduleDateTime1.getEpochSecond());
        System.out.println("scheduleDateTime1.getEpochMillisecond(): " + scheduleDateTime1.getEpochMillisecond());
        System.out.println("scheduleDateTime1: " + scheduleDateTime1);
    }

    @Test
    public void testDayNumber() {
        ScheduleDateTime scheduleDateTime = DefaultScheduleDateBuilder.now().toDate(2017, 2, 5).getResult();
        System.out.println("这个月的天数是: " + scheduleDateTime.getMonthDayNumber());
    }
}
