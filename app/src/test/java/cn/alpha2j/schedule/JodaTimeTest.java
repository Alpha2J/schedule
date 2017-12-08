package cn.alpha2j.schedule;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.util.Date;

/**
 * @author alpha
 */
public class JodaTimeTest {
    @Test
    public void firstTest() {
//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime.getYear() + " " + localDateTime.getMonthOfYear() + " " + localDateTime.getDayOfMonth() + " " + localDateTime.getHourOfDay());
//        LocalDateTime localDateTime1 = localDateTime.withYear(2015);
//        System.out.println(localDateTime1.getYear() + " " + localDateTime1.getMonthOfYear() + " " + localDateTime1.getDayOfMonth() + " " + localDateTime1.getHourOfDay());

//        DateTime dateTime = new DateTime();
//        DateTime dateTime1 = new DateTime();
//        LocalDateTime localDateTime = new LocalDateTime();
//        System.out.println(localDateTime);
//        System.out.println(dateTime);
//        Date date = new Date();
//        System.out.println(date);

        LocalDateTime localDateTime = new LocalDateTime();
        LocalDateTime localDateTime1 = new LocalDateTime(new Instant().getMillis());
        System.out.println(localDateTime1);
        System.out.println(localDateTime);
    }

    @Test
    public void secondTest() {
        LocalDateTime ldt = new LocalDateTime(-62135596800000L);
        System.out.println(ldt);
//        System.out.println(ldt.getLocalMillis());
    }

    @Test
    public void testLocalDateToInstant() {
//        这种方式转换出来的结果并不准确(错误, 准确, 因为直接输出的Instant是utc时区的时间, 但是millis还是一样的)
        DateTime dateTime = new DateTime();
        System.out.println(dateTime);

        Instant instant = dateTime.toInstant();
        System.out.println(instant);

        System.out.println(instant.toDateTime().toInstant());
    }

    @Test
    public void testInstantToLocalDate() {
        Instant instant = Instant.now();
        System.out.println(instant);

        LocalDate localDate = new LocalDate(instant.getMillis());
        System.out.println(localDate);
    }

    @Test
    public void testTimeZone() {
        DateTimeZone dateTimeZone = DateTimeZone.UTC;

        Instant instant = Instant.now();
        System.out.println(instant);
        System.out.println(instant.getMillis());

        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime());
    }

    @Test
    public void testInstantConstructor() {
        Instant instant = new Instant();
        Instant instant1 = new Instant(new Date().getTime());

        System.out.println(instant.getMillis());
        System.out.println(instant1.getMillis());
    }

    @Test
    public void testAll() {
        DateTime dateTime = new DateTime(2017, 12, 8, 0, 0);
        System.out.println(dateTime);

        Instant instant = dateTime.toInstant();
        System.out.println(instant);

        LocalDateTime localDateTime = new LocalDateTime(instant);
        System.out.println(localDateTime.getYear() + " " + localDateTime.getMonthOfYear() + " " + localDateTime.getDayOfMonth() + " " + localDateTime.getHourOfDay());
        localDateTime = new LocalDateTime(instant.getMillis());
        System.out.println(localDateTime.getYear() + " " + localDateTime.getMonthOfYear() + " " + localDateTime.getDayOfMonth() + " " + localDateTime.getHourOfDay());
    }

}
