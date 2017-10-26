package cn.alpha2j.schedule;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

//import me.shaohui.bottomdialog.BottomDialog;
//
public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //添加material design 的Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //为左侧抽屉添加事件图标
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //左侧抽屉NavigationView的相关设置
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_profile);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

        //为下部的添加悬浮按钮设置监听事件
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Snackbar.make(v, "点击了添加按钮", Snackbar.LENGTH_SHORT).setAction("撤销", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(MainActivity.this, "你已经撤销了添加", Toast.LENGTH_SHORT).show();
//                            }
//                        }).show();
                        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                        startActivity(intent);
                    }
                }
        );

        //设置RecyclerView
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        final List<Task> taskList = new ArrayList<>();
//        taskList.add(new Task("今晚打老虎"));
//        taskList.add(new Task("记得吃饭"));
//        taskList.add(new Task("学习英语"));
//        taskList.add(new Task("学习英语"));
//        taskList.add(new Task("学习英语"));
//        taskList.add(new Task("学习英语"));
//        taskList.add(new Task("学习英语"));
//
//        for (int i = 0; i < 100; i++) {
//            taskList.add(new Task("学习英语"));
//        }

//        TaskListAdapter adapter = new TaskListAdapter(taskList);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        MyItemDecoration itemDecoration = new MyItemDecoration();
//        recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallback() {
//            @Override
//            public long getGroupId(int position) {
//                return Character.toUpperCase(taskList.get(position).getTitle().charAt(0));
//            }
//
//            @Override
//            public String getGroupFirstLine(int position) {
//                return taskList.get(position).getTitle().substring(0, 1).toUpperCase();
//            }
//        }));
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
                Toast.makeText(this, "点击了添加按钮", Toast.LENGTH_SHORT).show();
//                BottomDialog.create(getSupportFragmentManager())
//                        .setLayoutRes(R.layout.dialog_add_task)
//                        .show();
                break;
            case R.id.setting:
                Toast.makeText(this, "点击了设置图标", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        return true;
    }
}
