package cn.alpha2j.schedule.app.ui.data.observer;

import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;

/**
 * @author alpha
 */
public class TodayFinishedTaskDataProviderObserver extends AbstractTodayTaskDataProviderObserver {

    public TodayFinishedTaskDataProviderObserver(TaskTodayRVAdapterGetter taskTodayRVAdapterGetter) {
        super(taskTodayRVAdapterGetter);
    }

    @Override
    public String getTodayTaskDataProviderType() {
        return TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED;
    }
}
