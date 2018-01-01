package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;
import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * @author alpha
 */
public abstract class AbstractTaskDataProviderPersistenceDecorator implements DataProvider {

    private TaskDataProvider mTaskDataProvider;
    private TaskService mTaskService;

    public AbstractTaskDataProviderPersistenceDecorator(TaskDataProvider taskDataProvider) {

        this.mTaskDataProvider = taskDataProvider;
        this.mTaskService = TaskServiceImpl.getInstance();
    }

    @Override
    public int getCount() {

        return mTaskDataProvider.getCount();
    }

    @Override
    public void addItem(Data data) {

        mTaskDataProvider.addItem(data);
        mTaskService.addTask(((TaskDataProvider.TaskData)data).getTask());
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

        mTaskDataProvider.removeItem(position);

        switch (getTaskDataProviderType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED:
                mTaskService.setDone(mTaskDataProvider.getLastRemoval().getTask());
                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                mTaskService.setUnDone(mTaskDataProvider.getLastRemoval().getTask());
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

        switch (getTaskDataProviderType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED:
                mTaskService.setUnDone(mTaskDataProvider.getLastRemoval().getTask());
                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                mTaskService.setDone(mTaskDataProvider.getLastRemoval().getTask());
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
