package cn.alpha2j.schedule.app.remind;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.entity.ReminderSetting;
import cn.alpha2j.schedule.data.Task;

/**
 * 状态栏通知进行提醒
 *
 * @author alpha
 *         Created on 2018/3/22.
 */
public class NotificationReminder extends AbstractReminder implements Reminder {

    private static final String TAG = "NotificationReminder";

    public NotificationReminder() {

    }

    public NotificationReminder(ReminderSetting reminderSetting, Context context) {
        super(reminderSetting, context);
    }

    @Override
    public void remind(Task task) {
        NotificationManager notificationManager = (NotificationManager) MyApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getContext(), String.valueOf(task.getId()))
                .setContentTitle(task.getTitle())
                .setContentText(task.getDescription())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.mipmap.ic_launcher_round))
                .setPriority(NotificationCompat.PRIORITY_MAX);

//        设置铃声
        builder.setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, mReminderSetting.getRemindNotificationRingtone()));

        if(mReminderSetting.isRemindVibrate()) {
            builder.setVibrate(new long[]{0, 1000, 1000, 1000});
        }

        notificationManager.notify(task.getId().intValue(), builder.build());
        Log.d(TAG, "remind: 进行了提醒, task id为" + task.getId());
    }
}
