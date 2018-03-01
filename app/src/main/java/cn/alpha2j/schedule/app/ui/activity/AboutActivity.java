package cn.alpha2j.schedule.app.ui.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.TextView;

import cn.alpha2j.schedule.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initActivity(@Nullable Bundle savedInstanceState) {
        TextView version = findViewById(R.id.tv_about_version);
        version.setText(getVersionName());
    }

    @Override
    protected String getToolbarTitle() {
        return "关于";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                return true;
            default:
        }

        return true;
    }

    private String getVersionName() {

        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return getResources().getString(R.string.task_about_version_name, version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
//    public String getVersionName() {
//        try {
//            PackageManager manager = this.getPackageManager();
//            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
//            String version = info.versionName;
//            return getString(R.string.about_version) + " " + version;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
}
