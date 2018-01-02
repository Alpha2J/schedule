package cn.alpha2j.schedule.time.builder;

import cn.alpha2j.schedule.exception.NothingWasBuildException;
import cn.alpha2j.schedule.time.ScheduleDateTime;

/**
 * @author alpha
 */
public interface BaseScheduleDateTimeBuilder {

    /**
     * 获得一个ScheduleDateTimeBuilder 对象
     *
     * @return ScheduleDateTimeBuilder对象
     */
    ScheduleDateTimeBuilder buildDateAndTime();

    /**
     * 获得一个ScheduleDateBuilder 对象
     *
     * @return ScheduleDateBuilder对象
     */
    ScheduleDateBuilder buildDate();

    /**
     * 获得一个ScheduleTimeBuilder 对象
     *
     * @return ScheduleTimeBuilder对象
     */
    ScheduleTimeBuilder buildTime();

    /**
     * 获取建造器生成的结果
     *
     * @return 生成的结果
     * @throws NothingWasBuildException 没有调用过构建方法构建过所需结果
     */
    ScheduleDateTime getResult();
}
