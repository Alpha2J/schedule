package cn.alpha2j.schedule.app.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import cn.alpha2j.schedule.app.ui.data.TaskDataProvider;
import cn.alpha2j.schedule.app.ui.data.observer.DataObserver;
import cn.alpha2j.schedule.app.ui.dialog.AddTaskBottomDialog;
import cn.alpha2j.schedule.app.ui.fragment.BaseFragment;
import cn.alpha2j.schedule.app.ui.fragment.TaskDataProviderFragment;
import cn.alpha2j.schedule.app.ui.fragment.TaskOverviewFragment;
import cn.alpha2j.schedule.app.ui.fragment.TaskStatisticsFragment;
import cn.alpha2j.schedule.app.ui.fragment.TaskTodayFragment;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.data.creator.TodayFinishedDataProviderCreator;
import cn.alpha2j.schedule.app.ui.data.creator.TodayUnfinishedDataProviderCreator;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TaskDataProvider.TaskDataProviderGetter {

    public interface FragmentConstant {
        String FRAGMENT_TAG = "FragmentTag";
        String FRAGMENT_TAG_TASK_TODAY = "TaskTodayFragment";
        String FRAGMENT_TAG_TASK_OVERVIEW = "TaskOverviewFragment";
        String FRAGMENT_TAG_TASK_STATISTICS = "TaskStatisticsFragment";

        String FRAGMENT_TAG_TASK_TODAY_UNFINISHED_DATA = "TaskTodayUnfinishedDataFragment";
        String FRAGMENT_TAG_TASK_TODAY_FINISHED_DATA = "TaskTodayFinishedDataFragment";
    }

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFloatingActionButton;

    private TaskTodayFragment mTaskTodayFragment;
    private TaskOverviewFragment mTaskOverviewFragment;
    private TaskStatisticsFragment mTaskStatisticsFragment;
    private ConcurrentHashMap<String, Fragment> mMapOfAddedFragments = new ConcurrentHashMap<>();

    private String mCurrentFragment;

    private List<DataObserver> mObservers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();

        //添加数据存储的Fragment
        //已经完成的任务
        BaseFragment finishedTaskDataProviderFrag = TaskDataProviderFragment.newInstance(new TodayFinishedDataProviderCreator());
        getSupportFragmentManager().beginTransaction().add(finishedTaskDataProviderFrag, FragmentConstant.FRAGMENT_TAG_TASK_TODAY_FINISHED_DATA).commit();
        //未完成的任务
        BaseFragment unfinishedTaskDataProviderFrag = TaskDataProviderFragment.newInstance(new TodayUnfinishedDataProviderCreator());
        getSupportFragmentManager().beginTransaction().add(unfinishedTaskDataProviderFrag, FragmentConstant.FRAGMENT_TAG_TASK_TODAY_UNFINISHED_DATA).commit();

        //如果当前的活动没有被销毁过, 那么直接创建Fragment, 且默认第一个显示的为当天消息的Fragment
//        否则, 获取销毁前显示的Fragment, 将它显示出来
        if(savedInstanceState == null) {
            mCurrentFragment = FragmentConstant.FRAGMENT_TAG_TASK_TODAY;
            displayFragment(mCurrentFragment);
        } else {
            mCurrentFragment = savedInstanceState.getString(FragmentConstant.FRAGMENT_TAG);
//            这个方法不用了, 因为activity重建的时候里面什么东西都没有, 只需要获得相应的fragment就可以了
//            removeAllAndDisplayFragments(mCurrentFragment);
//            用这个方法代替
            displayFragment(mCurrentFragment);
        }
        //设置选中项
        switch (mCurrentFragment) {
            case FragmentConstant.FRAGMENT_TAG_TASK_TODAY :
                mNavigationView.setCheckedItem(R.id.activity_main_menu_task_today_item);
                break;
            case FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW :
                mNavigationView.setCheckedItem(R.id.activity_main_menu_task_overview_item);
                break;
            case FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS :
                mNavigationView.setCheckedItem(R.id.activity_main_menu_task_statistics_item);
                break;
            default:
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

//        将当前fragment 的类型传入
//        做if判断好像有点臃余, 不过能增加健壮性
        if(mCurrentFragment != null) {
            outState.putString(FragmentConstant.FRAGMENT_TAG, this.mCurrentFragment);
        } else {
            outState.putString(FragmentConstant.FRAGMENT_TAG, FragmentConstant.FRAGMENT_TAG_TASK_TODAY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //HomeAsUp按钮的id就是 android.R.id.home
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.activity_main_menu_add_item:
                AddTaskBottomDialog addTaskBottomDialog = new AddTaskBottomDialog();
                addTaskBottomDialog.setOnTaskAddedListener(task -> {

                });
                addTaskBottomDialog.show(getSupportFragmentManager());

//                AddTaskBottomDialog addTaskBottomDialog = new AddTaskBottomDialog();
//                addTaskBottomDialog.setOnTaskAddedListener(task -> {
//                    unfinishedTaskList.add(new RecyclerViewTaskItem(task, false));
//                    unfinishedTaskAdapter.notifyDataSetChanged();
//                    recyclerView.scrollToPosition(unfinishedTaskList.size() - 1);
//                    taskAlarm.addTask(task);
//                });
//                addTaskBottomDialog.show(getSupportFragmentManager());
                break;
            case R.id.activity_main_menu_normal_setting_item:
                Toast.makeText(this, "点击了toolbar上的设置图标", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_main_menu_profile_item :
                Toast.makeText(this, "个人简介", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_main_menu_task_today_item :
                Toast.makeText(this, "任务: 今天", Toast.LENGTH_SHORT).show();
                displayFragment(FragmentConstant.FRAGMENT_TAG_TASK_TODAY);
                break;
            case R.id.activity_main_menu_task_overview_item :
                Toast.makeText(this, "任务: 总览", Toast.LENGTH_SHORT).show();

                displayFragment(FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW);
                break;
            case R.id.activity_main_menu_task_statistics_item :
                Toast.makeText(this, "统计", Toast.LENGTH_SHORT).show();

                displayFragment(FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS);
                break;
            case R.id.activity_main_menu_settings_item :
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        mDrawerLayout.closeDrawers();

        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public TaskDataProvider getFinishedTaskDataProvider() {

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentConstant.FRAGMENT_TAG_TASK_TODAY_FINISHED_DATA);

        return (TaskDataProvider) ((TaskDataProviderFragment)fragment).getDataProvider();
    }

    @Override
    public TaskDataProvider getUnfinishedTaskDataProvider() {

        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentConstant.FRAGMENT_TAG_TASK_TODAY_UNFINISHED_DATA);

        return (TaskDataProvider) ((TaskDataProviderFragment)fragment).getDataProvider();
    }

    private void initActivity() {
        //初始化MainActivity所需控件
        initViews();

        //添加material design 的Toolbar
        addToolbar();

        //为左侧抽屉添加点击事件的图标
        changeDisplayHomeAsUpIcon();

        //左侧抽屉NavigationView的相关设置
        initNavigationViewData();

        //为floatingactionbutton添加监听事件
        setFloatingActionButtonListener();
    }

    private void initViews() {
        mDrawerLayout = findViewById(R.id.dl_home_activity_container);
        mNavigationView = findViewById(R.id.nv_home_sidebar);
        mFloatingActionButton = findViewById(R.id.fab_home_add_task_btn);
    }

    private void addToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_home_app_bar);
        setSupportActionBar(toolbar);
    }

    private void changeDisplayHomeAsUpIcon() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void initNavigationViewData() {
        mNavigationView.setCheckedItem(R.id.activity_main_menu_profile_item);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void setFloatingActionButtonListener() {
        mFloatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }


    private void displayFragment(String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentTag) {
            case FragmentConstant.FRAGMENT_TAG_TASK_TODAY :
                if(mTaskTodayFragment == null) {
                    mTaskTodayFragment = new TaskTodayFragment();
                    mMapOfAddedFragments.put(FragmentConstant.FRAGMENT_TAG_TASK_TODAY, mTaskTodayFragment);
                }

                if(mTaskTodayFragment.isAdded()) {
                    transaction.show(mTaskTodayFragment);
                } else {
                    transaction.add(R.id.fl_home_fragment_container, mTaskTodayFragment, FragmentConstant.FRAGMENT_TAG_TASK_TODAY);
                }

                hideOthersFragments(fragmentTag);
                break;
            case FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW :
                if(mTaskOverviewFragment == null) {
                    mTaskOverviewFragment = new TaskOverviewFragment();
                    mMapOfAddedFragments.put(FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW, mTaskOverviewFragment);
                }

                if(mTaskOverviewFragment.isAdded()) {
                    transaction.show(mTaskOverviewFragment);
                } else {
                    transaction.add(R.id.fl_home_fragment_container, mTaskOverviewFragment, FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW);
                }

                hideOthersFragments(fragmentTag);
                break;
            case FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS :
                if(mTaskStatisticsFragment == null) {
                    mTaskStatisticsFragment = new TaskStatisticsFragment();
                    mMapOfAddedFragments.put(FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS, mTaskStatisticsFragment);
                }

                if(mTaskStatisticsFragment.isAdded()) {
                    transaction.show(mTaskStatisticsFragment);
                } else {
                    transaction.add(R.id.fl_home_fragment_container, mTaskStatisticsFragment, FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS);
                }

                hideOthersFragments(fragmentTag);
                break;
            default:
        }

        transaction.commit();
    }

    /**
     * 还有漏洞, 需要将其他的fragment设置为null, 这样在显示fragment的时候才能重新初始化
     * (其实好像也不用设置为null, 因为这个方法只在activity重建的时候调用, Activity重建的时候所有字段都是空的)
     *
     * @param fragmentTag
     */
    private void removeAllAndDisplayFragments(String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentTag) {
            case FragmentConstant.FRAGMENT_TAG_TASK_TODAY :
                mTaskTodayFragment = new TaskTodayFragment();
                transaction.replace(R.id.fl_home_fragment_container, mTaskTodayFragment, FragmentConstant.FRAGMENT_TAG_TASK_TODAY);

                if(mMapOfAddedFragments.size() != 0) {
                    mMapOfAddedFragments.clear();
                }

                mMapOfAddedFragments.put(FragmentConstant.FRAGMENT_TAG_TASK_TODAY, mTaskTodayFragment);
                break;
            case FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW :
                mTaskOverviewFragment = new TaskOverviewFragment();
                transaction.replace(R.id.fl_home_fragment_container, mTaskOverviewFragment, FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW);

                if(mMapOfAddedFragments.size() != 0) {
                    mMapOfAddedFragments.clear();
                }

                mMapOfAddedFragments.put(FragmentConstant.FRAGMENT_TAG_TASK_OVERVIEW, mTaskOverviewFragment);
                break;
            case FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS :
                mTaskStatisticsFragment = new TaskStatisticsFragment();
                transaction.replace(R.id.fl_home_fragment_container, mTaskStatisticsFragment, FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS);

                if(mMapOfAddedFragments.size() != 0) {
                    mMapOfAddedFragments.clear();
                }

                mMapOfAddedFragments.put(FragmentConstant.FRAGMENT_TAG_TASK_STATISTICS, mTaskStatisticsFragment);
                break;
            default:
        }

        transaction.commit();
    }

    private void hideOthersFragments(String fragmentTag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for (ConcurrentHashMap.Entry<String, Fragment> entry : mMapOfAddedFragments.entrySet()) {
            if (!entry.getKey().equals(fragmentTag)) {
                Fragment fragment = entry.getValue();

                if(fragment != null) {
                    if(fragment.isAdded()) {
                        transaction.remove(fragment);
                    }
                }
            }
        }

        transaction.commit();
    }
}
