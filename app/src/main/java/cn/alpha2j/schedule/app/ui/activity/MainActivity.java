package cn.alpha2j.schedule.app.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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

import java.util.ArrayList;
import java.util.List;

import cn.alpha2j.schedule.app.thread.TaskAlarmThread;
import cn.alpha2j.schedule.app.ui.adapter.RecyclerViewTaskAdapter;
import cn.alpha2j.schedule.app.util.TaskAlarm;
import cn.alpha2j.schedule.service.TaskService;
import cn.alpha2j.schedule.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.app.ui.dialog.AddTaskBottomDialog;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.adapter.TaskListAdapter;
import cn.alpha2j.schedule.entity.Task;

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

    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    private List<Task> taskList;
    private TaskService taskService;

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
                    taskList.add(task);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(taskList.size() - 1);
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
        adapter = new RecyclerViewTaskAdapter();
        taskList = new ArrayList<>();
        taskService = TaskServiceImpl.getInstance();
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
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
