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
     * 根据任务的日期获取所有属于当天的任务
     * @param taskDate 日期的utc时间表示毫秒数
     * @return 获得的任务, 不会为null, 如果没有结果, 那么size为0
     */
    List<TaskEntity> findTaskEntitiesByTaskDate(long taskDate);

    /**
     * 获取指定日期的是否已经完成的任务
     *
     * @param taskDate utc时间毫秒数
     * @param done 是否已完成
     * @return 结果集, 永远不会为null
     */
    List<TaskEntity> findTaskEntitiesByTaskDateAndDone(long taskDate, boolean done);
}
