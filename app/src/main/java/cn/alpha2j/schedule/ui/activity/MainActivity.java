package cn.alpha2j.schedule.ui.activity;

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

import cn.alpha2j.schedule.service.TaskService;
import cn.alpha2j.schedule.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.ui.dialog.AddTaskBottomDialog;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.ui.adapter.TaskListAdapter;
import cn.alpha2j.schedule.entity.Task;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private List<Task> taskList;
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //添加material design 的Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //为左侧抽屉添加点击事件的图标
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //左侧抽屉NavigationView的相关设置
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_profile);
        navigationView.setNavigationItemSelectedListener(this);

        //为下部的添加悬浮按钮设置监听事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        //初始化RecyclerView(用当天数据)
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        TaskService taskService = new TaskServiceImpl();
        taskList = taskService.findAllForToday();

        adapter = new TaskListAdapter(taskList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //HomeAsUp按钮的id就是 android.R.id.home
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.add:
                AddTaskBottomDialog addTaskBottomDialog = new AddTaskBottomDialog();
                addTaskBottomDialog.setOnTaskAddedListener(task -> {
                    taskList.add(task);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(taskList.size() - 1);
                });
                addTaskBottomDialog.show(getSupportFragmentManager());
                break;
            case R.id.setting:
                Toast.makeText(this, "点击了设置图标", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                Toast.makeText(this, "个人简介", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_task_today:
                Toast.makeText(this, "任务: 今天", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_task_next_seven_day:
                Toast.makeText(this, "任务: 接下来7天", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_task_history:
                Toast.makeText(this, "任务: 历史", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_task_statistics:
                Toast.makeText(this, "统计分析", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_setting:
                Toast.makeText(this, "设置中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(this, "关于", Toast.LENGTH_SHORT).show();
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
}
