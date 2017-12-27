package cn.alpha2j.schedule.data.repository;

import java.util.List;

import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.repository.base.GenericRepository;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public interface TaskRepository extends GenericRepository<TaskEntity, Long> {

    /**
     * 根据任务的日期获取所有属于当天的任务
     * @param taskDate
     * @return 获得的任务
     */
    List<TaskEntity> findTaskEntitiesByTaskDate(long taskDate);

    /**
     * 获取指定日期的是否已经完成的任务
     *
     * @param taskDate
     * @param done
     * @return
     */
    List<TaskEntity> findTaskEntitiesByTaskDateAndDone(long taskDate, boolean done);

    /**
     * 更新任务实体
     *
     * @param id
     * @param field
     * @param value
     */
    void updateTaskEntity(long id, String field, Object value);
}
