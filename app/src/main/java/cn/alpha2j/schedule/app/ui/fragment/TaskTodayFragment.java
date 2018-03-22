package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.composedadapter.ComposedAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.remind.RemindManager;
import cn.alpha2j.schedule.app.ui.activity.adapter.SimpleSectionHeaderAdapter;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableFinishedTaskRVAdapter;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableTaskRVAdapter;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableUnfinishedTaskRVAdapter;
import cn.alpha2j.schedule.app.ui.data.generator.TodayFinishedDataProviderGenerator;
import cn.alpha2j.schedule.app.ui.data.generator.TodayUnfinishedDataProviderGenerator;
import cn.alpha2j.schedule.app.ui.data.observer.AbstractTodayRVTDPObserver;
import cn.alpha2j.schedule.app.ui.data.observer.RVDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.TodayFinishedTaskDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.TodayUnfinishedTaskDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;
import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
public class TaskTodayFragment extends BaseFragment
        implements AbstractTodayRVTDPObserver.TaskTodayRVAdapterGetter {

    private static final String TAG = "TaskTodayFragment";

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> mUnfinishedTaskAdapter;
    private RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> mFinishedTaskAdapter;

    private RVDataProviderObserver mUnfinishedTaskObserver;
    private RVDataProviderObserver mFinishedTaskObserver;

    private RVTaskDataProvider mTodayUnfinishedTaskDataProvider;
    private RVTaskDataProvider mTodayFinishedTaskDataProvider;

    public TaskTodayFragment() { }

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

    /**
     * 更新Fragment的显示数据
     */
    @Override
    public void refreshData() {

//        将数据集里面的数据全部删除
        RVTaskDataProvider unfinishedTemp = new TodayUnfinishedDataProviderGenerator().generate();
        mTodayUnfinishedTaskDataProvider.replaceData(unfinishedTemp.getDataSet());
        mUnfinishedTaskAdapter.notifyDataSetChanged();

        RVTaskDataProvider finishedTemp = new TodayFinishedDataProviderGenerator().generate();
        mTodayFinishedTaskDataProvider.replaceData(finishedTemp.getDataSet());
        mFinishedTaskAdapter.notifyDataSetChanged();
        Log.d(TAG, "refreshData: unfinished data provider and finished data provider has been reload.");
    }

    private void initViews() {

        mRecyclerView = mRootView.findViewById(R.id.rv_home_task_today_container);
    }

    private void initData() {

        mUnfinishedTaskObserver = new TodayUnfinishedTaskDataProviderObserver(this);
        mFinishedTaskObserver = new TodayFinishedTaskDataProviderObserver(this);

        mTodayUnfinishedTaskDataProvider = new TodayUnfinishedDataProviderGenerator().generate();
        mTodayFinishedTaskDataProvider = new TodayFinishedDataProviderGenerator().generate();

        mUnfinishedTaskAdapter = new SwipeableUnfinishedTaskRVAdapter(getContext(), mTodayUnfinishedTaskDataProvider);
        mFinishedTaskAdapter = new SwipeableFinishedTaskRVAdapter(getContext(), mTodayFinishedTaskDataProvider);

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
                int position = getLocalPosition(view, mUnfinishedTaskAdapter);

                if(target == SwipeableTaskRVAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {
//                    点击了item
                    System.out.println(position);
                } else {
//                    点击了item的delete button
                    mTodayUnfinishedTaskDataProvider.deleteItem(position);
                    mUnfinishedTaskAdapter.notifyItemRemoved(position);
                    mUnfinishedTaskObserver.notifyDataDelete();
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
                int position = getLocalPosition(view, mFinishedTaskAdapter);

                if(target == SwipeableTaskRVAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {
//                    点击了item
                    System.out.println(position);
                } else {
//                    点击了item的delete button
                    mTodayFinishedTaskDataProvider.deleteItem(position);
                    mFinishedTaskAdapter.notifyItemRemoved(position);
                    mFinishedTaskObserver.notifyDataDelete();
                }
            }
        });

//        组合各个section
        ComposedAdapter composedAdapter = new ComposedAdapter();
        composedAdapter.addAdapter(new SimpleSectionHeaderAdapter("未完成"));
        RecyclerViewSwipeManager unfinishedRVSManager = new RecyclerViewSwipeManager();
        composedAdapter.addAdapter(unfinishedRVSManager.createWrappedAdapter(mUnfinishedTaskAdapter));

        composedAdapter.addAdapter(new SimpleSectionHeaderAdapter("已完成"));
        RecyclerViewSwipeManager finishedRVSManager = new RecyclerViewSwipeManager();
        composedAdapter.addAdapter(finishedRVSManager.createWrappedAdapter(mFinishedTaskAdapter));

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
//        Change animations 在support-v7-recyclerview v22中默认是开启的
//        这里关闭它为了让item的回滚动画更好地工作
        animator.setSupportsChangeAnimations(false);

//        将设置附加到RecyclerView中
        mRecyclerView.setAdapter(composedAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setItemAnimator(animator);

//        为每个item添加下划线
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_h), true));

//        为RecyclerView关联每个Adapter的Manager

        unfinishedRVSManager.attachRecyclerView(mRecyclerView);
        finishedRVSManager.attachRecyclerView(mRecyclerView);
    }

    private int getLocalPosition(View view, RecyclerView.Adapter adapter) {
        RecyclerView recyclerView = RecyclerViewAdapterUtils.getParentRecyclerView(view);
        RecyclerView.ViewHolder viewHolder = recyclerView.findContainingViewHolder(view);

        int rootPosition = viewHolder.getAdapterPosition();
        if (rootPosition == RecyclerView.NO_POSITION) {
            return -1;
        }

        RecyclerView.Adapter rootAdapter = recyclerView.getAdapter();

        return WrapperAdapterUtils.unwrapPosition(rootAdapter, adapter, rootPosition);
    }

    /**
     * 当fragment初始化完成后需要将所有未完成的task遍历一遍, 如果需要通知且时间合适的话就添加到通知管理
     */
    private void initNotification() {

        for (int i = 0; i < mTodayUnfinishedTaskDataProvider.getCount(); i++) {
            Task task = mTodayUnfinishedTaskDataProvider.getItem(i).getTask();
            RemindManager.add(task);
        }
    }

    /**
     * 由当前Fragment来进行持久化
     * @param task 持久化的task
     */
    public void notifyNewTaskAdd(Task task) {
//        需要先将数据添加到前端的DataProvider中, 还要将数据持久化到后台中
        mTodayUnfinishedTaskDataProvider.addItem(new RVTaskDataProvider.RVTaskData(task, false));
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
