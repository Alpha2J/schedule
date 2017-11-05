package cn.alpha2j.schedule.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.entity.Task;
import cn.alpha2j.schedule.repository.ScheduleDatabaseHelper;
import cn.alpha2j.schedule.repository.TaskRepository;
import cn.alpha2j.schedule.util.DateUtils;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskRepositoryImpl implements TaskRepository {

    private ScheduleDatabaseHelper mDatabaseHelper;

    public TaskRepositoryImpl(ScheduleDatabaseHelper mDatabaseHelper) {
        this.mDatabaseHelper = mDatabaseHelper;
    }

    @Override
    public long addTask(Task task) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", task.getTitle());
        contentValues.put("description", task.getDescription());

        long secondsForDate = DateUtils.generateSecondsForDate(task.getDate());
        contentValues.put("date", secondsForDate);

        contentValues.put("isAlarm", task.isAlarm());
        contentValues.put("alarmDateTime", task.getAlarmDateTime().getTime());

        long id = database.insert(Constants.TABLE_NAME_TASK, null, contentValues);

        return id;
    }

    @Override
    public List<Task> findAllByDate(Date date) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        //设置时间为当天0点
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        today.set(Calendar.HOUR_OF_DAY, 0);

        //当天0点的时间戳
        long secondsForToday = DateUtils.generateSecondsForToday();

        Cursor cursor = database.query(Constants.TABLE_NAME_TASK, null, "date = ?", new String[] {String.valueOf(secondsForToday)}, null, null, null);

        List<Task> taskList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex("id")));
            task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            task.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            task.setDate(new Date(cursor.getLong(cursor.getColumnIndex("date"))));
            //设置是否提醒
            boolean isAlarm = (cursor.getInt(cursor.getColumnIndex("isAlarm"))) == 1;
            task.setAlarm(isAlarm);
            //设置提醒时间
            task.setAlarmDateTime(new Date(cursor.getLong(cursor.getColumnIndex("alarmDateTime"))));

            taskList.add(task);
        }

        cursor.close();

        return taskList;
    }
}
