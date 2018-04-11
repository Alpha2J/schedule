package cn.alpha2j.schedule.app.ui.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.dialog.YearAndMonthPickerDialog;
import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.data.service.impl.TaskServiceImpl;
import cn.alpha2j.schedule.time.ScheduleDateTime;

public class TaskOverviewActivity extends BaseActivity {

    private LinearLayout mDatePickerWrapper;

    private TextView mDateShow;

    private RecyclerView mRecyclerView;

    private YearAndMonthPickerDialog.OnYearAndMonthSetListener mOnYearAndMonthSetListener;

    private TaskService mTaskService;

    private int mCurrentYear;

    private int mCurrentMonth;

    private MyAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_overview;
    }

    @Override
    protected void initActivity(@Nullable Bundle savedInstanceState) {

        mDatePickerWrapper = findViewById(R.id.ll_ov_date_picker_wrapper);
        mDateShow = findViewById(R.id.tv_ov_date_show);
        mRecyclerView = findViewById(R.id.rv_ov_main_content);
        mOnYearAndMonthSetListener = (year, month) -> {
            mCurrentYear = year;
            mCurrentMonth = month;
            mDateShow.setText(getResources().getString(R.string.year_month, mCurrentYear, mCurrentMonth));
            refreshView();
        };

        mDatePickerWrapper.setOnClickListener((view -> {
            YearAndMonthPickerDialog yearAndMonthPickerDialog = new YearAndMonthPickerDialog();
            yearAndMonthPickerDialog.setOnYearAndMonthSetListener(mOnYearAndMonthSetListener);
            yearAndMonthPickerDialog.show(getSupportFragmentManager(), "OverviewYearAndMonthPD");
        }));

//        用当前月份初始化显示
        mTaskService = TaskServiceImpl.getInstance();
        ScheduleDateTime scheduleDateTime = ScheduleDateTime.now();
        mCurrentYear = scheduleDateTime.getYear();
        mCurrentMonth = scheduleDateTime.getMonthOfYear();
        mDateShow.setText(getResources().getString(R.string.year_month, mCurrentYear, mCurrentMonth));

        initRecyclerView();
    }

    @Override
    protected String getToolbarTitle() {
        return "";
    }

    private void initRecyclerView() {

        RecyclerViewExpandableItemManager expMgr = new RecyclerViewExpandableItemManager(null);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mCurrentYear, mCurrentMonth, getData());
        mRecyclerView.setAdapter(expMgr.createWrappedAdapter(mAdapter));

        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(this, R.drawable.list_divider_h), true));
        expMgr.expandAll();
        expMgr.attachRecyclerView(mRecyclerView);
    }

    private List<MyGroupItem> getData() {

        List<MyGroupItem> groupItems = new ArrayList<>();
        Map<Integer, List<Task>> map = mTaskService.findAndMap(mCurrentYear, mCurrentMonth);
        for (Map.Entry<Integer, List<Task>> entry : map.entrySet()){
            List<MyChildItem> children = new ArrayList<>();
            for (Task task : entry.getValue()) {
                MyChildItem myChildItem = new MyChildItem(task);
                children.add(myChildItem);
            }
            MyGroupItem myGroupItem = new MyGroupItem(entry.getKey(), children);
            groupItems.add(myGroupItem);
        }

        return groupItems;
    }

    private void refreshView() {

        List<MyGroupItem> groupItems = getData();
        mAdapter.setYear(mCurrentYear);
        mAdapter.setMonth(mCurrentMonth);
        mAdapter.getMyGroupItems().clear();
        mAdapter.getMyGroupItems().addAll(groupItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home :
                finish();
                return true;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    static class MyGroupItem {
        public final long id;
        public final List<MyChildItem> mChildItems;

        public MyGroupItem(long id, List<MyChildItem> childItems) {
            this.id = id;
            mChildItems = childItems;
        }
    }

    static class MyChildItem {
        public final long id;
        public final Task mTask;

        public MyChildItem(Task task) {
            this.id = task.getId().intValue();
            this.mTask = task;
        }
    }

    static class MyGroupViewHolder extends AbstractExpandableItemViewHolder {

        public TextView mTitle;

        public MyGroupViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.recycler_view_section_header_text);
        }
    }

    static class MyChildViewHolder extends AbstractExpandableItemViewHolder {

        private TextView mTitle;

        private TextView mTime;

        public MyChildViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.tv_ov_item_title);
            mTime = itemView.findViewById(R.id.tv_ov_item_time);
        }
    }

    static class MyAdapter extends AbstractExpandableItemAdapter<MyGroupViewHolder, MyChildViewHolder> {

        List<MyGroupItem> mMyGroupItems;

        int mYear;

        int mMonth;

        public MyAdapter(int year, int month, List<MyGroupItem> myGroupItems) {
            setHasStableIds(true);

            this.mYear = year;
            this.mMonth = month;
            mMyGroupItems = myGroupItems;
        }

        public List<MyGroupItem> getMyGroupItems() {
            return mMyGroupItems;
        }

        public void setMyGroupItems(List<MyGroupItem> myGroupItems) {
            mMyGroupItems = myGroupItems;
        }

        public int getYear() {
            return mYear;
        }

        public void setYear(int year) {
            mYear = year;
        }

        public int getMonth() {
            return mMonth;
        }

        public void setMonth(int month) {
            mMonth = month;
        }

        @Override
        public int getGroupCount() {
            return mMyGroupItems.size();
        }

        @Override
        public int getChildCount(int groupPosition) {
            return mMyGroupItems.get(groupPosition).mChildItems.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return mMyGroupItems.get(groupPosition).id;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return mMyGroupItems.get(groupPosition).mChildItems.get(childPosition).id;
        }

        @Override
        public MyGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_section_header, parent, false);
            return new MyGroupViewHolder(v);
        }

        @Override
        public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_task_item, parent, false);
            return new MyChildViewHolder(v);
        }

        @Override
        public void onBindGroupViewHolder(MyGroupViewHolder holder, int groupPosition, int viewType) {
            MyGroupItem group = mMyGroupItems.get(groupPosition);
            String str = mYear + "年" + mMonth + "月" + group.id + "日";
            holder.mTitle.setText(str);
        }

        @Override
        public void onBindChildViewHolder(MyChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
            MyChildItem child = mMyGroupItems.get(groupPosition).mChildItems.get(childPosition);
            holder.mTitle.setText(child.mTask.getTitle());
            String timeStr = child.mTask.getTime().getHourOfDay() + ":" + child.mTask.getTime().getMinuteOfHour();
            holder.mTime.setText(timeStr);
        }

        @Override
        public boolean onCheckCanExpandOrCollapseGroup(MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
            return true;
        }
    }
}
