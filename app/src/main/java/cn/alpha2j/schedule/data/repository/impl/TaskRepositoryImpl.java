package cn.alpha2j.schedule.data.repository.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.repository.TaskRepository;
import cn.alpha2j.schedule.data.repository.base.SqliteGenericRepository;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class TaskRepositoryImpl extends SqliteGenericRepository<TaskEntity, Long> implements TaskRepository {

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

    @Override
    public List<TaskEntity> findTaskEntitiesByTaskDate(long taskDate) {

        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, "taskDate = ?", new String[]{String.valueOf(taskDate)}, null, null, null);

        return resolveDataFromCursor(cursor);
    }

    @Override
    public List<TaskEntity> findTaskEntitiesByTaskDateAndDone(long taskDate, boolean done) {

        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        Cursor cursor = database.query(TABLE_NAME, null, "taskDate = ? AND done = ?", new String[] {String.valueOf(taskDate), String.valueOf(done)}, null, null, null);

        return resolveDataFromCursor(cursor);
    }

    @Override
    public void updateTaskEntity(long id, String field, Object value) {

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("field", value.toString());

        database.update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    private List<TaskEntity> resolveDataFromCursor(Cursor cursor) {
        List<TaskEntity> taskEntities = new ArrayList<>();

        while (cursor.moveToNext()) {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setId(cursor.getInt(cursor.getColumnIndex("id")));
            taskEntity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            taskEntity.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            taskEntity.setTaskDate(cursor.getLong(cursor.getColumnIndex("taskDate")));

            //设置是否提醒
            boolean alarm = (cursor.getInt(cursor.getColumnIndex("alarm"))) == 1;
            taskEntity.setAlarm(alarm);
            //设置提醒时间
            taskEntity.setTaskAlarmDateTime(cursor.getLong(cursor.getColumnIndex("taskAlarmDateTime")));

            //设置是否已经完成
            boolean done = (cursor.getInt(cursor.getColumnIndex("done"))) == 1;
            taskEntity.setDone(done);

            taskEntities.add(taskEntity);
        }

        cursor.close();

        return taskEntities;
    }

}
