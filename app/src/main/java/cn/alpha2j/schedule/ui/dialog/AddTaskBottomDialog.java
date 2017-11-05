package cn.alpha2j.schedule.ui.dialog;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.entity.Task;
import cn.alpha2j.schedule.service.TaskService;
import cn.alpha2j.schedule.service.impl.TaskServiceImpl;
import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 *
 * 一个BottomDialog, 用于快速添加 Task
 * @author alpha
 * Created on 2017/11/4.
 */
public class AddTaskBottomDialog extends BaseBottomDialog implements View.OnClickListener {

    private OnTaskAddedListener mOnTaskAddedListener;
    private EditText mTaskTitle;

    @Override
    public int getLayoutRes() {
        return R.layout.bottom_dialog_add_task;
    }

    @Override
    public void bindView(View v) {
        v.findViewById(R.id.bottom_dialog_add_done_btn).setOnClickListener(this);
        mTaskTitle = (EditText) v.findViewById(R.id.bottom_dialog_task_title);
    }

    @Override
    public void onClick(View v) {
        //快速生成Task, 包含 Task 的Title, 时间为当天
        Task task = new Task();
        task.setTitle(mTaskTitle.getText().toString());
        task.setDate(new Date());
        task.setAlarm(false);

        //设置提醒时间, 暂定为当天 13 点
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 13);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Date alarmDateTime = new Date(today.getTimeInMillis() / 1000);
        task.setAlarmDateTime(alarmDateTime);

        //进行持久化
        TaskService taskService = new TaskServiceImpl();
        boolean isSuccess = taskService.addTask(task);

        if(isSuccess) {
            Toast.makeText(MyApplication.getContext(), "添加成功", Toast.LENGTH_SHORT).show();

            //持久化成功后回调方法
            if(mOnTaskAddedListener != null) {
                mOnTaskAddedListener.onTaskAdded(task);
            }
        } else {
            Toast.makeText(MyApplication.getContext(), "添加失败", Toast.LENGTH_SHORT).show();
        }

        this.dismiss();
    }

    public void setOnTaskAddedListener(OnTaskAddedListener onTaskAddedListener) {
        this.mOnTaskAddedListener = onTaskAddedListener;
    }

    public interface OnTaskAddedListener {
        /**
         * Task 持久化成功后回调
         * @param task
         */
        void onTaskAdded(Task task);
    }
}
