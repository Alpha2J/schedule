package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
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

//        如果当前的Decorator是未完成任务的, 那么添加一个到Provider中表示新增, 需要持久化到数据库
//        如果是已完成任务的, 那么直接添加到Provider中, 前台显示, 后台不需要持久化
        switch (getTaskDataProviderType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
//                如果是未完成的任务, 那么如果该Task已经存在于数据库中, 说明是从已完成切换到未完成的, 那么只需要将数据添加到TaskDataProvider中, 然后更新
//                数据库信息就可以了, 如果不存在于数据库中, 那么就需要新加数据
                Task unfinishedTask = ((TaskData)data).getTask();
//                需要将任务设置为未完成
                unfinishedTask.setDone(false);
                long ufId = taskService.addOrUpdateTask(unfinishedTask);
//                重新设置好id, 如果是新建任务, 那么id为插入数据后的id, 如果是旧的数据, 那么id为以前的id
                unfinishedTask.setId(ufId);
                mTaskDataProvider.addItem(data);
                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                Task finishedTask = ((TaskData)data).getTask();
                finishedTask.setDone(true);
                long fId = taskService.addOrUpdateTask(finishedTask);
                finishedTask.setId(fId);
                mTaskDataProvider.addItem(data);
                break;
            default:
        }
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
    public void removeItem(int position) {

        TaskService taskService = TaskServiceImpl.getInstance();

        mTaskDataProvider.removeItem(position);

//        将数据从未完成删除或者从已经完成删除, 只需要将他们从自己的TaskDataProvider中删除, 然后更新数据
//        将响应数据更新为已完成或者未完成
//        同时还要更新前台的数据
        Task task = mTaskDataProvider.getLastRemoval().getTask();
        switch (getTaskDataProviderType()) {
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
        switch (getTaskDataProviderType()) {
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

    public abstract String getTaskDataProviderType();
}
