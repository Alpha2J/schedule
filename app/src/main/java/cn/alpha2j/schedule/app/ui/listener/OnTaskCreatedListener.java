package cn.alpha2j.schedule.app.ui.listener;

import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 *         Created on 2018/3/13.
 */
public interface OnTaskCreatedListener {

    /**
     * 获取到用户输入的信息后回调
     *
     * @param task
     */
    void onTaskCreated(Task task);
}