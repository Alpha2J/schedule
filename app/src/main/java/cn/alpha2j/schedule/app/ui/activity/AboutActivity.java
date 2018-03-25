package cn.alpha2j.schedule.app.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

        FloatingActionButton shareButton = findViewById(R.id.fab_about_share);
        shareButton.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "欢迎分享...");
            intent.setType("text/plain");
            startActivity(intent);
        });
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
}
