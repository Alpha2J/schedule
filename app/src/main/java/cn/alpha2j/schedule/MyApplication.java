package cn.alpha2j.schedule;

import android.app.Application;
import android.content.Context;

/**
 * Created by alpha on 2017/10/26.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //如果使用LitePal数据库, 这里还要设置p461
    }

    public static Context getContext() {
        return context;
    }
}
