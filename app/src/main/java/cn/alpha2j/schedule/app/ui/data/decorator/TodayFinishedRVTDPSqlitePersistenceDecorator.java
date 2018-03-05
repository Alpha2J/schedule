package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 */
public class TodayFinishedRVTDPSqlitePersistenceDecorator extends AbstractRVTDPSqlitePersistenceDecorator {

    public TodayFinishedRVTDPSqlitePersistenceDecorator(RVTaskDataProvider taskDataProvider) {
        super(taskDataProvider);
    }

    @Override
    public String getRVTaskDataProviderType() {
        return RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED;
    }
}
