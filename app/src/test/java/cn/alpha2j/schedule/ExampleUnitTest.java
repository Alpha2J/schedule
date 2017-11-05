package cn.alpha2j.schedule;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        System.out.println(calendar.toString());

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        System.out.println(calendar.toString());
        System.out.println(calendar.getTime().getTime());
    }

    @Test
    public void testToday() {
        System.out.println("System.currentTimeMillis: " + System.currentTimeMillis());
        System.out.println();

        Date date = new Date();
        System.out.println("Date.getTime(): " + date.getTime());
        System.out.println();

        Calendar calendar = Calendar.getInstance();
        System.out.println("calendar year: " + calendar.get(Calendar.YEAR)
        + " calendar month: " + calendar.get(Calendar.MONTH)
        + " calendar day: " + calendar.get(Calendar.DATE)
        + " calendar hour: " + calendar.get(Calendar.HOUR_OF_DAY)
        + " calendar minute: " + calendar.get(Calendar.MINUTE)
        + " calendar second: " + calendar.get(Calendar.SECOND)
        + " calendar.getTimeInMillis(): " + calendar.getTimeInMillis()
        + " calendar.getTime().getTime(): " + calendar.getTime().getTime());
        System.out.println();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        System.out.println("after calendar.set(Calendar.HOUR_OF_DAY, 0): ");
        System.out.println("calendar year: " + calendar.get(Calendar.YEAR)
                + " calendar month: " + calendar.get(Calendar.MONTH)
                + " calendar day: " + calendar.get(Calendar.DATE)
                + " calendar hour: " + calendar.get(Calendar.HOUR_OF_DAY)
                + " calendar minute: " + calendar.get(Calendar.MINUTE)
                + " calendar second: " + calendar.get(Calendar.SECOND)
                + " calendar.getTimeInMillis(): " + calendar.getTimeInMillis()
                + " calendar.getTime().getTime(): " + calendar.getTime().getTime());

        System.out.println("set minute to 0");
        calendar.set(Calendar.MINUTE, 0);
        System.out.println("after set minute to 0, timeMillis is: " + calendar.getTimeInMillis());

    }
}