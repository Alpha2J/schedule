package cn.alpha2j.schedule.app.ui.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.time.ScheduleDateTime;

/**
 * @author alpha
 */
public class YearAndMonthPickerDialog extends DialogFragment {

    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 2099;
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;
    private OnYearAndMonthSetListener mOnYearAndMonthSetListener;

    private int mCurrentYear;
    private int mCurrentMonthOfYear;

    public YearAndMonthPickerDialog() {

//        如果是调用构造参数来初始化dialog的, 那么以当前时间的年和月作为初始化时间
        ScheduleDateTime now = ScheduleDateTime.now();
        setCurrentYear(now.getYear());
        setCurrentMonthOfYear(now.getMonthOfYear());
    }

    public static YearAndMonthPickerDialog newInstance(int currentYear, int currentMonth) {

        YearAndMonthPickerDialog dialog = new YearAndMonthPickerDialog();
        dialog.setCurrentYear(currentYear);
        dialog.setCurrentMonthOfYear(currentMonth);

        return dialog;
    }

    public int getCurrentYear() {
        return mCurrentYear;
    }

    public void setCurrentYear(int currentYear) {

        if (currentYear < MIN_YEAR || currentYear > MAX_YEAR) {
            throw new IllegalArgumentException("年份参数不合法.");
        }

        mCurrentYear = currentYear;
    }

    public int getCurrentMonthOfYear() {
        return mCurrentMonthOfYear;
    }

    public void setCurrentMonthOfYear(int currentMonthOfYear) {

        if (currentMonthOfYear < MIN_MONTH || currentMonthOfYear > MAX_MONTH) {
            throw new IllegalArgumentException("月份参数不合法.");
        }

        mCurrentMonthOfYear = currentMonthOfYear;
    }

    public void setOnYearAndMonthSetListener(OnYearAndMonthSetListener onYearAndMonthSetListener) {
        this.mOnYearAndMonthSetListener = onYearAndMonthSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_year_and_month_picker, null);

        final NumberPicker yearPicker = dialogView.findViewById(R.id.np_year);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(getCurrentYear());

        final NumberPicker monthPicker = dialogView.findViewById(R.id.np_month);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(getCurrentMonthOfYear());

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setView(dialogView)
                .setPositiveButton("确定", (dialog, id) -> {
                    if(mOnYearAndMonthSetListener != null) {
                        mOnYearAndMonthSetListener.onYearAndMonthSet(yearPicker.getValue(), monthPicker.getValue());
                    }
                })
                .setNegativeButton("取消", (dialog, id) -> {
                    YearAndMonthPickerDialog.this.getDialog().cancel();
                });

        return dialogBuilder.create();
    }

    public interface OnYearAndMonthSetListener {

        /**
         * 当年和月设置好了后会回调这个方法, 如果监听器存在
         *
         * @param year 年
         * @param monthOfYear 月, 从1 开始
         */
        void onYearAndMonthSet(int year, int monthOfYear);
    }
}
