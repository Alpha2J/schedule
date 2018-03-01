package cn.alpha2j.schedule.app.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.dialog.DescriptionSetterDialog;
import cn.alpha2j.schedule.app.ui.dialog.ReminderSetterDialog;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleTimeBuilder;


/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskAddActivity extends BaseActivity {

    private EditText mTaskTitleEditText;
    private TextView mTaskDateTextView;
    private TextView mTaskTimeTextView;
    private LinearLayout mReminderLinearLayout;
    private ImageView mReminderIcon;
    private TextView mReminderTextView;
    private LinearLayout mDescriptionLinearLayout;
    private TextView mDescriptionTextView;

    private DateAndTimeWrapper mDateAndTimeWrapper;
    private ReminderWrapper mReminderWrapper;
    private String mDescription;

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    private OnTaskCreatedListener mOnTaskCreatedListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_add;
    }

    @Override
    protected void initActivity(@Nullable Bundle savedInstanceState) {

//        实例化日期和时间的设置监听器
        mOnDateSetListener = (datePicker, year, monthOfYear, dayOfMonth) -> {
            mDateAndTimeWrapper.setDate(year, monthOfYear + 1, dayOfMonth);
            refreshDateAndTimeText();
        };
        mOnTimeSetListener = (timePicker, hourOfDay, minuteOfHour) -> {
            mDateAndTimeWrapper.setTime(hourOfDay, minuteOfHour);
            refreshDateAndTimeText();
        };
        mDateAndTimeWrapper = new DateAndTimeWrapper();
        mReminderWrapper = new ReminderWrapper();
        mDescription = "";

        initViews();
    }

    private void initViews() {

        mTaskTitleEditText = findViewById(R.id.et_task_add_title);
//        添加点击事件, 点击弹出日期 选择框
        mTaskDateTextView = findViewById(R.id.tv_task_add_date);
        mTaskDateTextView.setOnClickListener(view -> {
            showDatePickerDialog();
        });
//        添加点击事件, 点击出现时间选择框;
        mTaskTimeTextView = findViewById(R.id.tv_task_add_time);
        mTaskTimeTextView.setOnClickListener(view -> showTimePickerDialog());
        refreshDateAndTimeText();

//        获取图标
        mReminderIcon = findViewById(R.id.iv_task_add_alarm_icon);
//        设置点击事件, 点击弹出dialog, 选择是否提醒, 如果提醒则设置提醒时间, 且将时间显示到reminderTextView中
        mReminderLinearLayout = findViewById(R.id.ll_task_add_reminder_container);
        mReminderTextView = findViewById(R.id.tv_task_add_reminder);
        mReminderLinearLayout.setOnClickListener(view -> {
            ReminderSetterDialog reminderTimeSetterDialog = ReminderSetterDialog.newInstance(mReminderWrapper);
            reminderTimeSetterDialog.setOnReminderSetListener(reminderWrapper -> {
                this.mReminderWrapper = reminderWrapper;
                refreshReminderText();
            });
            reminderTimeSetterDialog.show(getSupportFragmentManager(), "ReminderTimeSetterDialog");
        });
//        设置点击事件, 点击弹出dialog, 将当前已有描述传进去, 且设置到descriptionTextView中.
        mDescriptionLinearLayout = findViewById(R.id.ll_task_add_description_container);
        mDescriptionTextView = findViewById(R.id.tv_task_add_description);
        mDescriptionLinearLayout.setOnClickListener(view -> {

            DescriptionSetterDialog dialog = new DescriptionSetterDialog(TaskAddActivity.this, mDescription);
            dialog.setOnDescriptionWroteListener(description -> {
                mDescription = description;
                refreshDescriptionText();
            });
            dialog.show();
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

    private void refreshDateAndTimeText() {

        if(mDateAndTimeWrapper.isToday()) {
            mTaskDateTextView.setText(getResources().getString(R.string.task_add_string_today));
        } else {
            mTaskDateTextView.setText(getResources().getString(R.string.task_add_date, mDateAndTimeWrapper.getYear(), mDateAndTimeWrapper.getMonthOfYear(), mDateAndTimeWrapper.getDayOfMonth()));
        }

        mTaskTimeTextView.setText(getResources().getString(R.string.task_add_time, mDateAndTimeWrapper.getHourOfDay(), mDateAndTimeWrapper.getMinuteOfHour()));
    }

    private void refreshReminderText() {

        if(mReminderWrapper.isRemind()) {
            mReminderIcon.setImageResource(R.drawable.ic_alarm);
            mReminderTextView.setText(getResources().getString(R.string.task_add_string_reminder_text, mReminderWrapper.getNum(), mReminderWrapper.getTimeType().getName()));
        } else {
            mReminderIcon.setImageResource(R.drawable.ic_alarm_off);
            mReminderTextView.setText(getResources().getString(R.string.task_add_string_no_alarm));
        }
    }

    private void refreshDescriptionText() {

        if (mDescription == null || "".equals(mDescription)) {
            mDescriptionTextView.setText(getResources().getString(R.string.task_add_string_new_desc));
            mDescriptionTextView.setTextColor(ContextCompat.getColor(this, R.color.colorGreyDark));
        } else {
            mDescriptionTextView.setText(mDescription);
            mDescriptionTextView.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        }
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
                TaskService taskService = TaskServiceImpl.getInstance();
                Task task = new Task();
                task.setTitle(mTaskTitleEditText.getText().toString());
                task.setDone(false);
                task.setTaskDate(mDateAndTimeWrapper.getResult());
                if(mReminderWrapper.isRemind()) {
                    task.setAlarm(true);
                    long t = mDateAndTimeWrapper.getResult().getEpochMillisecond() - mReminderWrapper.getResult();
                    task.setTaskAlarmDateTime(ScheduleDateTime.of(t));
                } else {
                    task.setAlarm(false);
                }

                task.setDescription(mDescription);
                taskService.addTask(task);

                if(mOnTaskCreatedListener != null) {
                    mOnTaskCreatedListener.onTaskCreated(task);
                }

                finish();

                Toast.makeText(this, "添加成功!", Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnTaskCreatedListener {

        /**
         * 获取到用户输入的信息后回调
         *
         * @param task
         */
        void onTaskCreated(Task task);
    }

    public static class DateAndTimeWrapper {

//        todo 这里的话就要区分ScheduleDate和ScheduleTime了, 还要再重写
        private ScheduleDateTime mScheduleDateTime;

        public DateAndTimeWrapper() {
            mScheduleDateTime = ScheduleDateTime.now();
        }

        public int getYear() {

            return mScheduleDateTime.getYear();
        }

        public int getMonthOfYear() {

            return mScheduleDateTime.getMonthOfYear();
        }

        public int getDayOfMonth() {

            return mScheduleDateTime.getDayOfMonth();
        }

        public int getHourOfDay() {

            return mScheduleDateTime.getHourOfDay();
        }

        public int getMinuteOfHour() {

            return mScheduleDateTime.getMinuteOfHour();
        }

        public void setDate(int year, int monthOfYear, int dayOfMonth) {

            mScheduleDateTime = DefaultScheduleDateBuilder.of(mScheduleDateTime).toDate(year, monthOfYear, dayOfMonth).getResult();
        }

        public void setTime(int hourOfDay, int minuteOfHour) {

            mScheduleDateTime = DefaultScheduleTimeBuilder.of(mScheduleDateTime).toTime(hourOfDay, minuteOfHour, 0).getResult();
        }

        public boolean isToday() {

            ScheduleDateTime today = ScheduleDateTime.now();

            if (today.getYear() == mScheduleDateTime.getYear() && today.getMonthOfYear() == mScheduleDateTime.getMonthOfYear() && today.getDayOfMonth() == mScheduleDateTime.getDayOfMonth()) {
                return true;
            }

            return false;
        }

        public ScheduleDateTime getResult() {

            return mScheduleDateTime;
        }
    }

    /**
     * 包含提醒设置, 是否提醒, 提醒类型
     */
    public static class ReminderWrapper {

        private boolean remind;

        private int num;

        private TimeType mTimeType;

        public ReminderWrapper() {
//            默认为不提醒, 且时间类型为分钟
            remind = false;
            num = 0;
            mTimeType = TimeType.MINUTE;
        }

        public boolean isRemind() {
            return remind;
        }

        public void setRemind(boolean remind) {
            this.remind = remind;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public TimeType getTimeType() {
            return mTimeType;
        }

        public void setTimeType(TimeType timeType) {
            mTimeType = timeType;
        }

//        以毫秒的形式返回
        public long getResult() {
//            如果不提醒那么返回0
            if(remind) {
                switch (mTimeType) {
                    case MINUTE:
                        return num * 60 * 1000;
                    case HOUR:
                        return num * 60 * 60 * 1000;
                    case DAY:
                        return num * 24 * 60 * 60 * 1000;
                    default:
                        return num * 60 * 1000;
                }
            } else {
                return 0;
            }
        }

        public enum TimeType {
            MINUTE("分钟"),
            HOUR("小时"),
            DAY("天");

            private String mName;

            TimeType(String name) {
                this.mName = name;
            }

            public String getName() {
                return mName;
            }
        }
    }
}
