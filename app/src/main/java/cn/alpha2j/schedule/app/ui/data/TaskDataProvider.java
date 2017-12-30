package cn.alpha2j.schedule.app.ui.data;

import java.util.Collections;
import java.util.List;

import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
public class TaskDataProvider extends AbstractDataProvider {

    private List<TaskData> mDataset;
    private TaskData mLastRemovedData;
    private int mLastRemovedPosition = -1;

    public TaskDataProvider(List<TaskData> dataset) {

        mDataset = dataset;
    }

    @Override
    public int getCount() {

        return mDataset.size();
    }

    @Override
    public Data getItem(int index) {

        if(index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mDataset.get(index);
    }

    @Override
    public void removeItem(int position) {

        final TaskData removedItem = mDataset.remove(position);

        mLastRemovedData = removedItem;
        mLastRemovedPosition = position;
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {

        if(fromPosition == toPosition) {
            return;
        }

//        final ConcreteData item = mData.remove()

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
    public Data getLastRemoval() {
        return mLastRemovedData;
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

    public interface TaskDataProviderGetter {

        TaskDataProvider getFinishedTaskDataProvider();

        TaskDataProvider getUnfinishedTaskDataProvider();
    }
}
