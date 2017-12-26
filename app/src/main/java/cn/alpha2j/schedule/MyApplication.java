package cn.alpha2j.schedule;

import android.app.Application;
import android.content.Context;

import cn.alpha2j.schedule.data.repository.base.ScheduleDatabaseHelper;

/**
 * @author alpha
 * Created on 2017/11/4.
 */
public class MyApplication extends Application {
    private static Context sContext;
    private static ScheduleDatabaseHelper sDatabaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        //如果使用LitePal数据库, 这里还要设置p461
    }

    public static Context getContext() {
        return sContext;
    }

    public static ScheduleDatabaseHelper getDatabaseHelper() {
        if(sDatabaseHelper == null) {
            synchronized (MyApplication.class) {
                if(sDatabaseHelper == null) {
                    sDatabaseHelper = new ScheduleDatabaseHelper(getContext(), Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
                }
            }
        }

        return sDatabaseHelper;
    }
}
