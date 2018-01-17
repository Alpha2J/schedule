package cn.alpha2j.schedule.app.ui.dialog;


import android.app.DatePickerDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Calendar;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 */
public class YearAndMonthPickerDialog extends DialogFragment {

    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 2099;
    private OnYearAndMonthSetListener mOnYearAndMonthSetListener;

    public YearAndMonthPickerDialog() {

    }

    public void setOnYearAndMonthSetListener(OnYearAndMonthSetListener onYearAndMonthSetListener) {
        this.mOnYearAndMonthSetListener = onYearAndMonthSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.dialog_year_and_month_picker, null);

        Calendar calendar = Calendar.getInstance();
        final NumberPicker yearPicker = dialogView.findViewById(R.id.np_year);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(calendar.get(Calendar.YEAR));

        final NumberPicker monthPicker = dialogView.findViewById(R.id.np_month);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(calendar.get(Calendar.MONTH) + 1);

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

        void onYearAndMonthSet(int year, int month);
    }
}
