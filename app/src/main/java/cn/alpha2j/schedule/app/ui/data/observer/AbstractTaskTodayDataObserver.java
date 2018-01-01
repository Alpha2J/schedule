package cn.alpha2j.schedule.app.ui.data.observer;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableRecyclerViewAdapter;
import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;
import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * 今日任务的RecyclerView的Adapter的观察者, 如果adapter里面的数据发生改变, 那么这里做出相应的动作
 * @author alpha
 */
public abstract class AbstractTaskTodayDataObserver implements DataProviderObserver {

    protected RecyclerViewAdapterGetter mRecyclerViewAdapterGetter;
    protected TaskService mTaskService;

    public AbstractTaskTodayDataObserver(RecyclerViewAdapterGetter recyclerViewAdapterGetter) {
        this.mRecyclerViewAdapterGetter = recyclerViewAdapterGetter;
        this.mTaskService = TaskServiceImpl.getInstance();
    }

    @Override
    public void notifyDataAdd(TaskDataProvider.TaskData taskData) {

        Task task = taskData.getTask();
        long id = mTaskService.addTask(task);
        if(id == -1) {
            Toast.makeText(MyApplication.getContext(), "添加失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MyApplication.getContext(), "添加成功", Toast.LENGTH_SHORT).show();

            task.setId(id);
//            ((SwipeableTaskAdapter)mRecyclerViewAdapterGetter.getUnfinishedTaskAdapter()).getDataProvider().


        }
    }

    @Override
    public void notifyDataDelete() {

        switch (getType()) {
            case TaskTodayDataObserverType.TYPE_FINISHED :


                DataProvider abstractDataProvider = ((SwipeableRecyclerViewAdapter)mRecyclerViewAdapterGetter.getUnfinishedTaskAdapter()).getDataProvider();
                TaskDataProvider.TaskData taskData = (TaskDataProvider.TaskData) ((SwipeableRecyclerViewAdapter)mRecyclerViewAdapterGetter.getFinishedTaskAdapter()).getDataProvider().getLastRemoval();
                abstractDataProvider.addItem(new TaskDataProvider.TaskData(taskData.getTask(), false));
                mRecyclerViewAdapterGetter.getUnfinishedTaskAdapter().notifyItemInserted(abstractDataProvider.getCount());

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

        RecyclerView.Adapter<SwipeableRecyclerViewAdapter.SwipeableItemViewHolder> getFinishedTaskAdapter();

        RecyclerView.Adapter<SwipeableRecyclerViewAdapter.SwipeableItemViewHolder> getUnfinishedTaskAdapter();
    }

    public interface TaskTodayDataObserverType {

        int TYPE_FINISHED = 0;

        int TYPE_UNFINISHED = 1;
    }
}
