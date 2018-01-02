package cn.alpha2j.schedule.app.ui.data.observer;

import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;

/**
 * @author alpha
 */
public class TodayUnfinishedTaskDataProviderObserver extends AbstractTodayTaskDataProviderObserver {

    public TodayUnfinishedTaskDataProviderObserver(TaskTodayRVAdapterGetter taskTodayRVAdapterGetter) {
        super(taskTodayRVAdapterGetter);
    }

    @Override
    public String getTodayTaskDataProviderObserverType() {
        return TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED;
    }

}
