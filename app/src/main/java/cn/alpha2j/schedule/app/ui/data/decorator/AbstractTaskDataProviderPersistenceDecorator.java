package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * 数据在操作的时候需要保证前端和数据库的数据同步, 无论增加或删除等操作, Persistence Decorator是对数据库进行操作的
 *
 * @author alpha
 */
public abstract class AbstractTaskDataProviderPersistenceDecorator extends TaskDataProvider {

    protected TaskDataProvider mTaskDataProvider;

    public AbstractTaskDataProviderPersistenceDecorator(TaskDataProvider taskDataProvider) {

        this.mTaskDataProvider = taskDataProvider;
    }

    @Override
    public int getCount() {

        return mTaskDataProvider.getCount();
    }

    @Override
    public void addItem(Data data) {

        TaskService taskService = TaskServiceImpl.getInstance();
        Task task = ((TaskData)data).getTask();

        switch (getTaskDataProviderPersistenceDecoratorType()) {
//            如果是添加到未完成的Provider里面, 那么需要将task设置为未完成
            case TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                task.setDone(false);
                break;
            case TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                task.setDone(true);
                break;
            default:
        }

//        执行数据库操作后要将id设置回前端, 如果这个task是新增的, 那么id还是以前的, 如果是更新的, 那么id是以前的.
        long id = taskService.addOrUpdateTask(task);
        task.setId(id);

        mTaskDataProvider.addItem(data);
    }

    @Override
    public TaskDataProvider.TaskData getItem(int index) {

        return mTaskDataProvider.getItem(index);
    }

    /**
     * 当被装饰者完成item的移除后, 将改变持久化到数据库
     *
     * @param position
     */
    @Override
    public TaskData removeItem(int position) {

        TaskService taskService = TaskServiceImpl.getInstance();

        TaskData lastRemovedItem = mTaskDataProvider.removeItem(position);

//        将数据从未完成删除或者从已经完成删除, 只需要将他们从自己的TaskDataProvider中删除, 然后更新数据
//        将响应数据更新为已完成或者未完成
//        同时还要更新前台的数据
        Task task = lastRemovedItem.getTask();
        switch (getTaskDataProviderPersistenceDecoratorType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED:
                task.setDone(true);
                taskService.setDone(task);
                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                task.setDone(false);
                taskService.setUnDone(task);
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

        TaskService taskService = TaskServiceImpl.getInstance();

//        如果要取消上次的移除操作, 那么还要判断DataProvider的类型, 如果是为完成, 那么还要讲任务设置回已完成
        Task task = mTaskDataProvider.getLastRemoval().getTask();
        switch (getTaskDataProviderPersistenceDecoratorType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                task.setDone(false);
                taskService.setUnDone(task);
                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                task.setDone(true);
                taskService.setDone(task);
                break;
            default:
        }

        return mTaskDataProvider.undoLastRemoval();
    }

    @Override
    public TaskDataProvider.TaskData getLastRemoval() {

        return mTaskDataProvider.getLastRemoval();
    }

    public abstract String getTaskDataProviderPersistenceDecoratorType();
}
