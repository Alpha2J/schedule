package cn.alpha2j.schedule.app.ui.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;

/**
 * 测试时插数据用
 */
public class TestActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mAddOneFinished;
    private Button mAddOneUnfinished;
    private Button mAddBulk;

    private TaskService mTaskService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTaskService = TaskServiceImpl.getInstance();

        mToolbar = findViewById(R.id.tb_test_app_bar);
        mToolbar.setTitle("添加测试数据");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAddOneFinished = findViewById(R.id.btn_test_add_one_f);
        mAddOneUnfinished = findViewById(R.id.btn_test_add_one_uf);
        mAddBulk = findViewById(R.id.btn_test_add_bulk);

        mAddOneFinished.setOnClickListener(view -> {
            addOne(true);
        });

        mAddOneUnfinished.setOnClickListener(view -> {
            addOne(false);
        });

        mAddBulk.setOnClickListener(view -> {
            generateBulkData();
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void addOne(boolean isFinished) {
        ScheduleDateTime scheduleDateTime = ScheduleDateTime.now();
        int year = scheduleDateTime.getYear();
        int month = scheduleDateTime.getMonthOfYear();
        int day = scheduleDateTime.getDayOfMonth();

        DatePickerDialog.OnDateSetListener dateSetListener = ((datePicker, y, m, d) -> {
            generateData(y, m + 1, d, isFinished);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        });
        DatePickerDialog datePickerDialog = new DatePickerDialog(TestActivity.this, dateSetListener, year, month - 1, day);
        datePickerDialog.show();
    }

    private void generateData(int year, int monthOfYear, int dayOfMonth, boolean isFinished) {
        Task task = new Task();
        ScheduleDateTime scheduleDateTime = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayOfMonth).toDateBegin().getResult();
        task.setTime(scheduleDateTime);
        task.setTitle("generated task, random id is: " + new Random().nextInt(1000));
        task.setDone(isFinished);

        mTaskService.addTask(task);
    }

    /**
     * 一次添加50条数据, 时间都是在2017年
     */
    private void generateBulkData() {
        int number = 50;

        int month;
//        日的话1到28好了
        int day;
        boolean isFinished;
        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < number; i++) {
            month = random.nextInt(11) + 1;
            day = random.nextInt(28) + 1;
            isFinished = random.nextBoolean();
            Task task = new Task();
            task.setTitle("Random Bulk Generate");
            ScheduleDateTime scheduleDateTime = DefaultScheduleDateBuilder.now().toDate(2017, month, day).toDateBegin().getResult();
            task.setTime(scheduleDateTime);
            task.setDone(isFinished);

            mTaskService.addTask(task);
        }
    }
}