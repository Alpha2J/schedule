package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author alpha
 */
public abstract class BaseFragment extends Fragment {
    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null) {
            mRootView = hasView() ? inflater.inflate(getLayoutId(), container, false) : null;
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        afterCreated(savedInstanceState);
    }

    /**
     * 是否有视图, 如果有, 那么会使用getLayoutId() 的值创建视图
     * @return
     */
    protected abstract boolean hasView();

    /**
     * 使用该方法提供的id来获取layout资源进行Fragment的创建.
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化完成后调用
     * @param savedInstanceState
     */
    protected abstract void afterCreated(Bundle savedInstanceState);

    /**
     * 各个Fragment中使用到的常量, 当新增Fragment时Tag 常量必须增加, 表示该Fragment的名字
     */
    public interface FragmentConstant {

        String FRAGMENT_TAG = "FragmentTag";
        String FRAGMENT_TAG_TASK_TODAY = "TaskTodayFragment";
        String FRAGMENT_TAG_TASK_OVERVIEW = "TaskOverviewFragment";
        String FRAGMENT_TAG_TASK_STATISTICS = "TaskStatisticsFragment";

        String FRAGMENT_TAG_TASK_TODAY_UNFINISHED_DATA = "TaskTodayUnfinishedDataFragment";
        String FRAGMENT_TAG_TASK_TODAY_FINISHED_DATA = "TaskTodayFinishedDataFragment";
    }
}
