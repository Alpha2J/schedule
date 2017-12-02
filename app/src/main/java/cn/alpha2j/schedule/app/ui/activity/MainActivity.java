package cn.alpha2j.schedule.app.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.composedadapter.ComposedAdapter;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.app.thread.TaskAlarmThread;
import cn.alpha2j.schedule.app.ui.adapter.SectionHeaderAdapter;
import cn.alpha2j.schedule.app.ui.adapter.SwipeableTaskAdapter;
import cn.alpha2j.schedule.app.ui.data.RecyclerViewTaskItem;
import cn.alpha2j.schedule.app.util.TaskAlarm;
import cn.alpha2j.schedule.entity.Task;
import cn.alpha2j.schedule.service.TaskService;
import cn.alpha2j.schedule.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.app.ui.dialog.AddTaskBottomDialog;
import cn.alpha2j.schedule.R;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private NavigationView navigationView;

    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> unfinishedTaskAdapter;
    private RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder> finishedTaskAdapter;

    private List<RecyclerViewTaskItem> unfinishedTaskList;
    private List<RecyclerViewTaskItem> finishedTaskList;
    private TaskService taskService;
    private TaskAlarm taskAlarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //初始化RecyclerView的数据
        initRecyclerViewData();

        //初始化提醒器
        List<Task> taskList = new ArrayList<>();
        for (RecyclerViewTaskItem taskItem : unfinishedTaskList) {
            taskList.add(taskItem.getTask());
        }

        taskAlarm = new TaskAlarm(taskList);
        TaskAlarmThread taskAlarmThread = new TaskAlarmThread(taskAlarm, getApplicationContext());
        taskAlarmThread.start();
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
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.activity_main_menu_add_item:
                AddTaskBottomDialog addTaskBottomDialog = new AddTaskBottomDialog();
                addTaskBottomDialog.setOnTaskAddedListener(task -> {
                    unfinishedTaskList.add(new RecyclerViewTaskItem(task, false));
                    unfinishedTaskAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(unfinishedTaskList.size() - 1);
                    taskAlarm.addTask(task);
                });
                addTaskBottomDialog.show(getSupportFragmentManager());
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
                break;
            case R.id.activity_main_menu_task_overview_item :
                Toast.makeText(this, "任务: 总览", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_main_menu_task_statistics_item :
                Toast.makeText(this, "统计", Toast.LENGTH_SHORT).show();
                break;
            case R.id.activity_main_menu_settings_item :
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        drawerLayout.closeDrawers();

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.activity_main_fab);
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
    }

    /**
     * 初始化非View类型的字段
     */
    private void initFields() {
        unfinishedTaskList = new ArrayList<>();
        finishedTaskList = new ArrayList<>();

        unfinishedTaskAdapter = new SwipeableTaskAdapter(unfinishedTaskList);
        finishedTaskAdapter = new SwipeableTaskAdapter(finishedTaskList);

        taskService = TaskServiceImpl.getInstance();

        List<Task> ufTL = taskService.findAllUnfinishedForToday();
        List<Task> fTL = taskService.findAllFinishedForToday();

        for (Task task : ufTL) {
            RecyclerViewTaskItem taskItem = new RecyclerViewTaskItem(task, false);
            unfinishedTaskList.add(taskItem);
        }

        for(Task task : fTL) {
            RecyclerViewTaskItem taskItem = new RecyclerViewTaskItem(task, false);
            finishedTaskList.add(taskItem);
        }
    }

    private void addToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
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
        navigationView.setCheckedItem(R.id.activity_main_menu_profile_item);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initFloatingActionButtonData() {
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 初始化RecyclerView的数据
     */
    private void initRecyclerViewData() {
        RecyclerViewSwipeManager unfinishedRVSManager = new RecyclerViewSwipeManager();
        RecyclerViewSwipeManager finishedRVSManager = new RecyclerViewSwipeManager();

        //为每个adapter设置监听器
        SwipeableTaskAdapter tempAdapter = (SwipeableTaskAdapter) unfinishedTaskAdapter;
        tempAdapter.setEventListener(new SwipeableTaskAdapter.EventListener() {
            @Override
            public void onItemRemoved(int position, RecyclerViewTaskItem taskItem) {
                taskService.setDone(taskItem.getTask());
                finishedTaskList.add(taskItem);
                finishedTaskAdapter.notifyDataSetChanged();



                Snackbar snackbar = Snackbar.make(
                        findViewById(R.id.activity_main_coordinator_layout),
                        "一个任务已完成",
                        Snackbar.LENGTH_LONG
                );

                snackbar.setAction("撤销", view -> {
                    taskService.setUnDone(taskItem.getTask());
                    unfinishedTaskList.add(position, taskItem);
                    finishedTaskList.remove(finishedTaskList.size() - 1);
                    finishedTaskAdapter.notifyDataSetChanged();
                    unfinishedTaskAdapter.notifyItemInserted(position);
                });

                snackbar.show();
            }

            @Override
            public void onItemPinned(int position) {
                Toast.makeText(MainActivity.this, "未完成pinned", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemViewClicked(View view, int target) {
                if(target == SwipeableTaskAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {
                    Toast.makeText(MainActivity.this, "点击了未完成的item", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Constants.TASK_TIME_OUT_RECEIVER_ACTION);
                    sendBroadcast(intent);
                } else {
                    Toast.makeText(MainActivity.this, "未完成的delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tempAdapter = (SwipeableTaskAdapter) finishedTaskAdapter;
        tempAdapter.setEventListener(new SwipeableTaskAdapter.EventListener() {
            @Override
            public void onItemRemoved(int position, RecyclerViewTaskItem taskItem) {
                Toast.makeText(MainActivity.this, "已完成删除", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemPinned(int position) {
                Toast.makeText(MainActivity.this, "已完成pinned", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemViewClicked(View view, int target) {
                if(target == SwipeableTaskAdapter.EventListener.TASK_ITEM_CLICK_EVENT) {
                    Toast.makeText(MainActivity.this, "点击了已完成的item", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "已完成的delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //用来组合各个section
        ComposedAdapter composedAdapter = new ComposedAdapter();

        composedAdapter.addAdapter(new SectionHeaderAdapter("未完成"));
        composedAdapter.addAdapter(unfinishedRVSManager.createWrappedAdapter(unfinishedTaskAdapter));
        composedAdapter.addAdapter(new SectionHeaderAdapter("已完成"));
        composedAdapter.addAdapter(finishedRVSManager.createWrappedAdapter(finishedTaskAdapter));

        final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
        //Change animations 在support-v7-recyclerview v22中默认是开启的
        //这里关闭它为了让item的回滚动画更好地工作
        animator.setSupportsChangeAnimations(false);

        recyclerView.setAdapter(composedAdapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(animator);
        //为每个item添加下划分割线
        recyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getApplicationContext(), R.drawable.list_divider_h), true));

        //为RecyclerView关联每个Adapter的Manager
        unfinishedRVSManager.attachRecyclerView(recyclerView);
        finishedRVSManager.attachRecyclerView(recyclerView);
    }
}
