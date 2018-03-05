package cn.alpha2j.schedule.app.ui.data.observer;

/**
 * @author alpha
 */
public interface RVDataProviderObserver {

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

    /**
     * 删除数据
     */
    void notifyDataDelete();
}
