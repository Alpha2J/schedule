package cn.alpha2j.schedule.app.ui.data.observer;

import java.io.Serializable;

import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;

/**
 * @author alpha
 */
public interface DataProviderObserver extends Serializable {

    /**
     * 当DataProvider新增完数据后执行
     * @param data
     */
    void notifyDataAdd(DataProvider.Data data);

    /**
     * 当DataProvider删除完数据后执行
     *
     * @param data
     */
    void notifyDataDelete(DataProvider.Data data);

    /**
     * 取消上次的删除操作
     */
    void notifyUndoLastDataDelete();
}
