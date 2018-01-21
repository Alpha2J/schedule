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

    /**
     * 测试当switch的参数为string类型, 且为null时, 会不会抛出异常
     *
     * 结果, 如果为null, 直接抛空指针异常
     */
    @Test
    public void testSwitchNull() {

        String str = null;
        switch (str) {
            case "str":
                System.out.println("str");
                break;
            default:
                System.out.println("in default");

        }
    }
}
