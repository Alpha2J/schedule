package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 */
public class TaskOverviewFragment extends BaseFragment {

    private static final String TAG = "TaskOverviewFragment";

    @Override
    protected boolean hasView() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_overview;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

    }

    @Override
    public void refreshData() {

    }
}
