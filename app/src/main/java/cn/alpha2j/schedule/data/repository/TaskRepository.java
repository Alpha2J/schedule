package cn.alpha2j.schedule.data.repository;

import java.util.List;

import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.repository.base.GenericRepository;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public interface TaskRepository extends GenericRepository<TaskEntity, Long> {

    List<TaskEntity> findTaskEntitiesByTaskDate(long taskDate);

    List<TaskEntity> findTaskEntitiesByTaskDateAndDone(long taskDate, boolean done);

    void updateTaskEntity(long id, String field, Object value);
}
