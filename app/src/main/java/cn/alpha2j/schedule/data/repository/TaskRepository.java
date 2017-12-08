package cn.alpha2j.schedule.data.repository;

import java.util.Date;
import java.util.List;

import cn.alpha2j.schedule.data.entity.Task;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public interface TaskRepository {

    /**
     * 增加任务
     * @param task  需要增加的任务
     * @return 返回插入后的id, 如果为 -1, 说明插入过程出异常
     */
    long addTask(Task task);

    /**
     * 根据日期寻找所有 Task
     * @param date 寻找的任务的日期
     * @return 返回一个 List, 如果没有找到 Task , 那么 list.size() 为0 ,而不是 null
     */
    List<Task> findAllByDate(Date date);

    /**
     * 根据日期查找所有未完成的Task
     * @param date 寻找任务的日期
     * @return 返回一个 List, 如果没有找到 Task , 那么 list.size() 为0 ,而不是 null
     */
    List<Task> findAllUnfinishedByDate(Date date);

    /**
     * 根据日期查找所有已完成的Task
     * @param date
     * @return 返回一个 List, 如果没有找到 Task , 那么 list.size() 为0 ,而不是 null
     */
    List<Task> findAllFinishedByDate(Date date);

    boolean updateIsDone(int id, boolean isDone);
}
