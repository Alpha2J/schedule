package cn.alpha2j.schedule.data.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.alpha2j.schedule.Constants;
import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.data.entity.Task;
import cn.alpha2j.schedule.data.repository.ScheduleDatabaseHelper;
import cn.alpha2j.schedule.data.repository.TaskRepository;
import cn.alpha2j.schedule.util.DateUtils;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskRepositoryImpl implements TaskRepository {

    private ScheduleDatabaseHelper mDatabaseHelper;
    private static TaskRepository taskRepository;

    public static TaskRepository getInstance() {
        if(taskRepository == null) {
            synchronized (TaskRepositoryImpl.class) {
                if(taskRepository == null) {
                    taskRepository = new TaskRepositoryImpl();
                }
            }
        }

        return taskRepository;
    }

    private TaskRepositoryImpl() {
        this.mDatabaseHelper = MyApplication.getDatabaseHelper();
    }

    @Override
    public long addTask(Task task) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", task.getTitle());
        contentValues.put("description", task.getDescription());

        //因为数据库存的是时间的秒数, 所以存入数据库时要进行转化
        long secondsForDate = DateUtils.generateSecondsForDate(task.getDate());
        if(secondsForDate != -1) {
            contentValues.put("date", secondsForDate);
        }

        contentValues.put("isAlarm", task.isAlarm());

        long secondsForAlarmDateTime = DateUtils.generateSecondsForDate(task.getAlarmDateTime());
        if(secondsForAlarmDateTime != -1) {
            contentValues.put("alarmDateTime", secondsForAlarmDateTime);
        }

        contentValues.put("isDone", task.isDone());

        long id = database.insert(Constants.TABLE_NAME_TASK, null, contentValues);

        return id;
    }

    @Override
    public List<Task> findAllByDate(Date date) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        long secondsForDate = DateUtils.generateSecondsForDate(date);

        Cursor cursor = database.query(Constants.TABLE_NAME_TASK, null, "date = ?", new String[] {String.valueOf(secondsForDate)}, null, null, null);
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

            //设置是否已经完成
            boolean isDone = (cursor.getInt(cursor.getColumnIndex("isDone"))) == 1;
            task.setDone(isDone);

            taskList.add(task);
        }

        cursor.close();

        return taskList;
    }

    @Override
    public List<Task> findAllUnfinishedByDate(Date date) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        long secondsForDate = DateUtils.generateSecondsForDate(date);

        Cursor cursor = database.query(Constants.TABLE_NAME_TASK, null, "date = ? AND isDone = ?", new String[] {String.valueOf(secondsForDate), String.valueOf(0)}, null, null, null);
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

            //设置是否已经完成
            boolean isDone = (cursor.getInt(cursor.getColumnIndex("isDone"))) == 1;
            task.setDone(isDone);

            taskList.add(task);
        }

        cursor.close();

        return taskList;
    }

    @Override
    public List<Task> findAllFinishedByDate(Date date) {
        if(date == null) {
            throw new NullPointerException("参数Date不能为null");
        }
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        long secondsForDate = DateUtils.generateSecondsForDate(date);

        Cursor cursor = database.query(Constants.TABLE_NAME_TASK, null, "date = ? AND isDone = ?", new String[] {String.valueOf(secondsForDate), String.valueOf(1)}, null, null, null);
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

            //设置是否已经完成
            boolean isDone = (cursor.getInt(cursor.getColumnIndex("isDone"))) == 1;
            task.setDone(isDone);

            taskList.add(task);
        }

        cursor.close();

        return taskList;
    }

    @Override
    public boolean updateIsDone(int id, boolean isDone) {
        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("isDone", isDone);
        int updatedRow = database.update(Constants.TABLE_NAME_TASK, contentValues, "id = ?", new String[] {String.valueOf(id)});

        return updatedRow != 0;
    }


}
