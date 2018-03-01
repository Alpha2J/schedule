package cn.alpha2j.schedule.app.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 *         Created on 2018/2/26.
 */
public class DescriptionSetterDialog {

    private Dialog mDialog;
    private ImageButton mConfirmButton;
    private EditText mDescriptionInput;
    private OnDescriptionWroteListener mOnDescriptionWroteListener;

    public DescriptionSetterDialog(Context context, String description) {
        mDialog = new Dialog(context, R.style.CustomDialogFullscreen);
        mDialog.setContentView(R.layout.dialog_description_setter);
        mConfirmButton = mDialog.findViewById(R.id.ib_dialog_desc_confirm_btn);
        mDescriptionInput = mDialog.findViewById(R.id.et_dialog_desc_content_input);
        if(description != null) {
            mDescriptionInput.setText(description);
            mDescriptionInput.setSelection(description.length());
        }

        mConfirmButton.setOnClickListener(view -> {

            if(mOnDescriptionWroteListener != null) {
                mOnDescriptionWroteListener.onDescriptionWrote(mDescriptionInput.getText().toString());
            }
            dismiss();
        });
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void setOnDescriptionWroteListener(OnDescriptionWroteListener onDescriptionWroteListener) {
        mOnDescriptionWroteListener = onDescriptionWroteListener;
    }

    public interface OnDescriptionWroteListener {

        void onDescriptionWrote(String description);
    }
}
