package cn.alpha2j.schedule.app.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import cn.alpha2j.schedule.app.ui.fragment.TaskOverviewFragment;
import cn.alpha2j.schedule.app.ui.fragment.TaskStatisticsFragment;
import cn.alpha2j.schedule.app.ui.fragment.TaskTodayFragment;
import cn.alpha2j.schedule.R;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private interface FragmentConstant {
        String FRAGMENT_TAG = "FragmentTag";
        String FRAGMENT_TAG_TASK_TODAY = "TaskTodayFragment";
        String FRAGMENT_TAG_TASK_OVERVIEW = "TaskOverviewFragment";
        String FRAGMENT_TAG_TASK_STATISTICS = "TaskStatisticsFragment";
    }

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private TaskTodayFragment mTaskTodayFragment;
    private TaskOverviewFragment mTaskOverviewFragment;
    private TaskStatisticsFragment mTaskStatisticsFragment;
    private ConcurrentHashMap<String, Fragment> mMapOfAddedFragments = new ConcurrentHashMap<>();

    private String mCurrentFragment;
    //转移到Fragment中了
//    private FloatingActionButton floatingActionButton;
//    private RecyclerView recyclerView;

//转移到 Fragment中去
//    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> unfinishedTaskAdapter;
//    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> finishedTaskAdapter;
//    private List<RecyclerViewTaskItem> unfinishedTaskList;
//    private List<RecyclerViewTaskItem> finishedTaskList;
//    private TaskService taskService;
//    private TaskAlarm taskAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //如果当前的活动没有被销毁过, 那么直接创建Fragment, 且默认第一个显示的为当天消息的Fragment
        //否则, 获取销毁前显示的Fragment, 将它显示出来
        if(savedInstanceState == null) {
            mCurrentFragment = FragmentConstant.FRAGMENT_TAG_TASK_TODAY;
            displayFragment(mCurrentFragment);
        } else {
            mCurrentFragment = savedInstanceState.getString(FragmentConstant.FRAGMENT_TAG);
            removeAllAndDisplayFragments(mCurrentFragment);
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

//                if(taskTodayFragment == null) {
//                    taskTodayFragment = new TaskTodayFragment();
//                    if(taskTodayFragment.isAdded()) {
//
//                    }
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().add(R.id.fl_home_fragment_container, taskTodayFragment, "TaskTodayFragment");
//                }


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment taskTodayFragment = new TaskTodayFragment();
                transaction.replace(R.id.fl_home_fragment_container, taskTodayFragment, "TaskTodayFragment");
                transaction.commit();
                break;
            case R.id.activity_main_menu_task_overview_item :
                Toast.makeText(this, "任务: 总览", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager1 = getSupportFragmentManager();
                FragmentTransaction transaction1 = fragmentManager1.beginTransaction();

                Fragment taskOverviewFragment = new TaskOverviewFragment();
                transaction1.replace(R.id.fl_home_fragment_container, taskOverviewFragment, "TaskOverviewFragment");
                transaction1.commit();
                break;
            case R.id.activity_main_menu_task_statistics_item :
                Toast.makeText(this, "统计", Toast.LENGTH_SHORT).show();
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

    private void initActivity() {
        //初始化MainActivity所需控件
        initViews();

        //初始化其他域
        initFields();

        //添加material design 的Toolbar
        addToolbar();

        //为左侧抽屉添加点击事件的图标
        changeDisplayHomeAsUpIcon();

        //左侧抽屉NavigationView的相关设置
        initNavigationViewData();

        //为悬浮按钮初始化数据--设置监听事件
        initFloatingActionButtonData();

//        //初始化RecyclerView的数据
//        initRecyclerViewData();
//
//        //初始化提醒器
//        List<Task> taskList = new ArrayList<>();
//        for (RecyclerViewTaskItem taskItem : unfinishedTaskList) {
//            taskList.add(taskItem.getTask());
//        }
//
//        taskAlarm = new TaskAlarm(taskList);
//        TaskAlarmThread taskAlarmThread = new TaskAlarmThread(taskAlarm, getApplicationContext());
//        taskAlarmThread.start();
    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_home_activity_container);
        mNavigationView = (NavigationView) findViewById(R.id.nv_home_side_container);


//        转移到Fragment中
//        floatingActionButton = (FloatingActionButton) findViewById(R.id.activity_main_fab);
//        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
    }

    /**
     * 初始化非View类型的字段
     */
    private void initFields() {

//        转移到Fragment中去
//        unfinishedTaskList = new ArrayList<>();
//        finishedTaskList = new ArrayList<>();
//        unfinishedTaskAdapter = new SwipeableTaskAdapter(unfinishedTaskList);
//        finishedTaskAdapter = new SwipeableTaskAdapter(finishedTaskList);
//
//        taskService = TaskServiceImpl.getInstance();
//
//        List<Task> ufTL = taskService.findAllUnfinishedForToday();
//        List<Task> fTL = taskService.findAllFinishedForToday();
//
//        for (Task task : ufTL) {
//            RecyclerViewTaskItem taskItem = new RecyclerViewTaskItem(task, false);
//            unfinishedTaskList.add(taskItem);
//        }
//
//        for(Task task : fTL) {
//            RecyclerViewTaskItem taskItem = new RecyclerViewTaskItem(task, false);
//            finishedTaskList.add(taskItem);
//        }
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_home_app_bar);
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

    private void initFloatingActionButtonData() {
//        floatingActionButton.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
//            startActivity(intent);
//        });
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

//    /**
//     * 初始化RecyclerView的数据
//     */
//    private void initRecyclerViewData() {
//        RecyclerViewSwipeManager unfinishedRVSManager = new RecyclerViewSwipeManager();
//        RecyclerViewSwipeManager finishedRVSManager = new RecyclerViewSwipeManager();
//
//        //为每个adapter设置监听器
//        SwipeableTaskAdapter tempAdapter = (SwipeableTaskAdapter) unfinishedTaskAdapter;
//        tempAdapter.setEventListener(new SwipeableTaskAdapter.EventListener() {
//            @Override
//            public void onItemRemoved(int position, RecyclerViewTaskItem taskItem) {
//                taskService.setDone(taskItem.getTask());
//                finishedTaskList.add(taskItem);
//                finishedTaskAdapter.notifyDataSetChanged();
//
//
//
//                Snackbar snackbar = Snackbar.make(
//                        findViewById(R.id.activity_main_coordinator_layout),
//                        "一个任务已完成",
//                        Snackbar.LENGTH_LONG
//                );
//
//                snackbar.setAction("撤销", view -> {
//                    taskService.setUnDone(taskItem.getTask());
//                    unfinishedTaskList.add(position, taskItem);
//                    finishedTaskList.remove(finishedTaskList.size() - 1);
//                    finishedTaskAdapter.notifyDataSetChanged();
//                    unfinishedTaskAdapter.notifyItemInserted(position);
//                });
//
//                snackbar.show();
//            }
//
//            @Override
//            public void onItemPinned(int position) {
//                Toast.makeText(MainActivity.this, "未完成pinned", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onItemViewClicked(View view, int target) {
//                if(target == SwipeableTaskAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {
//                    Toast.makeText(MainActivity.this, "点击了未完成的item", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Constants.TASK_TIME_OUT_RECEIVER_ACTION);
//                    sendBroadcast(intent);
//                } else {
//                    Toast.makeText(MainActivity.this, "未完成的delete", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        tempAdapter = (SwipeableTaskAdapter) finishedTaskAdapter;
//        tempAdapter.setEventListener(new SwipeableTaskAdapter.EventListener() {
//            @Override
//            public void onItemRemoved(int position, RecyclerViewTaskItem taskItem) {
//                Toast.makeText(MainActivity.this, "已完成删除", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onItemPinned(int position) {
//                Toast.makeText(MainActivity.this, "已完成pinned", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onItemViewClicked(View view, int target) {
//                if(target == SwipeableTaskAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {
//                    Toast.makeText(MainActivity.this, "点击了已完成的item", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "已完成的delete", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        //用来组合各个section
//        ComposedAdapter composedAdapter = new ComposedAdapter();
//
//        composedAdapter.addAdapter(new SectionHeaderAdapter("未完成"));
//        composedAdapter.addAdapter(unfinishedRVSManager.createWrappedAdapter(unfinishedTaskAdapter));
//        composedAdapter.addAdapter(new SectionHeaderAdapter("已完成"));
//        composedAdapter.addAdapter(finishedRVSManager.createWrappedAdapter(finishedTaskAdapter));
//
//        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
//        //Change animations 在support-v7-recyclerview v22中默认是开启的
//        //这里关闭它为了让item的回滚动画更好地工作
//        animator.setSupportsChangeAnimations(false);
//
//        recyclerView.setAdapter(composedAdapter);
//        recyclerView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setItemAnimator(animator);
//        //为每个item添加下划分割线
//        recyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_divider_h), true));
//
//        //为RecyclerView关联每个Adapter的Manager
//        unfinishedRVSManager.attachRecyclerView(recyclerView);
//        finishedRVSManager.attachRecyclerView(recyclerView);
//    }
}
