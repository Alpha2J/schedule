package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;

/**
 * @author alpha
 */
public class TodayFinishedTaskDataProviderPersistenceDecorator extends AbstractTaskDataProviderPersistenceDecorator {

    public TodayFinishedTaskDataProviderPersistenceDecorator(TaskDataProvider taskDataProvider) {
        super(taskDataProvider);
    }

    @Override
    public String getTaskDataProviderType() {

        return TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED;
    }
}
