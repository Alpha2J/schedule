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

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.activity.MainActivity;
import cn.alpha2j.schedule.app.ui.activity.adapter.SectionHeaderAdapter;
import cn.alpha2j.schedule.app.ui.activity.adapter.SwipeableRVAdapter;
import cn.alpha2j.schedule.app.ui.data.provider.TaskDataProvider;
import cn.alpha2j.schedule.app.ui.data.observer.DataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.AbstractTodayTaskDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.TodayFinishedTaskDataProviderObserver;
import cn.alpha2j.schedule.app.ui.data.observer.TodayUnfinishedTaskDataProviderObserver;
import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
public class TaskTodayFragment extends BaseFragment
        implements AbstractTodayTaskDataProviderObserver.TaskTodayRVAdapterGetter {

    private static final String TAG = "TaskTodayFragment";

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> mUnfinishedTaskAdapter;
    private RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> mFinishedTaskAdapter;
    private RecyclerViewSwipeManager mUnfinishedRVSManager;
    private RecyclerViewSwipeManager mFinishedRVSManager;
    private ComposedAdapter mComposedAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DataProviderObserver mUnfinishedTaskObserver;
    private DataProviderObserver mFinishedTaskObserver;

//    不再保存TaskTodayDataProviderGetter对象
//    private TaskDataProvider.TaskTodayDataProviderGetter mTaskTodayDataProviderGetter;

    public TaskTodayFragment() {
        mUnfinishedTaskObserver = new TodayUnfinishedTaskDataProviderObserver(this);
        mFinishedTaskObserver = new TodayFinishedTaskDataProviderObserver(this);
    }

//    不再保存TaskTodayDataProviderGetter对象
//    public static TaskTodayFragment newInstance(TaskDataProvider.TaskTodayDataProviderGetter taskTodayDataProviderGetter) {
//
//        TaskTodayFragment fragment = new TaskTodayFragment();
//
//        Bundle args = new Bundle();
//        args.putSerializable("taskTodayDataProviderGetter", taskTodayDataProviderGetter);
//        fragment.setArguments(args);
//
//        return fragment;
//    }

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

//        不再保存TaskTodayDataProviderGetter对象
//        if(mTaskTodayDataProviderGetter == null) {
//            mTaskTodayDataProviderGetter = (TaskDataProvider.TaskTodayDataProviderGetter) getArguments().get("taskTodayDataProviderGetter");
//            Log.d(TAG, "afterCreated: 获取了TaskTodayDataProviderGetter");
//        }

        initViews();
        initData();
    }

    private void initViews() {

        mRecyclerView = mRootView.findViewById(R.id.rv_home_task_today_container);
    }

    private void initData() {

        mUnfinishedTaskAdapter = new SwipeableRVAdapter(((MainActivity)getActivity()).getTodayUnfinishedTaskDataProvider());
        mFinishedTaskAdapter = new SwipeableRVAdapter(((MainActivity)getActivity()).getTodayFinishedTaskDataProvider());
        mUnfinishedRVSManager = new RecyclerViewSwipeManager();
        mFinishedRVSManager = new RecyclerViewSwipeManager();

        //设置监听事件
        ((SwipeableRVAdapter)mUnfinishedTaskAdapter).setEventListener(new SwipeableRVAdapter.EventListener() {
            @Override
            public void onItemRemoved(int position) {

                mUnfinishedTaskObserver.notifyDataRemove(((MainActivity)getActivity()).getTodayUnfinishedTaskDataProvider().getLastRemoval());

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
//                if(target == SwipeableRVAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {
//                    Toast.makeText(getContext(), "点击了未完成的item", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "点击了未完成的delete", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        ((SwipeableRVAdapter)mFinishedTaskAdapter).setEventListener(new SwipeableRVAdapter.EventListener() {
            @Override
            public void onItemRemoved(int position) {

                mFinishedTaskObserver.notifyDataRemove(((MainActivity)getActivity()).getTodayFinishedTaskDataProvider().getLastRemoval());

                Snackbar snackbar = Snackbar.make(
                        getActivity().findViewById(R.id.cl_home_content_container),
                        "一个任务已被标记为未完成",
                        Snackbar.LENGTH_SHORT
                );

                snackbar.setAction("撤销", view -> {
                    ((MainActivity)getActivity()).getTodayUnfinishedTaskDataProvider().undoLastRemoval();
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
        mComposedAdapter.addAdapter(new SectionHeaderAdapter("未完成"));
        mComposedAdapter.addAdapter(mUnfinishedRVSManager.createWrappedAdapter(mUnfinishedTaskAdapter));
        mComposedAdapter.addAdapter(new SectionHeaderAdapter("已完成"));
        mComposedAdapter.addAdapter(mFinishedRVSManager.createWrappedAdapter(mFinishedTaskAdapter));

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
//        Change animations 在support-v7-recyclerview v22中默认是开启的
//        这里关闭它为了让item的回滚动画更好地工作
        animator.setSupportsChangeAnimations(false);

        //将设置附加到RecyclerView中
        mRecyclerView.setAdapter(mComposedAdapter);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(animator);

        //为每个item添加下划线
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_h), true));

        //为RecyclerView关联每个Adapter的Manager
        mUnfinishedRVSManager.attachRecyclerView(mRecyclerView);
        mFinishedRVSManager.attachRecyclerView(mRecyclerView);
    }

    public void notifyNewTaskAdd(Task task) {
        mUnfinishedTaskObserver.notifyDataAdd(new TaskDataProvider.TaskData(task, false));
    }

    @Override
    public RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> getTodayFinishedRVAdapter() {
        return this.mFinishedTaskAdapter;
    }

    @Override
    public RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder> getTodayUnfinishedRVAdapter() {
        return this.mUnfinishedTaskAdapter;
    }
}
