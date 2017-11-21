package cn.alpha2j.schedule.app.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class ScheduleDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_TASK = "CREATE TABLE Schedule_Task (" +
            "id integer primary key autoincrement, " +
            "title text, " +
            "description text, " +
            "date integer, " +
            "isAlarm integer, " +
            "alarmDateTime integer, " +
            "isDone integer)";

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
