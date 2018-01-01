package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;
import cn.alpha2j.schedule.app.ui.data.generator.DataProviderGenerator;

/**
 * @author alpha
 */
public class TaskDataProviderFragment extends BaseFragment {

    private static final String TAG = "TaskDataProviderFrag";

    private DataProvider mDataProvider;

    public static TaskDataProviderFragment newInstance(DataProviderGenerator dataProviderCreator) {

        TaskDataProviderFragment fragment = new TaskDataProviderFragment();

        DataProvider dataProvider = dataProviderCreator.generate();
        Bundle args = new Bundle();
        args.putSerializable("dataProvider", dataProvider);
        fragment.setArguments(args);

        return fragment;
    }

    public DataProvider getDataProvider() {
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
            mDataProvider = (DataProvider) getArguments().get("dataProvider");
            Log.d(TAG, "afterCreated: 获取了DataProvider");
        }
    }
}
