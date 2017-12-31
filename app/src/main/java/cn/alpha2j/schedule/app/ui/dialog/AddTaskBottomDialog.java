package cn.alpha2j.schedule.app.ui.dialog;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;
import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 *
 * 一个BottomDialog, 用于快速添加 Task
 * @author alpha
 * Created on 2017/11/4.
 */
public class AddTaskBottomDialog extends BaseBottomDialog implements View.OnClickListener {

    private OnTaskCreatedListener mOnTaskCreatedListener;
    private EditText mTaskTitle;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main_bottom_dialog_add_task;
    }

    @Override
    public void bindView(View v) {
        v.findViewById(R.id.bottom_dialog_add_done_btn).setOnClickListener(this);
        mTaskTitle = v.findViewById(R.id.bottom_dialog_task_title);
    }

    @Override
    public void onClick(View v) {

        //快速生成Task, 包含 Task 的Title, 时间为当天
        Task task = new Task();
        task.setTitle(mTaskTitle.getText().toString());
        task.setDescription(null);
        task.setAlarm(false);
        task.setTaskAlarmDateTime(null);
        task.setDone(false);

        ScheduleDateTime taskDate = DefaultScheduleDateBuilder.now().toDateBegin().getResult();
        task.setTaskDate(taskDate);

        if(mOnTaskCreatedListener != null) {
            mOnTaskCreatedListener.onTaskCreated(task);
        }

//        更正, 不再在这里进行持久化, 获取到用户输入数据后用监听器通知监听者
//        进行持久化
//        TaskService taskService = TaskServiceImpl.getInstance();
//        long taskId = taskService.addTask(task);
//
//        if(taskId != -1) {
//            Toast.makeText(MyApplication.getContext(), "添加成功", Toast.LENGTH_SHORT).show();
//
//            task.setId(taskId);
//
//            //持久化成功后回调方法
//            if(mOnTaskAddedListener != null) {
//                mOnTaskAddedListener.onTaskAdded(task);
//            }
//        } else {
//            Toast.makeText(MyApplication.getContext(), "添加失败", Toast.LENGTH_SHORT).show();
//        }

        this.dismiss();
    }

    public void setOnTaskCreatedListener(OnTaskCreatedListener onTaskCreatedListener) {
        this.mOnTaskCreatedListener = onTaskCreatedListener;
    }

    public interface OnTaskCreatedListener {

        /**
         * 获取到用户输入的信息后回调
         *
         * @param task
         */
        void onTaskCreated(Task task);
    }
}
