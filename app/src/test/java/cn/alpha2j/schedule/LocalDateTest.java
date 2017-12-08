package cn.alpha2j.schedule;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alpha
 */
public class LocalDateTest {

    @Test
    public void testLocalDate() {
        //用静态方法of来获取实例
        LocalDate localDate = LocalDate.of(2017, 12, 4);
        //用字段相应的名字来获取字段
        int year = localDate.getYear();
        Month month = localDate.getMonth();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        int len = localDate.lengthOfMonth();
        boolean leap = localDate.isLeapYear();

        System.out.println("year: " + year +
                "month: " + month +
                "dayOfWeek: " + dayOfWeek +
                "len: " + len +
                "leap: " + leap);

        //用静态方法now() 来获取当前日期
        LocalDate today = LocalDate.now();

        //用get方法来获取各个字段
        int getYear = localDate.get(ChronoField.YEAR);
        int getMonth = localDate.get(ChronoField.MONTH_OF_YEAR);
        int getDay = localDate.get(ChronoField.DAY_OF_MONTH);
    }

    @Test
    public void testLocalTime() {
        //两个重载方法来构建LocalTime
        LocalTime hourAndMinute = LocalTime.of(10, 20);
        LocalTime hourMinuteAndSecond = LocalTime.of(10, 20, 22);

        int hour = hourAndMinute.getHour();
        int minute = hourAndMinute.getMinute();
        int second = hourAndMinute.getSecond();
        System.out.println("hour: " + hour + " minute: " + minute + " second: " + second);

        int hour1 = hourMinuteAndSecond.getHour();
        int minute1 = hourMinuteAndSecond.getMinute();
        int second1 = hourMinuteAndSecond.getSecond();
        System.out.println("hour1: " + hour1 + " minute1: " + minute1 + " second1: " + second1);
    }

    @Test
    public void testWithParse() {
        //LocalDate和LocalTime都可以使用静态方法parse来解析
        LocalDate date = LocalDate.parse("2017-12-04");
        LocalTime time = LocalTime.parse("18:22:12");
    }

    @Test
    public void testLocalDateTime() {
        //LocalDateTime 是LocalDate和LocalTime的合体, 同时表示日期和时间, 但是不带有时区信息
        //of创建
        LocalDateTime localDateTime1 = LocalDateTime.of(2017, 12, 4, 10, 2);

        //传入LocalDate和LocalTime参数创建
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.of(date, time);

        //用LocalDate.atTime(hour, minute, second)来创建
        LocalDateTime localDateTime3 = date.atTime(15, 22, 19);

        //用LocalDate.atTime(localTime)创建
        LocalDateTime localDateTime4 = date.atTime(time);

        //用LocalTime.adDate(localDate)创建
        LocalDateTime localDateTime5 = time.atDate(date);

        //转换为LocalDate
        LocalDate date1 = localDateTime1.toLocalDate();
        //转换为LocalTime
        LocalTime time1 = localDateTime1.toLocalTime();
    }

    @Test
    public void testInstant() {
        //Instant的设计初衷是为了给机器使用, 表示Unix元年时间开始所经历的秒数
        //Unix元年: 传统的设定为UTC时区1970年1月1日午夜时分
        //直接用秒数创建
        Instant instant = Instant.ofEpochSecond(3);
        //第一个参数表示秒, 第二个参数表示纳秒
        Instant instant1 = Instant.ofEpochSecond(3, 0);
        Instant instant2 = Instant.ofEpochSecond(2, 1_000_000_000);
        Instant instant3 = Instant.ofEpochSecond(4, -1_000_000_000);

    }

    @Test
    public void testDurationAndPeriod() {
        //获取两个时间之间的duration
        LocalTime time1 = LocalTime.of(10, 2);
        LocalTime time2 = LocalTime.of(18, 22);
        Duration duration1 = Duration.between(time1, time2);

        LocalDateTime dateTime1 = LocalDateTime.now();
        LocalDateTime dateTime2 = LocalDateTime.of(2015, 10, 10, 10, 10);
        Duration duration2 = Duration.between(dateTime1, dateTime2);

        //LocalDateTime是便于人们阅读使用, 而Instant是便于机器处理, 所以在这里不能讲两个混合来比较
        Instant instant1 = Instant.now();
        Instant instant2 = Instant.ofEpochSecond(122);
        Duration duration3 = Duration.between(instant1, instant2);

        //由于Duration类主要用于秒和纳秒衡量时间的长短, 所以不能仅传LocalDate做参数
        //如果需要年月日的方式对多个时间单位建模, 那么使用Period类
        Period tenDays = Period.between(LocalDate.of(2017, 12, 4), LocalDate.of(2017, 12, 14));


        //直接用静态方法来创建Duration和Period而不是用参数之间的差值来创建
        Duration threeMinutes = Duration.ofMinutes(3);
        Duration fourMinutes = Duration.of(4, ChronoUnit.MINUTES);

        Period fiveDays = Period.ofDays(5);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthOneDay = Period.of(2, 6, 1);
    }

    @Test
    public void instantInAction() {
        LocalDateTime localDateTime = LocalDateTime.now();
    }
}
