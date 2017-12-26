package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 */
public class TaskTodayFragment extends BaseFragment {

    private static final String TAG = "TaskTodayFragment";

    private FloatingActionButton mFloatingActionButton;

    private RecyclerView mRecyclerView;

    //    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> unfinishedTaskAdapter;
//    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> finishedTaskAdapter;
//    private List<RecyclerViewTaskItem> unfinishedTaskList;
//    private List<RecyclerViewTaskItem> finishedTaskList;
//    private TaskService taskService;
//    private TaskAlarm taskAlarm;



    public TaskTodayFragment() {}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_today;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

    }
}
