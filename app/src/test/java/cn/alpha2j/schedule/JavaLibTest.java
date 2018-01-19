package cn.alpha2j.schedule;

import org.junit.Test;

import java.util.Random;

/**
 * @author alpha
 */
public class JavaLibTest {

    @Test
    public void testRandom() {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
//            生成的值是0 到 11, 所以要生成1到12月份的随机值的话要加上1
//            int month = random.nextInt(12);
//            System.out.println(month);

            boolean randomBool = random.nextBoolean();
            System.out.println(randomBool);
        }

    }

    @Test
    public void testNum() {
        System.out.println((10.0 / 100.0) * 100);
    }
}
