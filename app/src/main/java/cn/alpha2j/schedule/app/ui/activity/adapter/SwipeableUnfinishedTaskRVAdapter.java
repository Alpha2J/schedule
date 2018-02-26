package cn.alpha2j.schedule.app.ui.activity.adapter;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;

/**
 * @author alpha
 *         Created on 2018/1/22.
 */
public class SwipeableUnfinishedTaskRVAdapter extends SwipeableTaskRVAdapter {

    public SwipeableUnfinishedTaskRVAdapter(DataProvider dataProvider) {
        super(dataProvider);
    }

    @Override
    public int getItemSwipeRightBackground() {
        return R.drawable.recycler_view_item_swipe_to_finish_bg;
    }

    @Override
    public int getItemRoundShapeColor() {
        return R.color.colorTaskUnfinished;
    }
}
