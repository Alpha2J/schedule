package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import cn.alpha2j.schedule.app.ui.data.AbstractDataProvider;
import cn.alpha2j.schedule.app.ui.data.creator.DataProviderCreator;

/**
 * @author alpha
 */
public class TaskDataProviderFragment extends BaseFragment {

    private static final String TAG = "TaskDataProviderFrag";

    private AbstractDataProvider mDataProvider;

    public static TaskDataProviderFragment newInstance(DataProviderCreator dataProviderCreator) {

        TaskDataProviderFragment fragment = new TaskDataProviderFragment();

        AbstractDataProvider dataProvider = dataProviderCreator.create();
        Bundle args = new Bundle();
        args.putSerializable("dataProvider", dataProvider);
        fragment.setArguments(args);

        return fragment;
    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }

    @Override
    protected boolean hasView() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return -1;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

        if (mDataProvider == null) {
            mDataProvider = (AbstractDataProvider) getArguments().get("dataProvider");
            Log.d(TAG, "afterCreated: 获取了DataProvider");
        }
    }
}
