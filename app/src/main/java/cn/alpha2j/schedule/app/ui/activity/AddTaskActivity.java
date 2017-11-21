package cn.alpha2j.schedule.app.ui.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button btn = (Button) findViewById(R.id.btn_pick_date);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, dateSetListener, year, month, day);
                DatePicker datePicker = datePickerDialog.getDatePicker();
                //后一天
                calendar.set(Calendar.DAY_OF_MONTH, 25);
                datePicker.setMinDate(calendar.getTimeInMillis());
                calendar.set(Calendar.DAY_OF_MONTH, 29);
                datePicker.setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
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
            Calendar calendar = Calendar.getInstance();

//
//            //修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
//            year=myyear;
//            month=monthOfYear;
//            day=dayOfMonth;
//            //更新日期
//            updateDate();
//
//        }
//        //当DatePickerDialog关闭时，更新日期显示
//        private void updateDate()
//        {
//            //在TextView上显示日期
//            showdate.setText("当前日期："+year+"-"+(month+1)+"-"+day);
//        }
        }
    };
}
