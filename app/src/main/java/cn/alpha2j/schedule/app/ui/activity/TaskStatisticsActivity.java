package cn.alpha2j.schedule.app.ui.activity;

import android.view.Menu;
import android.view.MenuItem;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.dialog.YearAndMonthPickerDialog;

/**
 * 任务统计显示图, 当进入这个activity的时候显示的是当月的数据
 * @author alpha
 */
public class TaskStatisticsActivity extends BaseActivity implements YearAndMonthPickerDialog.OnYearAndMonthSetListener {

    @Override
    public void onYearAndMonthSet(int year, int month) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_statistics;
    }

    @Override
    protected void initActivity() {

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

    //    private Toolbar mToolbar;
//    private TabLayout mTabLayout;
//    private ViewPager mViewPager;
//    private TextView mDatePicker;
//
//    private List<String> pageTitles;
//    private List<BaseChartFragment> pageFragments;
//
//    private ArrayList<BaseChartFragment.TaskDateData> mTaskDateDataList;
//
//    private TaskService mTaskService;
//
//    private int currentYear;
//    private int currentMonth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task_statistics);
//
//    }
//
//    /**
//     * 初始化视图
//     */
//    private void initView() {
//

//
//        mTabLayout = findViewById(R.id.tl_statistic_page_title);
//        mViewPager = findViewById(R.id.vp_statistic_page_container);
//        mDatePicker = findViewById(R.id.tv_statistics_date_picker);
//        mDatePicker.setOnClickListener(view -> {
//            YearAndMonthPickerDialog dialog = new YearAndMonthPickerDialog();
//            dialog.setOnYearAndMonthSetListener(this);
//
//            dialog.show(getSupportFragmentManager(), "YearAndMonthPickerDialog");
//        });
//
//        pageTitles = new ArrayList<>();
//        pageTitles.add("条形图");
//        pageTitles.add("曲线图");
//        pageTitles.add("扇形图");
//
//        pageFragments = new ArrayList<>();
//        pageFragments.add(new ColumnChartFragment());
//        pageFragments.add(new LineChartFragment());
//        pageFragments.add(new PieChartFragment());
//
//        SimpleViewPagerAdapter viewPagerAdapter = new SimpleViewPagerAdapter(getSupportFragmentManager(), pageFragments, pageTitles);
//        mViewPager.setAdapter(viewPagerAdapter);
//
//        mTabLayout.setupWithViewPager(mViewPager);
//
//        mTaskService = TaskServiceImpl.getInstance();
//    }
//
//    /**
//     * 初始化图标数据 从数据库读取
//     */
//    private void initTaskDateData() {
//
//        mTaskService = TaskServiceImpl.getInstance();
//        mTaskDateDataList = new ArrayList<>();
//
//    }
//

//
//    @Override
//    public void onYearAndMonthSet(int year, int month) {
//
//        ScheduleDateTime dateTime = DefaultScheduleDateBuilder.now().toDate(year, month, 1).getResult();
//
//        ArrayList<BaseChartFragment.TaskDateData> taskDateDataList = new ArrayList<>();
//
//        int monthDayNumber = dateTime.getMonthDayNumber();
//        for (int i = 1; i <= monthDayNumber; i++) {
//            dateTime = DefaultScheduleDateBuilder.of(dateTime).toDayOfMonth(i).getResult();
//            long epochMills = dateTime.getEpochMillisecond();
//
//            BaseChartFragment.TaskDateData taskDateData = new BaseChartFragment.TaskDateData();
//            taskDateData.setDataBelongingDate(epochMills);
//            taskDateData.setUnfinishedNumber(mTaskService.countUnfinishedForDate(year, month, i));
//            taskDateData.setFinishedNumber(mTaskService.countFinishedForDate(year, month, i));
//
//            taskDateDataList.add(taskDateData);
//        }
//
//        int currentPage = mViewPager.getCurrentItem();
//        pageFragments.get(currentPage).updateChart(taskDateDataList);
//    }
}
