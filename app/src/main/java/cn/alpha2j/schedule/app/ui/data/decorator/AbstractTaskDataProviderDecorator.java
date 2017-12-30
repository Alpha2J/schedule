package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.AbstractDataProvider;
import cn.alpha2j.schedule.app.ui.data.DataProviderType;
import cn.alpha2j.schedule.app.ui.data.TaskDataProvider;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * @author alpha
 */
public abstract class AbstractTaskDataProviderDecorator extends AbstractDataProvider {

    private TaskDataProvider mTaskDataProvider;
    private TaskService mTaskService;

    public AbstractTaskDataProviderDecorator(TaskDataProvider taskDataProvider) {

        this.mTaskDataProvider = taskDataProvider;
        this.mTaskService = TaskServiceImpl.getInstance();
    }

    @Override
    public int getCount() {
        return mTaskDataProvider.getCount();
    }

    @Override
    public Data getItem(int index) {
        return mTaskDataProvider.getItem(index);
    }

    @Override
    public void removeItem(int position) {

        mTaskDataProvider.removeItem(position);

        switch (getTaskProviderType()) {
            case DataProviderType.TYPE_UNFINISHED :
                mTaskService.setDone(((TaskDataProvider.TaskData)getLastRemoval()).getTask());
                break;
            case DataProviderType.TYPE_FINISHED :
                mTaskService.setUnDone(((TaskDataProvider.TaskData)getLastRemoval()).getTask());
                break;
            default:
        }
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {

    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {
        mTaskDataProvider.swapItem(fromPosition, toPosition);
    }

    @Override
    public int undoLastRemoval() {

        switch (getTaskProviderType()) {
            case DataProviderType.TYPE_UNFINISHED :
                mTaskService.setUnDone(((TaskDataProvider.TaskData)getLastRemoval()).getTask());
                break;
            case DataProviderType.TYPE_FINISHED :
                mTaskService.setDone(((TaskDataProvider.TaskData)getLastRemoval()).getTask());
                break;
            default:
        }

        return mTaskDataProvider.undoLastRemoval();
    }

    @Override
    public Data getLastRemoval() {
        return mTaskDataProvider.getLastRemoval();
    }

    public abstract int getTaskProviderType();
}
