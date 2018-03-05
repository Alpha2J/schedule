package cn.alpha2j.schedule.app.ui.data.observer;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 */
public class TodayFinishedTaskDataProviderObserver extends AbstractTodayRVTDPObserver {

    public TodayFinishedTaskDataProviderObserver(TaskTodayRVAdapterGetter taskTodayRVAdapterGetter) {
        super(taskTodayRVAdapterGetter);
    }

    @Override
    public String getTodayTaskDataProviderObserverType() {
        return RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED;
    }
}
