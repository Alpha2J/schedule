package cn.alpha2j.schedule.app.ui.activity;

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
 * 任务统计显示图, 当进入这个activity的时候显示的是当月的数据
 * @author alpha
 */
public class TaskStatisticsActivity extends BaseActivity implements YearAndMonthPickerDialog.OnYearAndMonthSetListener {

    private TaskService mTaskService;

    private TextView mDateTextView;
    private TextView mTotalNumberTextView;
    private TextView mFinishedNumberTextView;
    private TextView mUnfinishedNumberTextView;
    private ColumnChartView mChartView;
    private PreviewColumnChartView mPreviewChartView;
    private RoundCornerProgressBar mFinishedProgressBar;
    private RoundCornerProgressBar mUnfinishedProgressBar;

    private int mYear;
    private int mMonthOfYear;

    private int mTotalNumber;
    private int mFinishedNumber;
    private int mUnfinishedNumber;

    private ColumnChartData mData;
    private ColumnChartData mPreviewData;

    private ArrayList<TaskDateData> mTaskDateDataArrayList;

    @Override
    public void onYearAndMonthSet(int year, int monthOfYear) {
        mYear = year;
        mMonthOfYear = monthOfYear;

        showData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_statistics;
    }

    @Override
    protected void initActivity() {

        initView();
        initField();
        showData();
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
        mYear = now.getYear();
        mMonthOfYear = now.getMonthOfYear();
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
        ScheduleDateTime dateTime = DefaultScheduleDateBuilder.now().toDate(mYear, mMonthOfYear, 1).getResult();
        if (mTaskDateDataArrayList == null) {
            mTaskDateDataArrayList = new ArrayList<>();
        }
        int dayNumberOfMonth = dateTime.getMonthDayNumber();
        int totalFinishedNumber = 0;
        int totalUnfinishedNumber = 0;
        for (int i = 1; i <= dayNumberOfMonth; i++) {
            dateTime = DefaultScheduleDateBuilder.of(dateTime).toDayOfMonth(i).getResult();
            long epochMills = dateTime.getEpochMillisecond();

            TaskDateData taskDateData = new TaskDateData();
            taskDateData.setDataBelongingDate(epochMills);

            int finishedNumber = mTaskService.countFinishedForDate(mYear, mMonthOfYear, i);
            int unfinishedNumber = mTaskService.countUnfinishedForDate(mYear, mMonthOfYear, i);
            taskDateData.setFinishedNumber(finishedNumber);
            taskDateData.setUnfinishedNumber(unfinishedNumber);

            mTaskDateDataArrayList.add(taskDateData);

//            数量添加
            totalFinishedNumber += finishedNumber;
            totalUnfinishedNumber += unfinishedNumber;
        }

//        设置数量
        mTotalNumber = totalFinishedNumber + totalUnfinishedNumber;
        mFinishedNumber = totalFinishedNumber;
        mUnfinishedNumber = totalUnfinishedNumber;

//        设置头部数据
//        设置左边数据显示
        mDateTextView.setText(mYear + " - " + mMonthOfYear);
        mTotalNumberTextView.setText("总数量 " + mTotalNumber);
        mFinishedNumberTextView.setText("已完成 " + mFinishedNumber);
        mUnfinishedNumberTextView.setText("未完成 " + mUnfinishedNumber);
//        设置右边进度条
        if (mTotalNumber != 0) {
            mFinishedProgressBar.setProgress(mFinishedNumber / mTotalNumber);
            mUnfinishedProgressBar.setProgress(mUnfinishedNumber / mTotalNumber);
        }
    }

    /**
     * 设置下方的图表的显示
     */
    private void showChartData() {

        mData = new ColumnChartData(generateColumns());
        mData.setAxisXBottom(new Axis().setName("日期"));
        mData.setAxisYLeft(new Axis().setHasLines(true));
//        preview chart 的数据, 且preview chart的颜色用灰色来表示
        mPreviewData = new ColumnChartData(mData);
        for (Column column : mPreviewData.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }
        mChartView.setColumnChartData(mData);
        mChartView.setZoomEnabled(false);
        mChartView.setScrollEnabled(false);

        mPreviewChartView.setColumnChartData(mPreviewData);
        mPreviewChartView.setViewportChangeListener(new SimpleViewportChangeListener());

        previewX(false);
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
                YearAndMonthPickerDialog dialog = new YearAndMonthPickerDialog();
                dialog.setOnYearAndMonthSetListener(this);

                dialog.show(getSupportFragmentManager(), "YearAndMonthPickerDialog");
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
