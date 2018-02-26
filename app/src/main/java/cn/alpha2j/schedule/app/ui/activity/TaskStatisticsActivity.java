package cn.alpha2j.schedule.app.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;
import java.util.List;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.dialog.YearAndMonthPickerDialog;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.exception.FieldUninitException;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;

/**
 * 任务统计显示图, 当进入这个activity的时候显示的是当月的数据
 * @author alpha
 */
public class TaskStatisticsActivity extends BaseActivity implements YearAndMonthPickerDialog.OnYearAndMonthSetListener {

    private static final String TAG = "TaskStatisticsActivity";
    private static final String DIALOG_TAG = "StatisticsYearAndMonthPickerDialog";

    private TaskService mTaskService;

    private TextView mDateTextView;
    private TextView mTotalNumberTextView;
    private TextView mFinishedNumberTextView;
    private TextView mUnfinishedNumberTextView;
    private ColumnChartView mChartView;
    private PreviewColumnChartView mPreviewChartView;
    private RoundCornerProgressBar mFinishedProgressBar;
    private RoundCornerProgressBar mUnfinishedProgressBar;

    private int mCurrentYear;
    private int mCurrentMonthOfYear;

    private int mTotalNumber;
    private int mFinishedNumber;
    private int mUnfinishedNumber;

    private ColumnChartData mData;
    private ColumnChartData mPreviewData;

    private ArrayList<TaskDateData> mTaskDateDataArrayList;

    @Override
    public void onYearAndMonthSet(int year, int monthOfYear) {
        mCurrentYear = year;
        mCurrentMonthOfYear = monthOfYear;

        resetData();
        showData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_statistics;
    }

    @Override
    protected void initActivity(@Nullable Bundle savedInstanceState) {

        initView();
        initField();
        showData();
    }

    @Override
    protected String getToolbarTitle() {
        return getResources().getString(R.string.activity_toolbar_title_statistics);
    }

    /**
     * 初始化各个view
     */
    private void initView() {

        mDateTextView = findViewById(R.id.tv_statistics_header_date);
        mTotalNumberTextView = findViewById(R.id.tv_statistics_header_total);
        mFinishedNumberTextView = findViewById(R.id.tv_statistics_header_finished);
        mUnfinishedNumberTextView = findViewById(R.id.tv_statistics_header_unfinished);
        mChartView = findViewById(R.id.ccv_statistics_chart);
        mPreviewChartView = findViewById(R.id.pccv_statistics_preview_chart);
        mFinishedProgressBar = findViewById(R.id.rcpb_statistics_header_finished_pb);
        mUnfinishedProgressBar = findViewById(R.id.rcpb_statistics_header_unfinished_pb);
    }

    private void initField() {

        mTaskService = TaskServiceImpl.getInstance();

//        用当前的年和月作为年和月的初始化数据
        ScheduleDateTime now = ScheduleDateTime.now();
        mCurrentYear = now.getYear();
        mCurrentMonthOfYear = now.getMonthOfYear();
    }

    /**
     * 初始化数据, 包括头部总体数据和图标数据
     */
    private void showData() {

        showHeaderData();
        showChartData();
    }

    /**
     * 设置上方的数据显示
     */
    private void showHeaderData() {

//        获取当月的数据
        if (mTaskDateDataArrayList == null) {
            mTaskDateDataArrayList = new ArrayList<>();
        }
        ScheduleDateTime dateTime = DefaultScheduleDateBuilder.now().toDate(mCurrentYear, mCurrentMonthOfYear, 1).getResult();
        int dayNumberOfMonth = dateTime.getMonthDayNumber();
        int totalFinishedNumber = 0;
        int totalUnfinishedNumber = 0;
        for (int i = 1; i <= dayNumberOfMonth; i++) {

            dateTime = DefaultScheduleDateBuilder.of(dateTime).toDayOfMonth(i).getResult();
            long epochMills = dateTime.getEpochMillisecond();

            TaskDateData taskDateData = new TaskDateData();
            taskDateData.setDataBelongingDate(epochMills);

            int finishedNumber = mTaskService.countFinishedForDate(mCurrentYear, mCurrentMonthOfYear, i);
            int unfinishedNumber = mTaskService.countUnfinishedForDate(mCurrentYear, mCurrentMonthOfYear, i);
            taskDateData.setFinishedNumber(finishedNumber);
            taskDateData.setUnfinishedNumber(unfinishedNumber);

            mTaskDateDataArrayList.add(taskDateData);

//            数量添加
            totalFinishedNumber += finishedNumber;
            totalUnfinishedNumber += unfinishedNumber;
        }

//        设置数量
        mFinishedNumber = totalFinishedNumber;
        mUnfinishedNumber = totalUnfinishedNumber;
        mTotalNumber = mFinishedNumber + mUnfinishedNumber;

//        设置头部数据
//        设置左边数据显示
        Resources resources = getResources();
        mDateTextView.setText(resources.getString(R.string.statistics_date_rod, mCurrentYear, mCurrentMonthOfYear));
        mTotalNumberTextView.setText(resources.getString(R.string.statistics_total_num, mTotalNumber));
        mFinishedNumberTextView.setText(resources.getString(R.string.statistics_finished_num, mFinishedNumber));
        mUnfinishedNumberTextView.setText(resources.getString(R.string.statistics_unfinished_num, mUnfinishedNumber));
//        设置右边进度条
        if (mTotalNumber != 0) {
            mFinishedProgressBar.setProgress(100 * mFinishedNumber / mTotalNumber);
            mUnfinishedProgressBar.setProgress(100 * mUnfinishedNumber / mTotalNumber);
        }
    }

