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

//        不清楚这个lib在没有结果时返回的是null还是size为0的list, 以后如果发现是null那么记得回来这里更改
//        new一个ArrayList然后再addAll, 最后return, 下面方法也是一样
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
