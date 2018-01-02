package cn.alpha2j.schedule.app.ui.data.observer;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableRVAdapter;
import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;
import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.exception.WrongDataProviderTypeException;

/**
 * 今日任务的RecyclerView的Adapter的观察者, 如果adapter里面的数据发生改变, 那么这里做出相应的动作
 * @author alpha
 */
public abstract class AbstractTodayTaskDataProviderObserver implements DataProviderObserver {

    protected TaskTodayRVAdapterGetter mTaskTodayRVAdapterGetter;
    protected TaskService mTaskService;

    public AbstractTodayTaskDataProviderObserver(TaskTodayRVAdapterGetter taskTodayRVAdapterGetter) {
        this.mTaskTodayRVAdapterGetter = taskTodayRVAdapterGetter;
        this.mTaskService = TaskServiceImpl.getInstance();
    }

    @Override
    public void notifyDataAdd(DataProvider.Data data) {

        if(!(data instanceof TaskDataProvider.TaskData)) {
            throw new IllegalArgumentException("参数类型不正确");
        }

//        新加入的task都是未完成的, 所以前端显示是插入到未完成的adapter里面的
        SwipeableRVAdapter swipeableRVAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
        DataProvider dataProvider = swipeableRVAdapter.getDataProvider();
//        如果不是TaskDataProvider类型, 那么可能是DataProvider的装饰器类型
        if (!(dataProvider instanceof TaskDataProvider)) {
            dataProvider.addItem(data);
            Toast.makeText(MyApplication.getContext(), "添加成功", Toast.LENGTH_SHORT).show();
        } else {
//            这里还可以进行判断, 是否AbstractTaskDataProviderPersistenceDecorator 类型, 如果是才提示持久化成功, 先不判断
            dataProvider.addItem(data);
            Toast.makeText(MyApplication.getContext(), "添加成功, 未进行持久化", Toast.LENGTH_SHORT).show();
        }

//        无论是哪种类型的DataProvider, 只要添加进去了都要告知相关联的RecyclerViewAdapter
        swipeableRVAdapter.notifyItemInserted(dataProvider.getCount() - 1);
    }

    /**
     * 当删除未完成的数据时, 需要将删除的数据添加到已完成的数据集中, 只是前端逻辑, 数据层不用做, 数据层已经在装饰器中写好了
     * 如果是unfinished的remove, 那么需要将传进来的data加入到finished中
     * @param data
     */
    @Override
    public void notifyDataRemove(DataProvider.Data data) {

        switch (getTodayTaskDataProviderType()) {
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_UNFINISHED :
                SwipeableRVAdapter finishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();
                finishedAdapter.getDataProvider().addItem(data);
                finishedAdapter.notifyItemInserted(finishedAdapter.getDataProvider().getCount() - 1);

                break;
            case TaskDataProvider.TaskDataProviderType.TYPE_TODAY_TASK_FINISHED :
                SwipeableRVAdapter unfinishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
                unfinishedAdapter.getDataProvider().addItem(data);
                unfinishedAdapter.notifyItemInserted(unfinishedAdapter.getDataProvider().getCount() - 1);

                break;
            default:
        }
    }

    @Override
    public void notifyUndoLastDataRemove() {

        SwipeableRVAdapter unfinishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayUnfinishedRVAdapter();
        SwipeableRVAdapter finishedAdapter = (SwipeableRVAdapter) mTaskTodayRVAdapterGetter.getTodayFinishedRVAdapter();
        int lastInsertedPosition = -1;

        switch (getTodayTaskDataProviderType()) {
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

    public abstract String getTodayTaskDataProviderType();

    public interface TaskTodayRVAdapterGetter {

        RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> getTodayFinishedRVAdapter();

        RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> getTodayUnfinishedRVAdapter();
    }
}
