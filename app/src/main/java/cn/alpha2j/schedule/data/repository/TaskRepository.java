package cn.alpha2j.schedule.data.repository;

import java.util.List;

import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.entity.TaskEntityDao;
import cn.alpha2j.schedule.data.repository.base.GenericRepository;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public interface TaskRepository extends GenericRepository<TaskEntity, TaskEntityDao> {

    /**
     * 获取指定时间点的所有任务
     *
     * @param time 时间点的utc时间表示毫秒数
     * @return 获得的任务, 不会为null, 如果没有结果, 那么返回的list的size==0
     */
    List<TaskEntity> findTaskEntitiesByTime(long time);

    /**
     * 获取指定时间点的和(已经完成的/未完成的)任务
     *
     * @param time utc时间毫秒数
     * @param done 已完成/未完成
     * @return 结果集, 永远不会为null
     */
    List<TaskEntity> findTaskEntitiesByTimeAndDone(long time, boolean done);

    /**
     * 获取time在startTime到endTime范围内的所有任务(包括startTime和endTime)
     *
     * @param startTime 开始时间 距离utc毫秒数
     * @param endTime 结束时间
     * @return 结果集, 永远不会为null, 只会size == 0
     */
    List<TaskEntity> findTaskEntitiesByTimeBetween(long startTime, long endTime);

    /**
     * 获取time在startTime到endTime范围内的所有任务(包括startTime和endTime) 且符合done参数
     *
     * @param done 已完成/未完成
     * @param startTime 开始时间 距离utc毫秒数
     * @param endTime 结束时间
     * @return 结果集, 永远不会为null, 只会size == 0
     */
    List<TaskEntity> findTaskEntitiesByDoneAndTimeBetween(boolean done, long startTime, long endTime);

    /**
     * 获取指定时间点的完成或者未完成的任务数量
     *
     * @param time utc时间毫秒数
     * @param done 是否已经完成
     * @return 符合条件的数量, 如果没找到返回0
     */
    long countTaskEntitiesByTimeAndDone(long time, boolean done);

    /**
     * 时间区间内的完成或未完成任务数量
     *
     * @param done 已完成/未完成
     * @param startTime 开始时间 距离utc毫秒数
     * @param endTime 结束时间
     * @return 结果集, 永远不会为null, 只会size == 0
     */
    long countTaskEntitiesByDoneAndTimeBetween(boolean done, long startTime, long endTime);
}
