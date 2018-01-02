package cn.alpha2j.schedule.app.ui.data.observer;

import java.io.Serializable;

/**
 * @author alpha
 */
public interface DataProviderObserver extends Serializable {

    /**
     * 当DataProvider新增完数据后执行
     */
    void notifyDataAdd();

    /**
     * 当DataProvider删除完数据后执行
     */
    void notifyDataRemove();

    /**
     * 取消上次的删除操作
     */
    void notifyUndoLastDataRemove();
}
