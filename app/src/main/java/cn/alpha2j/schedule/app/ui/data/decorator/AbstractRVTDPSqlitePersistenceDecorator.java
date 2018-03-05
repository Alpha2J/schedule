package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * 全称: AbstractRecyclerViewTaskDataProviderSqlitePersistenceDecorator
 * 数据在操作的时候需要保证前端和数据库的数据同步, 无论增加或删除等操作, Persistence Decorator是对数据库进行操作的
 *
 * @author alpha
 */
public abstract class AbstractRVTDPSqlitePersistenceDecorator extends RVTaskDataProvider {

    protected RVTaskDataProvider mTaskDataProvider;
    protected TaskService mTaskService;

    public AbstractRVTDPSqlitePersistenceDecorator(RVTaskDataProvider taskDataProvider) {
        mTaskDataProvider = taskDataProvider;
        mTaskService = TaskServiceImpl.getInstance();
    }

    @Override
    public int getCount() {
        return mTaskDataProvider.getCount();
    }

    @Override
    public void addItem(RVAbstractData data) {
        Task task = ((RVTaskData)data).getTask();

        switch (getRVTaskDataProviderType()) {
//            如果是添加到未完成的Provider里面, 那么需要将task设置为未完成
            case RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                task.setDone(false);
                break;
            case RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                task.setDone(true);
                break;
            default:
        }

//        执行数据库操作后要将id设置回前端, 如果这个task是新增的, 那么id还是以前的, 如果是更新的, 那么id是以前的.
        long id = mTaskService.addOrUpdateTask(task);
        task.setId(id);

        mTaskDataProvider.addItem(data);
    }

    @Override
    public RVTaskData getItem(int index) {

        return mTaskDataProvider.getItem(index);
    }

    /**
     * 当被装饰者完成item的移除后, 将改变持久化到数据库
     *
     * @param position
     */
    @Override
    public RVTaskData removeItem(int position) {
        RVTaskData lastRemovedItem = mTaskDataProvider.removeItem(position);

//        将数据从未完成删除或者从已经完成删除, 只需要将他们从自己的TaskDataProvider中删除, 然后更新数据
//        将响应数据更新为已完成或者未完成
//        同时还要更新前台的数据
        Task task = lastRemovedItem.getTask();
        switch (getRVTaskDataProviderType()) {
            case RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED:
                task.setDone(true);
                mTaskService.setDone(task);
                break;
            case RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                task.setDone(false);
                mTaskService.setUnDone(task);
                break;
            default:
        }

        return lastRemovedItem;
    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {

        mTaskDataProvider.swapItem(fromPosition, toPosition);
    }

    @Override
    public int undoLastRemoval() {
//        如果要取消上次的移除操作, 那么还要判断DataProvider的类型, 如果是为完成, 那么还要讲任务设置回已完成
//        如果没有上次删除项, 直接返回-1
        RVTaskData rvTaskData = mTaskDataProvider.getLastRemoval();
        if(rvTaskData == null) {
            return -1;
        }
        Task task = rvTaskData.getTask();
        switch (getRVTaskDataProviderType()) {
            case RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                task.setDone(false);
                mTaskService.setUnDone(task);
                break;
            case RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                task.setDone(true);
                mTaskService.setDone(task);
                break;
            default:
        }

        return mTaskDataProvider.undoLastRemoval();
    }

    @Override
    public RVTaskData getLastRemoval() {

        return mTaskDataProvider.getLastRemoval();
    }

    /**
     * 从provider删除后还要从sqlite数据库删除
     * @param position
     * @return
     */
    @Override
    public RVTaskData deleteItem(int position) {

        RVTaskData taskData = mTaskDataProvider.deleteItem(position);

        mTaskService.delete(taskData.getTask());

        return taskData;
    }

    @Override
    public RVTaskData getLastDeletion() {
        return mTaskDataProvider.getLastDeletion();
    }

    /**
     * 获取Decorator的真实类型
     * @return 代表真实类型的string
     */
    public abstract String getRVTaskDataProviderType();
}
