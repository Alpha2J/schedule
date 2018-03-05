package cn.alpha2j.schedule.app.ui.activity.adapter;

import android.content.Context;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * @author alpha
 *         Created on 2018/1/22.
 */
public class SwipeableFinishedTaskRVAdapter extends SwipeableTaskRVAdapter {

    public SwipeableFinishedTaskRVAdapter(Context context, RVTaskDataProvider dataProvider) {
        super(context, dataProvider);
    }

    @Override
    public int getItemSwipeRightBackground() {
        return R.drawable.recycler_view_item_swipe_to_unfinish_bg;
    }

    @Override
    public int getItemRoundShapeColor() {
        return R.color.colorTaskFinished;
    }
}
