package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 */
public class TodayUnfinishedTaskDataProviderPersistenceDecorator extends AbstractTaskDataProviderPersistenceDecorator {

    public TodayUnfinishedTaskDataProviderPersistenceDecorator(RVTaskDataProvider taskDataProvider) {
        super(taskDataProvider);
    }

    @Override
    public String getTaskDataProviderPersistenceDecoratorType() {
        return RVTaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED;
    }
}
