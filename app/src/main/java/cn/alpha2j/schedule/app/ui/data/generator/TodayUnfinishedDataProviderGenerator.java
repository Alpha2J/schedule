package cn.alpha2j.schedule.app.ui.data.generator;

import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;

/**
 * @author alpha
 */
public class TodayUnfinishedDataProviderGenerator extends AbstractTaskDataProviderGenerator {

    @Override
    public String getTaskDataProviderType() {

        return TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED;
    }
}
