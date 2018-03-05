package cn.alpha2j.schedule.app.ui.data.provider;

/**
 * 数据提供器接口
 * @author alpha
 */
public interface RVDataProvider {

    /**
     * 表示数据的抽象类
     */
    abstract class RVAbstractData {

        /**
         * 获取数据的id
         * @return 数据的id
         */
        public abstract long getId();
    }

    /**
     * 获取数据提供器中持有的数据数量
     *
     * @return 持有的数据数量
     */
    int getCount();

    /**
     * 增加一个数据项
     *
     * @param data 增加的项
     */
    void addItem(RVAbstractData data);

    /**
     * 获取指定数据项
     * @param index 索引
     * @return 指定数据项
     */
    RVAbstractData getItem(int index);

    /**
     * 交换数据项
     *
     * @param fromPosition 交换项1
     * @param toPosition 交换项2
     */
    void swapItem(int fromPosition, int toPosition);

    /**
     * 移除指定数据项
     * @param position 指定项索引
     * @return 制定项
     */
    RVAbstractData removeItem(int position);

    /**
     * 取消上次的删除操作
     *
     * @return 重新插入的位置或者-1 如果没有进行插入
     */
    int undoLastRemoval();

    /**
     * 获取上次删除的数据项
     * @return 上次删除的数据项, 如果没有删除过, 那么返回null
     */
    RVAbstractData getLastRemoval();
}
