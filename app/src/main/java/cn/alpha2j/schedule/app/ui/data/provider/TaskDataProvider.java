package cn.alpha2j.schedule.app.ui.data.provider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
public class TaskDataProvider implements DataProvider {

    private List<TaskData> mDataset;
    private TaskData mLastRemovedData;
    private int mLastRemovedPosition;

    public TaskDataProvider() {

        this.mDataset = new ArrayList<>();
        mLastRemovedData = null;
        mLastRemovedPosition = -1;
    }

    public TaskDataProvider(List<TaskData> dataset) {

        if(dataset == null) {
            throw new NullPointerException("dataset不能为空");
        }

        mDataset = dataset;
        mLastRemovedData = null;
        mLastRemovedPosition = -1;
    }

    public void setDataset(List<TaskData> dataset) {

        if (dataset == null) {
            throw new NullPointerException("不允许传空值");
        }

        if (mDataset == null) {
            this.mDataset = dataset;
        } else {
            this.mDataset.addAll(dataset);
        }
    }

    @Override
    public int getCount() {

        return mDataset.size();
    }

    @Override
    public void addItem(Data data) {

        if (data instanceof TaskData) {
            mDataset.add((TaskData) data);
//            新增加一个Data, 那么要将上次删除的position重新设置为-1, 当下次如果取消上次的删除操作时, 会重新在
//            最后进行插入
            mLastRemovedPosition = -1;
        } else {
            throw new IllegalArgumentException("参数类型不正确");
        }
    }

    /**
     * 返回的实际类型为TaskData
     * @param index
     * @return
     */
    @Override
    public TaskData getItem(int index) {

        if(index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mDataset.get(index);
    }

    @Override
    public void removeItem(int position) {

        mLastRemovedData = mDataset.remove(position);
        mLastRemovedPosition = position;
    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {

        if(fromPosition == toPosition) {
            return;
        }

        Collections.swap(mDataset, fromPosition, toPosition);
        mLastRemovedPosition = -1;
    }

    @Override
    public int undoLastRemoval() {

        if(mLastRemovedData != null) {

//            如果交换过了, 那么会将position设置为-1, 所以如果是-1的话那么就在最后插入
            int insertedPosition;
            if(mLastRemovedPosition > 0 && mLastRemovedPosition < getCount()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = getCount();
            }

            mDataset.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }

    @Override
    public TaskData getLastRemoval() {

        if(mLastRemovedData != null) {
            return mLastRemovedData;
        } else {
            return null;
        }
    }

    public static final class TaskData extends Data {

        private Task mTask;
        private boolean mPinned;

        public TaskData() {

        }

        public TaskData(Task task, boolean pinned) {

            mTask = task;
            mPinned = pinned;
        }

        @Override
        public long getId() {

            return mTask.getId();
        }

        @Override
        public String getText() {

            return mTask.getTitle();
        }

        @Override
        public void setPinned(boolean pinned) {

            this.mPinned = pinned;
        }

        @Override
        public boolean isPinned() {

            return this.mPinned;
        }

        public Task getTask() {

            return mTask;
        }
    }

    public interface TaskDataProviderType {

        String TYPE_TODAY_TASK_UNFINISHED = "TYPE_TODAY_UNFINISHED_TASK_DATA_PROVIDER";

        String TYPE_TODAY_TASK_FINISHED = "TYPE_TODAY_FINISHED_TASK_DATA_PROVIDER";
    }

    /**
     * 标识为过期,
     * 需要放到Bundle里面进行传输, 实现Serializable接口
     */
    public interface TaskTodayDataProviderGetter extends Serializable {

        /**
         * 获取含有今日已完成数据的TaskDataProvider
         * @return 表示今日已完成的TaskDataProvider
         */
        TaskDataProvider getTodayFinishedTaskDataProvider();

        /**
         * 获取含有今日未完成数据的TaskDataProvider
         * @return 表示今日未完成的TaskDataProvider
         */
        TaskDataProvider getTodayUnfinishedTaskDataProvider();
    }
}
