package cn.alpha2j.schedule.app.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initToolbar();
        initActivity(savedInstanceState);
    }

    /**
     * 获取activity的布局文件id
     * @return 布局文件id
     */
    protected abstract int getLayoutId();

    /**
     * 调用方法初始化activity
     *
     * @param savedInstanceState
     */
    protected abstract void initActivity(@Nullable Bundle savedInstanceState);

    private void initToolbar() {

        mToolbar = findViewById(R.id.tb_app_tool_bar);
//        初始化toolbar
        mToolbar.setTitle(getToolbarTitle());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 获取toolbar的标题
     * @return toolbar的标题
     */
    protected abstract String getToolbarTitle();
}
