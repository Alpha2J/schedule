package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.composedadapter.ComposedAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.alarm.TaskDataReminder;
import cn.alpha2j.schedule.app.ui.activity.adapter.SimpleSectionHeaderAdapter;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableFinishedTaskRVAdapter;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableTaskRVAdapter;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableUnfinishedTaskRVAdapter;
import cn.alpha2j.schedule.app.ui.data.generator.TodayFinishedDataProviderGenerator;
import cn.alpha2j.schedule.app.ui.data.generator.TodayUnfinishedDataProviderGenerator;
import cn.alpha2j.schedule.app.ui.data.observer.AbstractTodayTaskDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.DataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.TodayFinishedTaskDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.TodayUnfinishedTaskDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
public class TaskTodayFragment extends BaseFragment
        implements AbstractTodayTaskDataProviderObserver.TaskTodayRVAdapterGetter {

    private static final String TAG = "TaskTodayFragment";

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> mUnfinishedTaskAdapter;
    private RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> mFinishedTaskAdapter;
    private RecyclerViewSwipeManager mUnfinishedRVSManager;
    private RecyclerViewSwipeManager mFinishedRVSManager;
    private ComposedAdapter mComposedAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DataProviderObserver mUnfinishedTaskObserver;
    private DataProviderObserver mFinishedTaskObserver;

    private TaskDataProvider mTodayUnfinishedTaskDataProvider;
    private TaskDataProvider mTodayFinishedTaskDataProvider;

    private TaskDataReminder mTaskDataReminder;

    public TaskTodayFragment() {
        mUnfinishedTaskObserver = new TodayUnfinishedTaskDataProviderObserver(this);
        mFinishedTaskObserver = new TodayFinishedTaskDataProviderObserver(this);

        mTodayUnfinishedTaskDataProvider = new TodayUnfinishedDataProviderGenerator().generate();
        mTodayFinishedTaskDataProvider = new TodayFinishedDataProviderGenerator().generate();

        mTaskDataReminder = new TaskDataReminder();
    }

    @Override
    protected boolean hasView() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_today;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

        initViews();
        initData();
        initNotification();
    }

    private void initViews() {

        mRecyclerView = mRootView.findViewById(R.id.rv_home_task_today_container);
    }

    private void initData() {

//        mUnfinishedTaskAdapter = new SwipeableTaskRVAdapter(mTodayUnfinishedTaskDataProvider);
//        mFinishedTaskAdapter = new SwipeableTaskRVAdapter(mTodayFinishedTaskDataProvider);
        mUnfinishedTaskAdapter = new SwipeableUnfinishedTaskRVAdapter(mTodayUnfinishedTaskDataProvider);
        mFinishedTaskAdapter = new SwipeableFinishedTaskRVAdapter(mTodayFinishedTaskDataProvider);
        mUnfinishedRVSManager = new RecyclerViewSwipeManager();
        mFinishedRVSManager = new RecyclerViewSwipeManager();

//        设置监听事件
        ((SwipeableTaskRVAdapter)mUnfinishedTaskAdapter).setEventListener(new SwipeableTaskRVAdapter.EventListener() {
            @Override
            public void onItemRemoved(int position) {
//                当数据从未完成的Adapter中删除后, 需要将删除的数据添加到已完成的Adapter中, 且添加到最后一个
                mUnfinishedTaskObserver.notifyDataRemove();

                Snackbar snackbar = Snackbar.make(
                        getActivity().findViewById(R.id.cl_home_content_container),
                        "一个任务已被标记为完成",
                        Snackbar.LENGTH_SHORT
                );

                snackbar.setAction("撤销", view -> {
                    mUnfinishedTaskObserver.notifyUndoLastDataRemove();
                });

                snackbar.show();
            }

            @Override
            public void onItemPinned(int position) {

            }

            @Override
            public void onItemViewClicked(View view, int target) {
                if(target == SwipeableTaskRVAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {

                } else {

                }
            }
        });

        ((SwipeableTaskRVAdapter)mFinishedTaskAdapter).setEventListener(new SwipeableTaskRVAdapter.EventListener() {
            @Override
            public void onItemRemoved(int position) {

                mFinishedTaskObserver.notifyDataRemove();

                Snackbar snackbar = Snackbar.make(
                        getActivity().findViewById(R.id.cl_home_content_container),
                        "一个任务已被标记为未完成",
                        Snackbar.LENGTH_SHORT
                );

                snackbar.setAction("撤销", view -> {
                    mFinishedTaskObserver.notifyUndoLastDataRemove();
                });

                snackbar.show();
            }

            @Override
            public void onItemPinned(int position) {

            }

            @Override
            public void onItemViewClicked(View view, int target) {

            }
        });

//        组合各个section
        mComposedAdapter = new ComposedAdapter();
        mComposedAdapter.addAdapter(new SimpleSectionHeaderAdapter("未完成"));
        mComposedAdapter.addAdapter(mUnfinishedRVSManager.createWrappedAdapter(mUnfinishedTaskAdapter));
        mComposedAdapter.addAdapter(new SimpleSectionHeaderAdapter("已完成"));
        mComposedAdapter.addAdapter(mFinishedRVSManager.createWrappedAdapter(mFinishedTaskAdapter));

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
//        Change animations 在support-v7-recyclerview v22中默认是开启的
//        这里关闭它为了让item的回滚动画更好地工作
        animator.setSupportsChangeAnimations(false);

//        将设置附加到RecyclerView中
        mRecyclerView.setAdapter(mComposedAdapter);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(animator);

//        为每个item添加下划线
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_h), true));

//        为RecyclerView关联每个Adapter的Manager
        mUnfinishedRVSManager.attachRecyclerView(mRecyclerView);
        mFinishedRVSManager.attachRecyclerView(mRecyclerView);
    }

    /**
     * 当fragment初始化完成后需要将所有未完成的task遍历一遍, 如果需要通知且时间合适的话就添加到通知管理
     */
    private void initNotification() {
        TaskDataReminder taskDataReminder = new TaskDataReminder();
        int count = mTodayUnfinishedTaskDataProvider.getCount();
        for (int i = 0; i < count; i++) {
            TaskDataProvider.TaskData taskData = mTodayUnfinishedTaskDataProvider.getItem(i);
            taskDataReminder.remind(taskData);
        }
    }

    public void notifyNewTaskAdd(Task task) {
//        需要先将数据添加到前端的DataProvider中, 还要将数据持久化到后台中
        mTodayUnfinishedTaskDataProvider.addItem(new TaskDataProvider.TaskData(task, false));
        mUnfinishedTaskObserver.notifyDataAdd();
    }

    @Override
    public RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> getTodayFinishedRVAdapter() {
        return this.mFinishedTaskAdapter;
    }

    @Override
    public RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> getTodayUnfinishedRVAdapter() {
        return this.mUnfinishedTaskAdapter;
    }
}
