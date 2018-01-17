package cn.alpha2j.schedule.data.service;

import java.util.List;

import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.exception.PrimaryKeyNotExistException;

/**
 * @author alpha
 */
public interface TaskService {

    /**
     * 增加新的Task
     *
     * @param task 需要增加的task, 不能为null
     * @return 增加成功的话返回task的id, 失败返回-1
     * @throws NullPointerException task为null
     */
    long addTask(Task task);

    /**
     * 增加一个Task或者更新该Task
     *
     * @param task 不能为null
     * @return 增加或更新后对应实体的id
     * @throws NullPointerException task为null
     */
    long addOrUpdateTask(Task task);

    /**
     * 查找属于当天的所有未完成的Task
     * @return 如果没有找到, 那么返回的 list.size() 为空
     */
    List<Task> findAllUnfinishedForToday();

    /**
     * 查找属于当天的所有已经完成的Task
     * @return 如果没有找到, 那么返回的 list.size() 为空
     */
    List<Task> findAllFinishedForToday();

    /**
     * 查找属于某年和某月的所有数据, 包括已经完成的和没有完成的
     * @param year 年
     * @param monthOfYear 月份 1到12
     * @return list.size()不为null
     */
    List<Task> findAllForYearAndMonth(int year, int monthOfYear);

    /**
     * 将该Task的状态设置为已完成
     * @param task 需要设置的task, 不能为空
     * @throws NullPointerException 参数task为空
     * @throws PrimaryKeyNotExistException 传入的参数不存在标识主键(id)
     * @throws IllegalArgumentException 传入的参数的标识主键id小于0
     */
    void setDone(Task task);

    /**
     * 将Task的状态设置为未完成
     * @param task 将task设置为未完成, 不能为空
     * @throws NullPointerException 参数task为空
     * @throws PrimaryKeyNotExistException 传入的参数不存在标识主键(id)
     * @throws IllegalArgumentException 传入的参数的标识主键id小于0
     */
    void setUnDone(Task task);

    int countFinishedForDate(int year, int monthOfYear, int dayOfMonth);

    int countUnfinishedForDate(int year, int monthOfYear, int dayOfMonth);
}
