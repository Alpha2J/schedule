package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 */
public class TaskTodayFragment extends BaseFragment {
    private static final String TAG = "TaskTodayFragment";

    public TaskTodayFragment() {}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_today;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

    }
}
