package cn.alpha2j.schedule.app.ui.data.observer;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableTaskAdapter;
import cn.alpha2j.schedule.app.ui.data.TaskDataProvider;

/**
 * 今日任务的RecyclerView的Adapter的观察者, 如果adapter里面的数据发生改变, 那么这里做出相应的动作
 * @author alpha
 */
public abstract class AbstractTaskTodayDataObserver implements TaskDataObserver {

    protected RecyclerViewAdapterGetter mRecyclerViewAdapterGetter;

    public AbstractTaskTodayDataObserver(RecyclerViewAdapterGetter recyclerViewAdapterGetter) {
        this.mRecyclerViewAdapterGetter = recyclerViewAdapterGetter;
    }

    @Override
    public void notifyDataAdd(TaskDataProvider.TaskData taskData) {
        Toast.makeText(MyApplication.getContext(), "a new task added" + taskData.getTask().getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDataDelete() {

        switch (getType()) {
            case TaskTodayDataObserverType.TYPE_FINISHED :
                Toast.makeText(MyApplication.getContext(), "已完成数据删除了", Toast.LENGTH_SHORT).show();
                break;
            case TaskTodayDataObserverType.TYPE_UNFINISHED :
                Toast.makeText(MyApplication.getContext(), "未完成数据删除了", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    @Override
    public void notifyUndoLastDataDelete() {

        switch (getType()) {
            case TaskTodayDataObserverType.TYPE_FINISHED :
                Toast.makeText(MyApplication.getContext(), "取消上次删除的已完成item", Toast.LENGTH_SHORT).show();
                break;
            case TaskTodayDataObserverType.TYPE_UNFINISHED :
                Toast.makeText(MyApplication.getContext(), "取消上次删除的未完成item", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    public abstract int getType();

    public interface RecyclerViewAdapterGetter {

        RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> getFinishedTaskAdapter();

        RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> getUnfinishedTaskAdapter();
    }

    public interface TaskTodayDataObserverType {

        int TYPE_FINISHED = 0;

        int TYPE_UNFINISHED = 1;
    }
}
