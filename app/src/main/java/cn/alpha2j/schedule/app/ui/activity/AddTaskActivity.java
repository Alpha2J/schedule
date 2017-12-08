package cn.alpha2j.schedule.app.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.entity.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class AddTaskActivity extends AppCompatActivity {

    private TextView taskTitle;
    private TextView taskDescription;
    private EditText editDate;
    private EditText editAlarmTime;
    private Switch isAlarm;
    private TaskService taskService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskService = TaskServiceImpl.getInstance();
        taskTitle = (TextView) findViewById(R.id.task_title);
        taskDescription = (TextView) findViewById(R.id.task_description);

        editDate = (EditText) findViewById(R.id.edit_date);
        editDate.setInputType(InputType.TYPE_NULL);
        editDate.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                showDatePickerDialog();
            }
        });
        editDate.setOnClickListener(view -> {
            showDatePickerDialog();
        });

        //选择提醒时间
        editAlarmTime = (EditText) findViewById(R.id.edit_alarm_time);
        editAlarmTime.setInputType(InputType.TYPE_NULL);
        editAlarmTime.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                showTimePickerDialog();
            }
        });
        editAlarmTime.setOnClickListener(view -> {
            showTimePickerDialog();
        });

        isAlarm = (Switch) findViewById(R.id.is_alarm_switch);
        isAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                } else {

                }
            }
        });


        Button button = (Button) findViewById(R.id.add_task_confirm);
        button.setOnClickListener(view -> {
            Task task = new Task();
            task.setTitle(taskTitle.getText().toString());
            task.setDescription(taskDescription.getText().toString());
            String dateStr = editDate.getText().toString();
            Calendar alarmDate = Calendar.getInstance();
            if(dateStr.trim() != "") {
                String[] tempDateStr = dateStr.split("-");
                alarmDate.set(Calendar.YEAR, Integer.valueOf(tempDateStr[0]));
                alarmDate.set(Calendar.MONTH, Integer.valueOf(tempDateStr[1]) - 1);
                alarmDate.set(Calendar.DAY_OF_MONTH, Integer.valueOf(tempDateStr[2]));

                task.setDate(alarmDate.getTime());
            } else {
                task.setDate(alarmDate.getTime());
            }

            if(isAlarm.isChecked()) {
                String alarmTimeStr = editAlarmTime.getText().toString();
                if(alarmTimeStr.trim() != "") {
                    String[] tempAlarmTimeStr = alarmTimeStr.split(":");
                    alarmDate.set(Calendar.HOUR_OF_DAY, Integer.valueOf(tempAlarmTimeStr[0]));
                    alarmDate.set(Calendar.MINUTE, Integer.valueOf(tempAlarmTimeStr[1]));
                    task.setAlarm(true);
                    task.setAlarmDateTime(alarmDate.getTime());
                }
            }

            taskService.addTask(task);
        });

    }

    protected void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, dateSetListener, year, month, day);
        DatePicker datePicker = datePickerDialog.getDatePicker();

        //设置DatePicker的可选日期
        datePicker.setMinDate(calendar.getTimeInMillis());
        //只有包含在内的7天可以选择
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    protected void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, timeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener()
    {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(AddTaskActivity.this, "year: " + year + " date: " + monthOfYear + " day: " + dayOfMonth, Toast.LENGTH_SHORT).show();
            editDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            editAlarmTime.setText(hourOfDay + ":" + minute);
        }
    };
}
