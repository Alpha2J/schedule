package cn.alpha2j.schedule.app.ui.dialog;

import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.alpha2j.schedule.R;

/**
 *
 * @author alpha
 *         Created on 2018/2/25.
 */
public class ReminderTimeSetterDialog extends DialogFragment {

    private Switch mSwitch;
    private ImageView mImageView;
    private TextView mTextView;
    private Spinner mTimeSpinner;
    private Spinner mTypeSpinner;

    private ReminderWrapper mReminderWrapper;

    private OnReminderSetListener mOnReminderSetListener;

    public ReminderTimeSetterDialog() {

        mReminderWrapper = new ReminderWrapper();
    }

    public static ReminderTimeSetterDialog newInstance(ReminderWrapper reminderWrapper) {

        ReminderTimeSetterDialog dialog = new ReminderTimeSetterDialog();
//        如果时间小于0或者超过60, 那么直接禁用提醒.
//        if (time <= 0 || time > 60) {
//            dialog.setTime(0);
//            dialog.setType(TimeType.TIME_TYPE_MINUTE);
//        } else {
//            dialog.setTime(time);
//            dialog.setType(type);
//        }

        return dialog;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setOnReminderSetListener(OnReminderSetListener onReminderSetListener) {
        mOnReminderSetListener = onReminderSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_reminder_time_setter, null);

//        如果提醒时间为0, 那么关闭提醒,否则打开提醒
        mSwitch = rootView.findViewById(R.id.s_reminder_dialog_alarm_control);
        if (mTime == 0) {
            mSwitch.setChecked(false);
        } else {
            mSwitch.setChecked(true);
        }
        mSwitch.setOnCheckedChangeListener(((compoundButton, b) -> {
            setViewEnable();
        }));

        mImageView = rootView.findViewById(R.id.iv_reminder_dialog_alarm_icon);
        mTextView = rootView.findViewById(R.id.tv_reminder_dialog_alarm_show);
        setDateText();

        mTimeSpinner = rootView.findViewById(R.id.spinner_reminder_dialog_time_num);
        List<Integer> spinnerTimeList = new ArrayList<>();
        for (int i = 0; i <= 60; i++) {
            spinnerTimeList.add(i);
        }
        ArrayAdapter<Integer> spinnerTimeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerTimeList);
        spinnerTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimeSpinner.setAdapter(spinnerTimeAdapter);
        mTimeSpinner.setSelection(mTime);
        mTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTime = spinnerTimeAdapter.getItem(i);
                setDateText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(), "nothing selected", Toast.LENGTH_SHORT).show();
            }
        });

        mTypeSpinner = rootView.findViewById(R.id.spinner_reminder_dialog_time_type);
        List<String> spinnerTypeList = new ArrayList<>();
        spinnerTypeList.add(TimeType.TIME_TYPE_MINUTE);
        spinnerTypeList.add(TimeType.TIME_TYPE_HOUR);
        spinnerTypeList.add(TimeType.TIME_TYPE_DAY);
        ArrayAdapter<String> spinnerTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerTypeList);
        spinnerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(spinnerTypeAdapter);
        switch (mType) {
            case TimeType.TIME_TYPE_MINUTE :
                mTypeSpinner.setSelection(0);
                break;
            case TimeType.TIME_TYPE_HOUR:
                mTypeSpinner.setSelection(1);
                break;
            case TimeType.TIME_TYPE_DAY:
                mTypeSpinner.setSelection(2);
                break;
            default:
                mTypeSpinner.setSelection(0);
        }
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mType = spinnerTypeAdapter.getItem(i);
                setDateText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        根据开关初始化view
        setViewEnable();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(rootView)
                .setPositiveButton("确定", (dialog, id) -> {
//                    如果设置了监听器那么回调
                    if(mOnReminderSetListener != null) {
                        mOnReminderSetListener.onReminderSet(mSwitch.isChecked(), mTime, mType);
                    }
                })
                .setNegativeButton("取消", (dialog, id) -> {
                    ReminderTimeSetterDialog.this.getDialog().cancel();
                });

        return dialogBuilder.create();
    }

    private void setDateText() {

        switch (mType) {
            case TimeType.TIME_TYPE_MINUTE :
                mTextView.setText(getResources().getString(R.string.time_setter_dialog_minute, mTime));
                break;
            case TimeType.TIME_TYPE_HOUR:
                mTextView.setText(getResources().getString(R.string.time_setter_dialog_hour, mTime));
                break;
            case TimeType.TIME_TYPE_DAY:
                mTextView.setText(getResources().getString(R.string.time_setter_dialog_day, mTime));
                break;
            default:
                mTextView.setText(getResources().getString(R.string.time_setter_dialog_minute, mTime));
        }
    }

    private void setViewEnable() {
        if (mSwitch.isChecked()) {
//            如果开关是开的, 那么设置图标为alarm
            mImageView.setImageResource(R.drawable.ic_alarm);
            mTextView.getPaint().setFlags(0);
            mTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
//            且打开下拉选择
            mTimeSpinner.setEnabled(true);
            mTypeSpinner.setEnabled(true);
        } else {
//            如果开关是关的, 那么
//            设置图标为alarm off
            mImageView.setImageResource(R.drawable.ic_alarm_off);
            mTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGreyDark));
//            且下拉选择禁用
            mTimeSpinner.setEnabled(false);
            mTypeSpinner.setEnabled(false);
        }
    }

    /**
     * 当设置完提醒后, 点击ok按钮退出时回调
     */
    public interface OnReminderSetListener {

        /**
         * 点击ok按钮时调用这个方法
         * @param reminderWrapper
         */
        void onReminderSet(ReminderWrapper reminderWrapper);
    }

    public static class ReminderWrapper {

        private boolean isRemind;

        private int num;

        private String timeType;

        public boolean isRemind() {
            return isRemind;
        }

        public void setRemind(boolean remind) {
            isRemind = remind;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }
    }

    public static class TimeType {

        public static final String TIME_TYPE_MINUTE = "分钟";

        public static final String TIME_TYPE_HOUR = "小时";

        public static final String TIME_TYPE_DAY = "天";
    }
}
