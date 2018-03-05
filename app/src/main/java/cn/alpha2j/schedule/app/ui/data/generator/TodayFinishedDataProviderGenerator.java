package cn.alpha2j.schedule.app.ui.data.generator;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 */
public class TodayFinishedDataProviderGenerator extends AbstractTaskDataProviderGenerator {

    @Override
    public String getTaskDataProviderType() {

        return RVTaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED;
    }
}
