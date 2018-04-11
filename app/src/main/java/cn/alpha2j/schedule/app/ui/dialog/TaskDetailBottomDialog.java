package cn.alpha2j.schedule.app.ui.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Wrapper;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.activity.TaskAddActivity;
import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * @author alpha
 *         Created on 2018/4/11.
 */
public class TaskDetailBottomDialog extends BaseBottomDialog {

    private TextView mTimeTextView;

    private ImageButton mEditButton;

    private TextView mTitleTextView;

    private TextView mDescriptionTextView;

    public static TaskDetailBottomDialog getInstance(TaskDetailWrapper wrapper) {

        TaskDetailBottomDialog dialog = new TaskDetailBottomDialog();

        Bundle args = new Bundle();
        args.putSerializable("wrapper", wrapper);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_task_detail;
    }

    @Override
    public void bindView(View v) {

        mTimeTextView = v.findViewById(R.id.tv_td_time);
        mEditButton = v.findViewById(R.id.ib_td_edit_icon);
        mTitleTextView = v.findViewById(R.id.tv_td_title);
        mDescriptionTextView = v.findViewById(R.id.tv_td_description);

        TaskDetailWrapper wrapper = (TaskDetailWrapper) getArguments().get("wrapper");
        mTimeTextView.setText(wrapper.getTimeStr());
        mTitleTextView.setText(wrapper.getTitle());
        mDescriptionTextView.setText(wrapper.getDescription());

        mEditButton.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), TaskAddActivity.class);
            intent.putExtra("taskId", wrapper.getId());
            startActivity(intent);
            this.dismiss();
        });
    }

    public static class TaskDetailWrapper implements Serializable {

        private long id;

        private String title;

        private String description;

        private String timeStr;

        public TaskDetailWrapper() { }

        public TaskDetailWrapper(long id, String title, String description, String timeStr) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.timeStr = timeStr;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }
    }
}
