package cn.alpha2j.schedule.data.repository.impl;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.entity.TaskEntityDao;
import cn.alpha2j.schedule.data.repository.TaskRepository;
import cn.alpha2j.schedule.data.repository.base.GreenDAOGenericRepository;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskRepositoryImpl extends GreenDAOGenericRepository<TaskEntity, TaskEntityDao> implements TaskRepository {

    private static TaskRepository taskRepository;

    public TaskRepositoryImpl() {
    }

    public static TaskRepository getInstance() {
        if(taskRepository == null) {
            synchronized (TaskRepositoryImpl.class) {
                if(taskRepository == null) {
                    taskRepository = new TaskRepositoryImpl();
                }
            }
        }

        return taskRepository;
    }

    @Override
    public List<TaskEntity> findTaskEntitiesByTaskDate(long taskDate) {

        return mDAO.queryBuilder()
                .where(TaskEntityDao.Properties.TaskDate.eq(taskDate))
                .list();
    }

    @Override
    public List<TaskEntity> findTaskEntitiesByTaskDateAndDone(long taskDate, boolean done) {

        QueryBuilder<TaskEntity> queryBuilder = mDAO.queryBuilder();
        queryBuilder.where(TaskEntityDao.Properties.TaskDate.eq(taskDate), TaskEntityDao.Properties.Done.eq(done));

        return queryBuilder.list();
    }
}
