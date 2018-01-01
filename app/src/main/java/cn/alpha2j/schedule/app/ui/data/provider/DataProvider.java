package cn.alpha2j.schedule.app.ui.data.provider;

import java.io.Serializable;

/**
 * 数据提供器接口
 * @author alpha
 */
public interface DataProvider extends Serializable {

    /**
     * 表示数据的抽象类
     */
    abstract class Data implements Serializable {

        /**
         * 获取数据的id
         * @return 数据的id
         */
        public abstract long getId();

        /**
         * 获取数据的文本内容
         * @return 数据的文本
         */
        public abstract String getText();

        /**
         * 将该数据设置为打开状态
         * @param pinned
         */
        public abstract void setPinned(boolean pinned);

        /**
         * 表示数据是否为打开状态
         * @return 数据是否为打开状态
         */
        public abstract boolean isPinned();
    }

    /**
     * 获取数据提供器中持有的数据数量
     *
     * @return 持有的数据数量
     */
    int getCount();

    /**
     * 增加一个数据项
     * @param data 增加的项
     */
    void addItem(Data data);

    /**
     * 获取指定数据项
     * @param index
     * @return 指定数据项
     */
    Data getItem(int index);

    /**
     * 移除指定数据项
     * @param position
     */
    void removeItem(int position);

    /**
     * 交换数据项
     *
     * @param fromPosition
     * @param toPosition
     */
    void swapItem(int fromPosition, int toPosition);

    /**
     * 取消上次的删除操作
     * @return 重新插入的位置或者-1 如果没有进行插入
     */
    int undoLastRemoval();

    /**
     * 获取上次删除的数据项
     * @return 上次删除的数据项, 如果没有删除过, 那么返回null
     */
    Data getLastRemoval();
}
