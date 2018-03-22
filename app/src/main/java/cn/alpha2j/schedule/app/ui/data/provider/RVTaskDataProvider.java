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
    private RVTaskData mLastDeletedData;

    public RVTaskDataProvider() {
        mDataSet = new ArrayList<>();
        mLastRemovedData = null;
        mLastRemovedPosition = -1;
        mLastDeletedData = null;
    }

    public RVTaskDataProvider(List<RVTaskData> dataSet) {

        if (dataSet == null) {
            throw new NullPointerException("dataset不能为空");
        }

        mDataSet = dataSet;
        mLastRemovedData = null;
        mLastRemovedPosition = -1;
        mLastDeletedData = null;
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

    /**
     * deleteItem方法和remove方法不同, remove只是将它从当前provider移除, 然后可能添加到另一个provider中
     * 但是deleteItem方法不仅会从当前provider中移除, 还会从持久层中删除
     * @param position
     * @return
     */
    public RVTaskData deleteItem(int position) {
        mLastDeletedData = mDataSet.remove(position);

//        删除一个item后, 如果下次需要undoremoval, 那么只能添加到尾部
        mLastRemovedPosition = -1;

        return mLastDeletedData;
    }

    public RVTaskData getLastDeletion() {
        return mLastDeletedData;
    }

    public List<RVTaskData> getDataSet() {
        return mDataSet;
    }

    public void setDataSet(List<RVTaskData> dataSet) {
        mDataSet = dataSet;
    }

    public void replaceData(List<RVTaskData> dataSet) {

        if (dataSet == null) {
            return;
        }

        mDataSet.clear();
        mDataSet.addAll(dataSet);
        mLastRemovedData = null;
        mLastRemovedPosition = -1;
        mLastDeletedData = null;
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

    public interface RVTaskDataProviderType {

        String TYPE_TODAY_TASK_UNFINISHED = "TYPE_TODAY_UNFINISHED_TASK_DATA_PROVIDER";

        String TYPE_TODAY_TASK_FINISHED = "TYPE_TODAY_FINISHED_TASK_DATA_PROVIDER";
    }
}
