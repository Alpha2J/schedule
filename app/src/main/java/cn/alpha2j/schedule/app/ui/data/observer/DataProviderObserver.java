package cn.alpha2j.schedule.app.ui.data.observer;

import java.io.Serializable;

import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;

/**
 * @author alpha
 */
public interface DataProviderObserver extends Serializable {

    void notifyDataAdd(TaskDataProvider.TaskData taskData);

    void notifyDataDelete();

    void notifyUndoLastDataDelete();
}
