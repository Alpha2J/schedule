package cn.alpha2j.schedule.time;

import org.junit.Test;

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
}
