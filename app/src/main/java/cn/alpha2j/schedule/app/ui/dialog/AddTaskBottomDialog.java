package cn.alpha2j.schedule.app.ui.dialog;

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
import cn.alpha2j.schedule.util.AlarmDateTimeGenerator;
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
        return R.layout.activity_main_bottom_dialog_add_task;
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
        task.setDescription(null);
        task.setDate(new Date());
        task.setAlarm(false);
        task.setAlarmDateTime(null);
        task.setDone(false);

        //进行持久化
        TaskService taskService = TaskServiceImpl.getInstance();
        long isSuccess = taskService.addTask(task);

        if(isSuccess != -1) {
            Toast.makeText(MyApplication.getContext(), "添加成功", Toast.LENGTH_SHORT).show();

            task.setId((int)isSuccess);

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
