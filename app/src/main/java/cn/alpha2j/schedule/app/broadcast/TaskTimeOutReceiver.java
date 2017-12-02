package cn.alpha2j.schedule.app.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import cn.alpha2j.schedule.MyApplication;

/**
 * 任务的提醒时间到了就会发送一条广播, 用这个广播接收器来进行接收.
 *
 * @author alpha
 * Created on 2017/11/14.
 */
public class TaskTimeOutReceiver extends BroadcastReceiver {

    private static final String TAG = "TaskTimeOutReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        if(intent == null) {
            return;
        }

        Log.d(TAG, "onReceive: 定时任务执行了");
        Toast.makeText(MyApplication.getContext(), "时间到了", Toast.LENGTH_SHORT).show();
    }
}
