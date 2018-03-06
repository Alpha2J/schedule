package cn.alpha2j.schedule.app.ui.activity.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionRemoveItem;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.data.provider.RVTaskDataProvider;

/**
 * 可左右滑动的RecyclerView Adapter
 * @author alpha
 */
public abstract class SwipeableTaskRVAdapter extends RecyclerView.Adapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder>
        implements SwipeableItemAdapter<SwipeableTaskRVAdapter.SwipeableTaskItemViewHolder> {

    private Context mContext;

    private RVTaskDataProvider mRVTaskDataProvider;

    /**
     * 在item上完成了各种事件后会回调该接口中的相应方法
     */
    private EventListener mEventListener;
    /**
     * 包裹着item的container的点击事件
     */
    private View.OnClickListener mOnItemClickListener;
    /**
     * 在包裹着item的container的下面层的button的点击事件
     */
    private View.OnClickListener mOnDeleteButtonClickListener;

    public SwipeableTaskRVAdapter(Context context, RVTaskDataProvider rvTaskDataProvider) {
        mContext = context;

        mRVTaskDataProvider = rvTaskDataProvider;

        mOnItemClickListener = view -> {
            onItemClick(view);
        };

        mOnDeleteButtonClickListener = view -> {
            onDeleteButtonClick(view);
        };

        setHasStableIds(true);
    }

    private void onItemClick(View view) {

        if(mEventListener != null) {
            mEventListener.onItemViewClicked(view, EventListener.TASK_ITEM_CLICK_EVENT);
        }
    }

    private void onDeleteButtonClick(View view) {

        if(mEventListener != null) {
            mEventListener.onItemViewClicked(view, EventListener.DELETE_BUTTON_CLICK_EVENT);
        }
    }

    @Override
    public long getItemId(int position) {
        return mRVTaskDataProvider.getItem(position).getId();
    }

    /**
     * SwipeableItemConstants接口的缩写(变短接口名字长度)
     */
    private interface Swipeable extends SwipeableItemConstants {}

    public interface EventListener {

        int TASK_ITEM_CLICK_EVENT = 0;

        int DELETE_BUTTON_CLICK_EVENT = 1;

        /**
         * 在item移除后会立即调用
         * @param position
         */
        void onItemRemoved(int position);

        /**
         * 在item固定后会立即调用
         * @param position
         */
        void onItemPinned(int position);

        /**
         * 在item被的点击后会立即调用
         * @param view
         * @param target
         */
        void onItemViewClicked(View view, int target);
    }

    public EventListener getEventListener() {
        return mEventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public View.OnClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public View.OnClickListener getOnDeleteButtonClickListener() {
        return mOnDeleteButtonClickListener;
    }

    public void setOnDeleteButtonClickListener(View.OnClickListener onDeleteButtonClickListener) {
        this.mOnDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    public RVTaskDataProvider getRVTaskDataProvider() {
        return mRVTaskDataProvider;
    }

    public void setRVTaskDataProvider(RVTaskDataProvider RVTaskDataProvider) {
        mRVTaskDataProvider = RVTaskDataProvider;
    }

    @Override
    public SwipeableTaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_task_item, parent, false);
        SwipeableTaskItemViewHolder viewHolder = new SwipeableTaskItemViewHolder(view);

        viewHolder.mContainer.setOnClickListener(mOnItemClickListener);
        viewHolder.mDeleteButton.setOnClickListener(mOnDeleteButtonClickListener);

        GradientDrawable gradientDrawable = (GradientDrawable) viewHolder.mRoundShape.getBackground();
        gradientDrawable.setColor(ContextCompat.getColor(parent.getContext(), getItemRoundShapeColor()));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SwipeableTaskItemViewHolder holder, int position) {

        final RVTaskDataProvider.RVTaskData item = mRVTaskDataProvider.getItem(position);

        holder.mTitle.setText(item.getText());
        holder.mTime.setText(mContext.getResources().getString(R.string.view_holder_item_time, item.getTask().getTime().getHourOfDay(), item.getTask().getTime().getMinuteOfHour()));

        if(item.getTask().isRemind()) {
            holder.mAlarmIcon.setImageResource(R.drawable.ic_alarm);
            holder.mAlarmTime.setText(mContext.getResources().getString(R.string.view_holder_item_alarm_time, item.getTask().getRemindTime().getHourOfDay(), item.getTask().getRemindTime().getMinuteOfHour()));
        } else {
            holder.mAlarmIcon.setImageResource(R.drawable.ic_alarm_off);
            holder.mAlarmTime.setText("");
        }
        final int swipeState = holder.getSwipeStateFlags();

        if ((swipeState & Swipeable.STATE_FLAG_IS_UPDATED) != 0) {
            int bgResId;

            if ((swipeState & Swipeable.STATE_FLAG_IS_ACTIVE) != 0) {
                bgResId = R.drawable.bg_item_swiping_active_state;
            } else if ((swipeState & Swipeable.STATE_FLAG_SWIPING) != 0) {
                bgResId = R.drawable.bg_item_swiping_state;
            } else {
                bgResId = R.drawable.bg_item_normal_state;
            }

            holder.mContainer.setBackgroundResource(bgResId);
        }

        //设置向左滑动的最大距离
        float density = holder.itemView.getResources().getDisplayMetrics().density;
        float pinnedDistance = density * 100;
        holder.setProportionalSwipeAmountModeEnabled(false);
        holder.setMaxLeftSwipeAmount(-pinnedDistance);

        holder.setSwipeItemHorizontalSlideAmount(item.isPinned() ? -pinnedDistance : 0);
    }

    @Override
    public int getItemCount() {

        return mRVTaskDataProvider.getCount();
    }

    @Override
    public int onGetSwipeReactionType(SwipeableTaskItemViewHolder holder, int position, int x, int y) {

        return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public void onSwipeItemStarted(SwipeableTaskItemViewHolder holder, int position) {

        notifyDataSetChanged();
    }

    @Override
    public void onSetSwipeBackground(SwipeableTaskItemViewHolder holder, int position, int type) {

        switch (type) {
            case Swipeable.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND :
                holder.mBehindView.setVisibility(View.GONE);
                holder.itemView.setBackgroundResource(R.drawable.recycler_view_item_swipe_neutral_bg);
                break;
            case Swipeable.DRAWABLE_SWIPE_LEFT_BACKGROUND :
                holder.mBehindView.setVisibility(View.VISIBLE);
                break;
            case Swipeable.DRAWABLE_SWIPE_RIGHT_BACKGROUND :
                holder.mBehindView.setVisibility(View.GONE);
                holder.itemView.setBackgroundResource(getItemSwipeRightBackground());
                break;
            default:
        }
    }

    @Override
    public SwipeResultAction onSwipeItem(SwipeableTaskItemViewHolder holder, int position, int result) {

        switch (result) {
            case Swipeable.RESULT_SWIPED_LEFT :
                return new SwipeLeftResultAction(this, position);
            case Swipeable.RESULT_SWIPED_RIGHT :
                if(mRVTaskDataProvider.getItem(position).isPinned()) {
                    return new UnPinResultAction(this, position);
                } else {
                    return new SwipeRemoveActionResult(this, position);
                }
            case Swipeable.RESULT_CANCELED :
            default:
                if(position != RecyclerView.NO_POSITION) {
                    return new UnPinResultAction(this, position);
                } else {
                    return null;
                }
        }
    }

    public abstract int getItemSwipeRightBackground();

    public abstract int getItemRoundShapeColor();

    public class SwipeableTaskItemViewHolder extends AbstractSwipeableItemViewHolder {

        private RelativeLayout mBehindView;
        private Button mDeleteButton;
        private LinearLayout mContainer;
        private RelativeLayout mRoundShape;
        private TextView mTitle;
        private TextView mTime;
        private ImageView mAlarmIcon;
        private TextView mAlarmTime;

        public SwipeableTaskItemViewHolder(View itemView) {
            super(itemView);

            mBehindView = itemView.findViewById(R.id.behind_views);
            mDeleteButton = itemView.findViewById(R.id.task_item_delete_button);
            mContainer = itemView.findViewById(R.id.container);
            mRoundShape = itemView.findViewById(R.id.round_shape);
            mTitle = itemView.findViewById(R.id.task_item_title);
            mTime = itemView.findViewById(R.id.task_item_time);
            mAlarmIcon = itemView.findViewById(R.id.task_item_alarm_icon);
            mAlarmTime = itemView.findViewById(R.id.task_item_alarm_time);
        }

        @Override
        public View getSwipeableContainerView() {

            return mContainer;
        }
    }

    /**
     * 向左移动, 显示下面的按钮
     */
    private class SwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {

        private SwipeableTaskRVAdapter mAdapter;
        private final int mPosition;
        private boolean mPinned;

        SwipeLeftResultAction(SwipeableTaskRVAdapter adapter, int position) {
            this.mAdapter = adapter;
            this.mPosition = position;
            this.mPinned = false;
        }

        @Override
        protected void onPerformAction() {

            super.onPerformAction();

            RVTaskDataProvider.RVTaskData item = mAdapter.mRVTaskDataProvider.getItem(mPosition);
            if(!item.isPinned()) {
                item.setPinned(true);
                mAdapter.notifyItemChanged(mPosition);
                mPinned = true;
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();

            if(mPinned && mAdapter.mEventListener != null) {
                mAdapter.mEventListener.onItemPinned(mPosition);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();

            mAdapter = null;
        }
    }

    /**
     * 删除该项
     */
    private class SwipeRemoveActionResult extends SwipeResultActionRemoveItem {

        private SwipeableTaskRVAdapter mAdapter;
        private final int mPosition;

        SwipeRemoveActionResult(SwipeableTaskRVAdapter adapter, int position) {
            this.mAdapter = adapter;
            this.mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            mAdapter.mRVTaskDataProvider.removeItem(mPosition);
            mAdapter.notifyItemRemoved(mPosition);
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();

            if(mAdapter.mEventListener != null) {
                mAdapter.mEventListener.onItemRemoved(mPosition);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();

            mAdapter = null;
        }
    }

    /**
     * 还原成初始状态, 没有打开显示下面的按钮
     */
    private class UnPinResultAction extends SwipeResultActionDefault {

        private SwipeableTaskRVAdapter mAdapter;
        private final int mPosition;

        UnPinResultAction(SwipeableTaskRVAdapter adapter, int position) {
            this.mAdapter = adapter;
            this.mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            RVTaskDataProvider.RVTaskData item = mAdapter.mRVTaskDataProvider.getItem(mPosition);
            if(item.isPinned()) {
                item.setPinned(false);
                mAdapter.notifyItemChanged(mPosition);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();

            mAdapter = null;
        }
    }
}
