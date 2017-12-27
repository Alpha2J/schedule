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
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterCreated(savedInstanceState);
    }

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
}
