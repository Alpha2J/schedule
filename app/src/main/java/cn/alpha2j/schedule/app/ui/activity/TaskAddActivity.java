package cn.alpha2j.schedule.app.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.dialog.ReminderTimeSetterDialog;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.time.ScheduleDateTime;


/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskAddActivity extends BaseActivity {

    private EditText mTaskTitleEditText;
    private TextView mTaskDateTextView;
    private TextView mTaskTimeTextView;
    private LinearLayout mReminderLinearLayout;
    private TextView mReminderTextView;
    private LinearLayout mDescriptionLinearLayout;
    private TextView mDescriptionTextView;

    private Task mTask;
    private ReminderTimeSetterDialog.ReminderWrapper mReminderWrapper;

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_add;
    }

    @Override
    protected void initActivity(@Nullable Bundle savedInstanceState) {

//        先实例化一个Task
        mTask = new Task();
//        实例化日期和时间的设置监听器
        mOnDateSetListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            Toast.makeText(this, "年份: " + year + " 月份: " + (monthOfYear + 1) + " 日期: " + dayOfMonth, Toast.LENGTH_SHORT).show();
            mTaskDateTextView.setText(getResources().getString(R.string.task_add_date, year, (monthOfYear + 1), dayOfMonth));
        };
        mOnTimeSetListener = (timePicker, hourOfDay, minuteOfHour) -> {
            Toast.makeText(this, "小时: " + hourOfDay + " 分钟: " + minuteOfHour, Toast.LENGTH_SHORT).show();
            mTaskTimeTextView.setText(getResources().getString(R.string.task_add_time, hourOfDay, minuteOfHour));
        };
        initViews();
    }

    private void initViews() {

        mTaskTitleEditText = findViewById(R.id.et_task_add_title);
//        添加点击事件, 点击弹出日期 选择框
        mTaskDateTextView = findViewById(R.id.tv_task_add_date);
        mTaskDateTextView.setOnClickListener(view -> {
            showDatePickerDialog();
        });
//        设置时间显示为当前时间; 添加点击事件, 点击出现时间选择框;
        mTaskTimeTextView = findViewById(R.id.tv_task_add_time);
        mTaskTimeTextView.setText(getResources().getString(R.string.task_add_time, ScheduleDateTime.now().getHourOfDay(), ScheduleDateTime.now().getMinuteOfHour()));
        mTaskTimeTextView.setOnClickListener(view -> {
            showTimePickerDialog();
        });
//        设置点击事件, 点击弹出dialog, 选择是否提醒, 如果提醒则设置提醒时间, 且将时间显示到reminderTextView中
        mReminderLinearLayout = findViewById(R.id.ll_task_add_reminder_container);
        mReminderTextView = findViewById(R.id.tv_task_add_reminder);
        mReminderLinearLayout.setOnClickListener(view -> {
            ReminderTimeSetterDialog reminderTimeSetterDialog = new ReminderTimeSetterDialog();
            reminderTimeSetterDialog.setOnReminderSetListener(new ReminderTimeSetterDialog.OnReminderSetListener() {
                @Override
                public void onReminderSet(boolean isRemind, int time, String type) {

                }
            });
            reminderTimeSetterDialog.show(getSupportFragmentManager(), "ReminderTimeSetterDialog");
        });
//        设置点击事件, 点击弹出dialog, 将当前已有描述传进去, 且设置到descriptionTextView中.
        mDescriptionLinearLayout = findViewById(R.id.ll_task_add_description_container);
        mDescriptionTextView = findViewById(R.id.tv_task_add_description);
        mDescriptionLinearLayout.setOnClickListener(view -> {

        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(TaskAddActivity.this, mOnDateSetListener, year, month, day);
        DatePicker datePicker = datePickerDialog.getDatePicker();

//        设置DatePicker的可选日期, 只有包含当天在内的7天可以选择(这样不合理)
//        包含当天在内的5年可选
        datePicker.setMinDate(calendar.getTimeInMillis());
        calendar.add(Calendar.YEAR, 5);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(TaskAddActivity.this, mOnTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_task_add_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            case R.id.activity_task_add_save :
//                点击保存则将内容保存到数据库; 然后结束该activity, 回到首页, toast提示是否保存成功.
                Toast.makeText(this, "add an item", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}
