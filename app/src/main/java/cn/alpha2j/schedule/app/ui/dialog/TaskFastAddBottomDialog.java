package cn.alpha2j.schedule.app.ui.dialog;

import android.app.TimePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.entity.ReminderSetting;
import cn.alpha2j.schedule.app.ui.helper.ApplicationSettingHelper;
import cn.alpha2j.schedule.app.ui.listener.OnTaskCreatedListener;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleTimeBuilder;
import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * 一个BottomDialog, 用于快速添加 Task
 *
 * @author alpha
 *         Created on 2017/11/4.
 */
public class TaskFastAddBottomDialog extends BaseBottomDialog {

    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    private OnTaskCreatedListener mOnTaskCreatedListener;
    private TextView mTime;
    private TextInputLayout mTaskTitleWrapper;
    private TextInputEditText mTaskTitle;
    private ScheduleDateTime mTaskTime;

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_task_fast_add;
    }

    @Override
    public void bindView(View v) {
//        初始化域
        mOnTimeSetListener = (timePicker, hourOfDay, minuteOfHour) -> {
            mTaskTime = DefaultScheduleTimeBuilder.of(mTaskTime).toHour(hourOfDay).toMinute(minuteOfHour).getResult();
            refreshTimeText();
        };
//        精确到分钟
        mTaskTime = DefaultScheduleTimeBuilder.now().toMinuteStart().getResult();
//        设置确定按钮的监听事件
        v.findViewById(R.id.bottom_dialog_add_done_btn).setOnClickListener(view -> {
            checkAndGenerateTask();
        });
//        刷新text view显示
        mTime = v.findViewById(R.id.tv_fast_add_dialog_time);
        mTime.setOnClickListener(view -> {
            showTimePickerDialog();
        });
        refreshTimeText();

        mTaskTitleWrapper = v.findViewById(R.id.bottom_dialog_task_title_wrapper);
        mTaskTitle = v.findViewById(R.id.bottom_dialog_task_title);
        mTaskTitle.setOnClickListener(view -> {
            mTaskTitleWrapper.setError(null);
        });
    }

    public void setOnTaskCreatedListener(OnTaskCreatedListener onTaskCreatedListener) {
        this.mOnTaskCreatedListener = onTaskCreatedListener;
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), mOnTimeSetListener, hour, minute, true);
        timePickerDialog.show();
    }

    /**
     * 更新任务的时间的显示
     */
    private void refreshTimeText() {
        mTime.setText(getResources().getString(R.string.common_today_hour_and_minute, mTaskTime.getHourOfDay(), mTaskTime.getMinuteOfHour()));
    }

    private void checkAndGenerateTask() {
        String title = mTaskTitle.getText().toString();

        if ("".equals(title)) {
            mTaskTitleWrapper.setError("请输入标题!");
            return;
        }
        if (title.length() > Constants.FIELD_LIMIT_TASK_TITLE) {
            mTaskTitleWrapper.setError("标题超出最大长度!");
            return;
        }

        Task task = new Task();
        task.setTitle(title);
        task.setTime(mTaskTime);
        task.setDone(false);
        task.setDescription(null);

//        设置提醒
        ReminderSetting reminderSetting = ApplicationSettingHelper.getReminderSetting();
        if(reminderSetting.isRemind()) {
            ReminderSetterDialog.ReminderWrapper reminderWrapper = new ReminderSetterDialog.ReminderWrapper();
            reminderWrapper.setNum(reminderSetting.getNum());
            reminderWrapper.setRemind(reminderSetting.isRemind());
            reminderWrapper.setTimeType(reminderSetting.getRemindTimeType());
            task.setRemind(true);
            task.setRemindTime(ScheduleDateTime.of(mTaskTime.getEpochMillisecond() - reminderWrapper.getResultAsEpochMills()));
        } else {
            task.setRemind(false);
            task.setRemindTime(null);
        }

        if (mOnTaskCreatedListener != null) {
            mOnTaskCreatedListener.onTaskCreated(task);
        }

        this.dismiss();
    }
}
