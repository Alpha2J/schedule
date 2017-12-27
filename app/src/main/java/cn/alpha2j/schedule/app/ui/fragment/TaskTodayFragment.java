package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.adapter.SwipeableTaskAdapter;
import cn.alpha2j.schedule.app.ui.data.RecyclerViewTaskItem;
import cn.alpha2j.schedule.app.util.TaskAlarm;
import cn.alpha2j.schedule.data.service.TaskService;

/**
 * @author alpha
 */
public class TaskTodayFragment extends BaseFragment {

    private static final String TAG = "TaskTodayFragment";

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> mUnfinishedTaskAdapter;
    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> mFinishedTaskAdapter;
    private List<RecyclerViewTaskItem> mUnfinishedTaskList;
    private List<RecyclerViewTaskItem> mFinishedTaskList;
    private TaskService mTaskService;
    private TaskAlarm mTaskAlarm;


    public TaskTodayFragment() {}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_today;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

        initViews();
    }

    private void initViews() {
        mRecyclerView = mRootView.findViewById(R.id.rv_home_task_today_container);
    }
}
