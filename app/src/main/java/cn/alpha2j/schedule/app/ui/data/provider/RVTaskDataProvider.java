package cn.alpha2j.schedule.app.ui.data.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
public class RVTaskDataProvider implements RVDataProvider {

    private List<RVTaskData> mDataSet;
    private RVTaskData mLastRemovedData;
    private int mLastRemovedPosition;

    public RVTaskDataProvider() {
        mDataSet = new ArrayList<>();
        mLastRemovedData = null;
        mLastRemovedPosition = -1;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public void addItem(RVAbstractData data) {

        if (data instanceof RVTaskData) {
            mDataSet.add((RVTaskData) data);
//            新增加一个Data, 那么要将上次删除的position重新设置为-1, 当下次如果取消上次的删除操作时, 会重新在
//            最后进行插入
            mLastRemovedPosition = -1;
        } else {
            throw new IllegalArgumentException("参数类型不正确");
        }
    }

    @Override
    public RVTaskData getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mDataSet.get(index);
    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {
        if(fromPosition == toPosition) {
            return;
        }

        Collections.swap(mDataSet, fromPosition, toPosition);
        mLastRemovedPosition = -1;
    }

    @Override
    public RVTaskData removeItem(int position) {
        mLastRemovedData = mDataSet.remove(position);
        mLastRemovedPosition = position;

        return mLastRemovedData;
    }

    @Override
    public int undoLastRemoval() {
        if (mLastRemovedData != null) {
//            如果交换过了, 那么会将position设置为-1, 所以如果是-1的话那么就在最后插入
            int insertedPosition;
            if (mLastRemovedPosition > 0 && mLastRemovedPosition < getCount()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = getCount();
            }

            mDataSet.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }

    @Override
    public RVTaskData getLastRemoval() {
        if(mLastRemovedData != null) {
            return mLastRemovedData;
        } else {
            return null;
        }
    }

    public static final class RVTaskData extends RVAbstractData {

        private Task mTask;
        private boolean mPinned;

        public RVTaskData(Task task, boolean pinned) {
            mTask = task;
            mPinned = pinned;
        }

        @Override
        public long getId() {
            return mTask.getId();
        }

        public String getText() {
            return mTask.getTitle();
        }

        public boolean isPinned() {
            return mPinned;
        }

        public void setPinned(boolean pinned) {
            mPinned = pinned;
        }

        public Task getTask() {
            return mTask;
        }
    }

//    public interface TaskDataProviderType {
//
//        String TYPE_TODAY_TASK_UNFINISHED = "TYPE_TODAY_UNFINISHED_TASK_DATA_PROVIDER";
//
//        String TYPE_TODAY_TASK_FINISHED = "TYPE_TODAY_FINISHED_TASK_DATA_PROVIDER";
//    }
//
//    /**
//     * 标识为过期,
//     * 需要放到Bundle里面进行传输, 实现Serializable接口
//     */
//    @Deprecated
//    public interface TaskTodayDataProviderGetter extends Serializable {
//
//        /**
//         * 获取含有今日已完成数据的TaskDataProvider
//         * @return 表示今日已完成的TaskDataProvider
//         */
//        TaskRVDataProvider getTodayFinishedTaskDataProvider();
//
//        /**
//         * 获取含有今日未完成数据的TaskDataProvider
//         * @return 表示今日未完成的TaskDataProvider
//         */
//        TaskRVDataProvider getTodayUnfinishedTaskDataProvider();
//    }
}
