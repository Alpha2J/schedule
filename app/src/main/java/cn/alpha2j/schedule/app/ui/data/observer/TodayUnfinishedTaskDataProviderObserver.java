package cn.alpha2j.schedule.app.ui.data.observer;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 */
public class TodayUnfinishedTaskDataProviderObserver extends AbstractTodayTaskDataProviderObserver {

    public TodayUnfinishedTaskDataProviderObserver(TaskTodayRVAdapterGetter taskTodayRVAdapterGetter) {
        super(taskTodayRVAdapterGetter);
    }

    @Override
    public String getTodayTaskDataProviderObserverType() {
        return RVTaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED;
    }

}
