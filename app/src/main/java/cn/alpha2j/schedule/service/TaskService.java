package cn.alpha2j.schedule.service;

import java.util.List;

import cn.alpha2j.schedule.entity.Task;

/**
 * @author alpha
 */
public interface TaskService {
    /**
     * 增加新的Task
     * @param task
     * @return 增加失败的话返回false, 成功true
     */
    boolean addTask(Task task);

    /**
     * 查找属于当天的所有Task
     * @return 如果没有找到, 那么返回的 list.size() 为空
     */
    List<Task> findAllForToday();
}
