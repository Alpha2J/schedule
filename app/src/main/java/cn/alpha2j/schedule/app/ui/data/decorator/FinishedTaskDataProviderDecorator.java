package cn.alpha2j.schedule.app.ui.data.decorator;

import cn.alpha2j.schedule.app.ui.data.DataProviderType;
import cn.alpha2j.schedule.app.ui.data.TaskDataProvider;

/**
 * @author alpha
 */
public class FinishedTaskDataProviderDecorator extends AbstractTaskDataProviderDecorator {

    public FinishedTaskDataProviderDecorator(TaskDataProvider taskDataProvider) {
        super(taskDataProvider);
    }

    @Override
    public int getTaskProviderType() {
        return DataProviderType.TYPE_FINISHED;
    }
}
