package cn.alpha2j.schedule.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 换用GreenDao框架, 不再使用自己写的持久层代码
 * @author alpha
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableName {

    /**
     * 表示实体的数据表名
     *
     * @return 数据表名
     */
    String value();
}
