package cn.alpha2j.schedule.app.ui.data.observer;

import android.support.v7.widget.RecyclerView;

import cn.alpha2j.schedule.app.alarm.TaskDataReminder;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableRVAdapter;
import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;
import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * 总体流程, DataProvider操作数据, 操作完后告诉Observer
 *
 * 今日任务的RecyclerView的Adapter的观察者, 如果adapter里面的数据发生改变, 那么这里做出相应的动作
 * @author alpha
 */
public abstract class AbstractTodayTaskDataProviderObserver implements DataProviderObserver {

    protected TaskTodayRVAdapterGetter mTaskTodayRVAdapterGetter;
    protected TaskService mTaskService;
    protected TaskDataReminder mTaskDataReminder;

    public AbstractTodayTaskDataProviderObserver(TaskTodayRVAdapterGetter taskTodayRVAdapterGetter) {
        this.mTaskTodayRVAdapterGetter = taskTodayRVAdapterGetter;
        this.mTaskService = TaskServiceImpl.getInstance();
        mTaskDataReminder = new TaskDataReminder();
    }

    /**
     * DataProvider添加完数据后会调用这个方法, 用来刷新前端显示
     */
    @Override
    public void notifyDataAdd() {

        SwipeableRVAdapter adapter = null;
//        当adapter设置的时候将这个设置为true, 表示adapter不会为null
        boolean adapterSet = false;
//        当数据增加的时候, 这个监听器方法要做的事情有:
//        用mTaskTodayRVAdapterGetter获取相应的adapter, 然后通知对应的adapter, 告诉他数据增加了, 刷新前端显示
        switch (getTodayTaskDataProviderObserverType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                adapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
                adapterSet = true;
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                adapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();
                adapterSet = true;
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                break;
            default:
        }
    }

    /**
     * 当删除未完成的数据时, 需要将删除的数据添加到已完成的数据集中, 只是前端逻辑, 数据层不用做, 数据层已经在装饰器中写好了
     * 如果是unfinished的remove, 那么需要将传进来的data加入到finished中
     */
    @Override
    public void notifyDataRemove() {

//        首先需要获取删除的那个Adapter的上次删除的数据, 然后将它添加到另一个Adapter中(队尾)
        SwipeableRVAdapter unfinishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
        SwipeableRVAdapter finishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();

        switch (getTodayTaskDataProviderObserverType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                DataProvider.Data finishedData = unfinishedAdapter.getDataProvider().getLastRemoval();
                finishedAdapter.getDataProvider().addItem(finishedData);
                finishedAdapter.notifyItemInserted(finishedAdapter.getItemCount() - 1);

                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                DataProvider.Data unfinishedData = finishedAdapter.getDataProvider().getLastRemoval();
                unfinishedAdapter.getDataProvider().addItem(unfinishedData);
                unfinishedAdapter.notifyItemInserted(unfinishedAdapter.getItemCount() - 1);

                break;
            default:
        }
    }

    @Override
    public void notifyUndoLastDataRemove() {

        SwipeableRVAdapter unfinishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
        SwipeableRVAdapter finishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();
        int lastInsertedPosition;

        switch (getTodayTaskDataProviderObserverType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                lastInsertedPosition = unfinishedAdapter.getDataProvider().undoLastRemoval();
                unfinishedAdapter.notifyItemInserted(lastInsertedPosition);

                //删除上次插入已完成的数据
                finishedAdapter.getDataProvider().removeItem(finishedAdapter.getDataProvider().getCount() - 1);
                finishedAdapter.notifyItemRemoved(finishedAdapter.getDataProvider().getCount());
                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                lastInsertedPosition = finishedAdapter.getDataProvider().undoLastRemoval();
                finishedAdapter.notifyItemInserted(lastInsertedPosition);

                //删除上次插入的为完成数据
                unfinishedAdapter.getDataProvider().removeItem(unfinishedAdapter.getDataProvider().getCount() - 1);
                unfinishedAdapter.notifyItemRemoved(unfinishedAdapter.getDataProvider().getCount());

                break;
            default:
        }
    }

    public abstract String getTodayTaskDataProviderObserverType();

    public interface TaskTodayRVAdapterGetter {

        RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> getTodayFinishedRVAdapter();

        RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> getTodayUnfinishedRVAdapter();
    }
}
