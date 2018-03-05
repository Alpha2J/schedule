package cn.alpha2j.schedule.app.ui.data.generator;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 */
public class TodayUnfinishedDataProviderGenerator extends AbstractTaskDataProviderGenerator {

    @Override
    public String getTaskDataProviderType() {

        return RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED;
    }
}
