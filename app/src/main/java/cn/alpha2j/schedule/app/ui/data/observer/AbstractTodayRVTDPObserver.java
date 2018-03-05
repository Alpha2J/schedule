package cn.alpha2j.schedule.app.ui.data.observer;

import android.support.v7.widget.RecyclerView;

import cn.alpha2j.schedule.app.alarm.TaskReminderWrapper;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableTaskRVAdapter;
import cn.alpha2j.schedule.app.ui.data.provider.RVDataProvider;
import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * 总体流程, DataProvider操作数据, 操作完后告诉Observer
 *
 * 今日任务的RecyclerView的Adapter的观察者, 如果adapter里面的数据发生改变, 那么这里做出相应的动作
 * @author alpha
 */
public abstract class AbstractTodayRVTDPObserver implements RVDataProviderObserver {

    protected TaskTodayRVAdapterGetter mTaskTodayRVAdapterGetter;
    protected TaskService mTaskService;
    protected TaskReminderWrapper mTaskDataReminder;

    public AbstractTodayRVTDPObserver(TaskTodayRVAdapterGetter taskTodayRVAdapterGetter) {
        mTaskTodayRVAdapterGetter = taskTodayRVAdapterGetter;
        mTaskService = TaskServiceImpl.getInstance();
        mTaskDataReminder = new TaskReminderWrapper();
    }

    /**
     * DataProvider添加完数据后会调用这个方法, 用来刷新前端显示
     */
    @Override
    public void notifyDataAdd() {

        SwipeableTaskRVAdapter adapter = null;
//        当adapter设置的时候将这个设置为true, 表示adapter不会为null
        boolean adapterSet = false;
//        当数据增加的时候, 这个监听器方法要做的事情有:
//        用mTaskTodayRVAdapterGetter获取相应的adapter, 然后通知对应的adapter, 告诉他数据增加了, 刷新前端显示
        switch (getTodayTaskDataProviderObserverType()) {
            case RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                adapter = (SwipeableTaskRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
                adapterSet = true;
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                break;
            case RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED:
                adapter = (SwipeableTaskRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();
                adapterSet = true;
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                break;
            default:
        }

//        刷新完后台显示后设置通知提醒
//        如果是未完成的adapter, 那么说明此时item是添加到未完成的, 进行通知
//        将最后插入那个进行通知
        if (getTodayTaskDataProviderObserverType().equals(RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED) && adapterSet) {
            int count = adapter.getRVTaskDataProvider().getCount();
            RVTaskDataProvider.RVTaskData taskData = adapter.getRVTaskDataProvider().getItem(count - 1);
            mTaskDataReminder.remind(taskData);
        }
    }

    /**
     * 当删除未完成的数据时, 需要将删除的数据添加到已完成的数据集中, 只是前端逻辑, 数据层不用做, 数据层已经在装饰器中写好了
     * 如果是unfinished的remove, 那么需要将传进来的data加入到finished中
     */
    @Override
    public void notifyDataRemove() {

//        首先需要获取删除的那个Adapter的上次删除的数据, 然后将它添加到另一个Adapter中(队尾)
        SwipeableTaskRVAdapter unfinishedAdapter = (SwipeableTaskRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
        SwipeableTaskRVAdapter finishedAdapter = (SwipeableTaskRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();

        switch (getTodayTaskDataProviderObserverType()) {
            case RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                RVTaskDataProvider.RVTaskData finishedData = unfinishedAdapter.getRVTaskDataProvider().getLastRemoval();
                finishedAdapter.getRVTaskDataProvider().addItem(finishedData);
                finishedAdapter.notifyItemInserted(finishedAdapter.getItemCount() - 1);

//                进行通知器通知, 如果是未完成的adapter里面移除的, 那么需要判断是否需要取消通知
                mTaskDataReminder.cancelRemind(finishedData);
                break;
            case RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                RVTaskDataProvider.RVTaskData unfinishedData = finishedAdapter.getRVTaskDataProvider().getLastRemoval();
                unfinishedAdapter.getRVTaskDataProvider().addItem(unfinishedData);
                unfinishedAdapter.notifyItemInserted(unfinishedAdapter.getItemCount() - 1);

//                如果是已完成的adapter里面移除的, 那么需要再次判断是否需要进行通知
                mTaskDataReminder.remind(unfinishedData);
                break;
            default:
        }

//        如果是从已完成的adapter里面删除的, 那么需要将他再次进行提醒, 如果是未完成那里删除的, 需要将它取消提醒
    }

    @Override
    public void notifyUndoLastDataRemove() {

        SwipeableTaskRVAdapter unfinishedAdapter = (SwipeableTaskRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
        SwipeableTaskRVAdapter finishedAdapter = (SwipeableTaskRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();
        int lastInsertedPosition;

        switch (getTodayTaskDataProviderObserverType()) {
            case RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                lastInsertedPosition = unfinishedAdapter.getRVTaskDataProvider().undoLastRemoval();
                unfinishedAdapter.notifyItemInserted(lastInsertedPosition);

                //删除上次插入已完成的数据
                RVTaskDataProvider.RVTaskData lastInsertedFinishedData = finishedAdapter.getRVTaskDataProvider().removeItem(finishedAdapter.getRVTaskDataProvider().getCount() - 1);
                finishedAdapter.notifyItemRemoved(finishedAdapter.getRVTaskDataProvider().getCount());

//                同理通知处理
                mTaskDataReminder.remind(lastInsertedFinishedData);
                break;
            case RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                lastInsertedPosition = finishedAdapter.getRVTaskDataProvider().undoLastRemoval();
                finishedAdapter.notifyItemInserted(lastInsertedPosition);

                //删除上次插入的为完成数据
                RVTaskDataProvider.RVTaskData lastInsertedUnfinishedData = unfinishedAdapter.getRVTaskDataProvider().removeItem(unfinishedAdapter.getRVTaskDataProvider().getCount() - 1);
                unfinishedAdapter.notifyItemRemoved(unfinishedAdapter.getRVTaskDataProvider().getCount());

//                通知处理
                mTaskDataReminder.cancelRemind(lastInsertedUnfinishedData);
                break;
            default:
        }
    }

    @Override
    public void notifyDataDelete() {
        switch (getTodayTaskDataProviderObserverType()) {
//            如果是未完成的删除, 那么需要取消提醒(已完成的不用取消, 因为已完成的没有再设置提醒)
            case RVTaskDataProvider.RVTaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                SwipeableTaskRVAdapter swipeableTaskRVAdapter = (SwipeableTaskRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
                mTaskDataReminder.cancelRemind(swipeableTaskRVAdapter.getRVTaskDataProvider().getLastDeletion());
                break;
            default:
        }
    }

    /**
     * 获取观察者类型
     * @return 字符串表示的类型
     */
    public abstract String getTodayTaskDataProviderObserverType();

    /**
     * 能获取今日RecyclerView 的Adapter的接口
     */
    public interface TaskTodayRVAdapterGetter {

        /**
         * 已经完成的adapter
         * @return ..
         */
        RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> getTodayFinishedRVAdapter();

        /**
         * 未完成的adapter
         * @return 大佬 不要染色了啊, 一个一个写好无聊的
         */
        RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> getTodayUnfinishedRVAdapter();
    }
}
