package cn.alpha2j.schedule.app.ui.fragment;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;

/**
 * @author alpha
 */
public class ColumnChartFragment extends BaseChartFragment {

    private ColumnChartView mChartView;
    private PreviewColumnChartView mPreviewChartView;
    private ColumnChartData mData;
    private ColumnChartData mPreviewData;

    public static ColumnChartFragment newInstance(ArrayList<TaskDateData> taskDateDataList) {

        ColumnChartFragment columnChartFragment = new ColumnChartFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("taskDateDataList", taskDateDataList);
        columnChartFragment.setArguments(bundle);

        return columnChartFragment;
    }

    @Override
    public void updateChart(List<TaskDateData> taskDateDataList) {

    }

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_statistics_column_chart;
    }

    @Override
    protected void afterCreated(Bundle savedInstanceState) {

        mChartView = mRootView.findViewById(R.id.ccv_column_chart);
        mPreviewChartView = mRootView.findViewById(R.id.pccv_preview_column_chart);

//        创建完后初始化图标数据
        initChart();
    }

    private void initChart() {

//        从argument中取出保存着的初始化数据进行初始化
        ArrayList<TaskDateData> taskDateDataList = getArguments().getParcelableArrayList("taskDateDataList");
        List<Column> columns = generateData(taskDateDataList);

        mData = new ColumnChartData(columns);
        mData.setAxisXBottom(new Axis().setName("日期"));
        mData.setAxisYLeft(new Axis().setHasLines(true));

//        preview chart 的数据, 且preview chart的颜色用灰色来表示
        mPreviewData = new ColumnChartData(mData);
        for (Column column : mPreviewData.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }

//        将数据设置到ChartView和PreviewChartView中
        mChartView.setColumnChartData(mData);
        mChartView.setZoomEnabled(false);
        mChartView.setScrollEnabled(false);

        mPreviewChartView.setColumnChartData(mPreviewData);
        mPreviewChartView.setViewportChangeListener(new SimpleViewportChangeListener());

        previewX(false);
    }

    private List<Column> generateData(List<TaskDateData> taskDateDataList) {

        if (taskDateDataList == null) {
            throw new NullPointerException("参数taskDateDataList不能为null.");
        }

        int columnsNum = taskDateDataList.size();
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> subColumnValues;
        for (int i = 0; i < columnsNum; i++) {
            subColumnValues = new ArrayList<>();
            TaskDateData taskDateData = taskDateDataList.get(i);
//            一列中, 显示已完成的数量, 然后才是未完成的数量
            subColumnValues.add(new SubcolumnValue(taskDateData.getFinishedNumber(), ChartUtils.COLOR_GREEN));
            subColumnValues.add(new SubcolumnValue(taskDateData.getUnfinishedNumber(), ChartUtils.COLOR_RED));

            columns.add(new Column(subColumnValues));
        }

        return columns;
    }

    private void previewX(boolean animate) {

        Viewport viewport = new Viewport(mChartView.getMaximumViewport());
        float dx = viewport.width() / 4;
        viewport.inset(dx, 0);

        if(animate) {
            mPreviewChartView.setCurrentViewportWithAnimation(viewport);
        } else {
            mPreviewChartView.setCurrentViewport(viewport);
        }

        mPreviewChartView.setZoomType(ZoomType.HORIZONTAL);
    }

    private class SimpleViewportChangeListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport viewport) {
            mChartView.setCurrentViewport(viewport);
        }
    }
}
