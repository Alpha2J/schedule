package cn.alpha2j.schedule.data.repository.impl;

import java.util.List;

import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.entity.TaskEntityDao;
import cn.alpha2j.schedule.data.repository.TaskRepository;
import cn.alpha2j.schedule.data.repository.base.GreenDAOGenericRepository;

/**
 * 使用单例模式
 *
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskRepositoryImpl extends GreenDAOGenericRepository<TaskEntity, TaskEntityDao> implements TaskRepository {

    private static TaskRepository taskRepository;

    private TaskRepositoryImpl() {
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
    public List<TaskEntity> findTaskEntitiesByTime(long time) {

//        不清楚这个lib在没有结果时返回的是null还是size为0的list, 以后如果发现是null那么记得回来这里更改
//        new一个ArrayList然后再addAll, 最后return, 下面方法也是一样
//        结论: 返回的是size == 0 的ArrayList
        return mDAO.queryBuilder()
                .where(TaskEntityDao.Properties.Time.eq(time))
                .list();
    }

    @Override
    public List<TaskEntity> findTaskEntitiesByTimeAndDone(long time, boolean done) {

        return mDAO.queryBuilder()
                .where(TaskEntityDao.Properties.Time.eq(time), TaskEntityDao.Properties.Done.eq(done))
                .list();
    }

    @Override
    public List<TaskEntity> findTaskEntitiesByTimeBetween(long startTime, long endTime) {

        return mDAO.queryBuilder()
                .where(TaskEntityDao.Properties.Time.between(startTime, endTime))
                .list();
    }

    @Override
    public List<TaskEntity> findTaskEntitiesByDoneAndTimeBetween(boolean done, long startTime, long endTime) {

        return mDAO.queryBuilder()
                .where(TaskEntityDao.Properties.Time.between(startTime, endTime), TaskEntityDao.Properties.Done.eq(done))
                .list();
    }

    @Override
    public long countTaskEntitiesByTimeAndDone(long time, boolean done) {

        return mDAO.queryBuilder().where(TaskEntityDao.Properties.Time.eq(time), TaskEntityDao.Properties.Done.eq(done))
                .count();
    }

    @Override
    public long countTaskEntitiesByDoneAndTimeBetween(boolean done, long startTime, long endTime) {

        return mDAO.queryBuilder()
                .where(TaskEntityDao.Properties.Time.between(startTime, endTime), TaskEntityDao.Properties.Done.eq(done))
                .count();
    }
}