    /**
     * 设置下方的图表的显示
     */
    private void showChartData() {

        mData = new ColumnChartData(generateColumns());
        mData.setStacked(true);
        mData.setAxisXBottom(new Axis(generateXBottomAxisValues()));
        mData.setAxisYLeft(new Axis().setHasLines(true));

//        preview chart 的数据, 且preview chart的颜色用灰色来表示
        mPreviewData = new ColumnChartData(mData);
        for (Column column : mPreviewData.getColumns()) {
//            预览表不需要用具体数字来显示数据
            column.setHasLabels(false);
            for (SubcolumnValue value : column.getValues()) {
                value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }

//        设置图标属性
        mChartView.setColumnChartData(mData);
//        设置图表不能放大缩小
        mChartView.setZoomEnabled(false);
//        同时设置图标不能左右滑动, 让previewChart来控制滑动
        mChartView.setScrollEnabled(false);

//        设置预览图标的属性
        mPreviewChartView.setColumnChartData(mPreviewData);
//        previewChart也不应该具有放大缩小功能
        mPreviewChartView.setZoomEnabled(false);
//        设置允许滑动(向左向右滑动来查看数据)
        mPreviewChartView.setScrollEnabled(true);
//        设置滑动框的颜色
        mPreviewChartView.setPreviewColor(ContextCompat.getColor(this, R.color.colorChartPreview));
        mPreviewChartView.setViewportChangeListener(new SimpleViewportChangeListener());

        previewX(true);
    }

    private List<Column> generateColumns() {

        if (mTaskDateDataArrayList == null) {
            throw new FieldUninitException("mTaskDateDataArrayList 域未初始化");
        }

        int columnsNum = mTaskDateDataArrayList.size();
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> subColumnValues;

        for (int i = 0; i < columnsNum; i++) {
            subColumnValues = new ArrayList<>();
            TaskDateData taskDateData = mTaskDateDataArrayList.get(i);
//            一列中, 显示已完成的数量, 然后才是未完成的数量
            subColumnValues.add(new SubcolumnValue(taskDateData.getFinishedNumber(), ContextCompat.getColor(this, R.color.colorTaskFinished)));
            subColumnValues.add(new SubcolumnValue(taskDateData.getUnfinishedNumber(), ContextCompat.getColor(this, R.color.colorTaskUnfinished)));

            Column column = new Column(subColumnValues);
            column.setHasLabels(true);
            columns.add(column);
        }

        return columns;
    }

    private List<AxisValue> generateXBottomAxisValues() {

//        日期从1号开始
        List<AxisValue> axisValues = new ArrayList<>();
        for (int i = 0; i < mTaskDateDataArrayList.size(); i++) {
            AxisValue axisValue = new AxisValue(i);
            axisValue.setLabel(String.valueOf(i + 1));
            axisValues.add(axisValue);
        }

        return axisValues;
    }

    private void previewX(boolean animate) {

//        将图标的高设置为自动计算出来的1.1倍
        Viewport chartMaximumViewport = new Viewport(mChartView.getMaximumViewport());
        chartMaximumViewport.top = chartMaximumViewport.top * 1.2f;
        mChartView.setMaximumViewport(chartMaximumViewport);

//        同理preview图标的高也要设置为1.1倍, 但是这里直接用chart的最大viewport作为参数, 所以不用再显示设置了
        Viewport previewChartMaximumViewport = new Viewport(mChartView.getMaximumViewport());
        mPreviewChartView.setMaximumViewport(previewChartMaximumViewport);

//        设置previewChart的current显示
        Viewport previewChartCurrentViewport = new Viewport(mPreviewChartView.getMaximumViewport());
        float dx = previewChartCurrentViewport.width() / 2.8f;
        previewChartCurrentViewport.inset(dx, 0);

        if(animate) {
            mPreviewChartView.setCurrentViewportWithAnimation(previewChartCurrentViewport);
        } else {
            mPreviewChartView.setCurrentViewport(previewChartCurrentViewport);
        }
    }

    private void resetData() {

        mTaskDateDataArrayList = null;
        mFinishedProgressBar.setProgress(0);
        mUnfinishedProgressBar.setProgress(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_task_statistics_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            case R.id.activity_task_statistics_set_date :
                YearAndMonthPickerDialog dialog = YearAndMonthPickerDialog.newInstance(mCurrentYear, mCurrentMonthOfYear);
                dialog.setOnYearAndMonthSetListener(this);

                dialog.show(getSupportFragmentManager(), DIALOG_TAG);
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private class TaskDateData {

        private int finishedNumber;
        private int unfinishedNumber;

        private long dataBelongingDate;

        public int getFinishedNumber() {
            return finishedNumber;
        }

        public void setFinishedNumber(int finishedNumber) {
            this.finishedNumber = finishedNumber;
        }

        public int getUnfinishedNumber() {
            return unfinishedNumber;
        }

        public void setUnfinishedNumber(int unfinishedNumber) {
            this.unfinishedNumber = unfinishedNumber;
        }

        public long getDataBelongingDate() {
            return dataBelongingDate;
        }

        public void setDataBelongingDate(long dataBelongingDate) {
            this.dataBelongingDate = dataBelongingDate;
        }
    }

    private class SimpleViewportChangeListener implements ViewportChangeListener {

        @Override
        public void onViewportChanged(Viewport viewport) {
            mChartView.setCurrentViewport(viewport);
        }
    }
}