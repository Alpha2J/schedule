package cn.alpha2j.schedule.app.remind;

import cn.alpha2j.schedule.data.Task;

/**
 * 提醒器接口, 使用什么方式进行提醒
 *
 * @author alpha
 */
public interface Reminder {

    String TASK_TIME_OUT_RECEIVER_ACTION = "cn.alpha2j.schedule.receiver.TASK_TIME_OUT";

    /**
     * 进行提醒
     * @param task 需要提醒的任务
     */
    void remind(Task task);
}
