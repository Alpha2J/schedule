package cn.alpha2j.schedule.app.ui.dialog;

import android.view.View;
import android.widget.EditText;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleTimeBuilder;
import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 *
 * 一个BottomDialog, 用于快速添加 Task
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskFastAddBottomDialog extends BaseBottomDialog implements View.OnClickListener {

    private OnTaskCreatedListener mOnTaskCreatedListener;
    private EditText mTaskTitle;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_task_fast_add;
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
//        TODO: 测试, 将提醒时间设置为一分钟后, 测试完后记得删除
        task.setAlarm(true);
        int nowMinute = ScheduleDateTime.now().getMinuteOfHour();
        ScheduleDateTime taskAlarmDateTime = DefaultScheduleTimeBuilder.now().toMinute(nowMinute + 1).getResult();
        task.setTaskAlarmDateTime(taskAlarmDateTime);
//        task.setAlarm(false);
//        task.setTaskAlarmDateTime(null);
        task.setDone(false);

        ScheduleDateTime taskDate = DefaultScheduleDateBuilder.now().toDateBegin().getResult();
        task.setTaskDate(taskDate);

        if(mOnTaskCreatedListener != null) {
            mOnTaskCreatedListener.onTaskCreated(task);
        }

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
