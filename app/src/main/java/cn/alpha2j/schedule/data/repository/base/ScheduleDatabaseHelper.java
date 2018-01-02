package cn.alpha2j.schedule.data.repository.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 换用GreenDao, 不再使用该类
 * @author alpha
 * Created on 2017/11/4.
 */
@Deprecated
public class ScheduleDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TASK = "CREATE TABLE Schedule_Task (" +
            "id integer primary key autoincrement, " +
            "title text, " +
            "description text, " +
            "taskDate integer, " +
            "alarm integer, " +
            "taskAlarmDateTime integer, " +
            "done integer)";

    private Context mContext;

    public ScheduleDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Schedule_Task");
        onCreate(db);
    }
}
