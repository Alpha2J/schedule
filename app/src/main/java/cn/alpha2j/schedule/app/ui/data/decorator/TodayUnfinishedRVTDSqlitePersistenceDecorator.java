package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 */
public class TodayUnfinishedRVTDSqlitePersistenceDecorator extends AbstractRVTDPSqlitePersistenceDecorator {

    public TodayUnfinishedRVTDSqlitePersistenceDecorator(RVTaskDataProvider taskDataProvider) {
        super(taskDataProvider);
    }

    @Override
    public String getRVTaskDataProviderType() {
        return RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED;
    }
}
