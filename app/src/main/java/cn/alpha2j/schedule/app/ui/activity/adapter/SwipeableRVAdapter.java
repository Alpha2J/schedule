package cn.alpha2j.schedule.app.ui.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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
import cn.alpha2j.schedule.app.ui.data.provider.DataProvider;

/**
 * 可左右滑动的RecyclerView Adapter
 * @author alpha
 */
public class SwipeableRVAdapter
        extends RecyclerView.Adapter<SwipeableRVAdapter.SwipeableItemViewHolder>
        implements SwipeableItemAdapter<SwipeableRVAdapter.SwipeableItemViewHolder> {

    private static final String TAG = "SwipeableRVAdapter";

    private DataProvider mDataProvider;

    /**
     * 在item上完成了各种事件后会回调该接口中的相应方法
     */
    private EventListener mEventListener;
    /**
     * 包裹着item的container的点击事件
     */
    private View.OnClickListener mItemOnClickListener;
    /**
     * 在包裹着item的container的下面层的button的点击事件
     */
    private View.OnClickListener mDeleteButtonOnClickListener;

    public SwipeableRVAdapter(DataProvider dataProvider) {

        this.mDataProvider = dataProvider;

        mItemOnClickListener = view -> {
            onItemClick(view);
        };

        mDeleteButtonOnClickListener = view -> {
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

        return mDataProvider.getItem(position).getId();
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

    public DataProvider getDataProvider() {
        return mDataProvider;
    }

    public void setDataProvider(DataProvider dataProvider) {
        mDataProvider = dataProvider;
    }

    public EventListener getEventListener() {
        return mEventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public View.OnClickListener getItemOnClickListener() {
        return mItemOnClickListener;
    }

    public void setItemOnClickListener(View.OnClickListener itemOnClickListener) {
        this.mItemOnClickListener = itemOnClickListener;
    }

    public View.OnClickListener getDeleteButtonOnClickListener() {
        return mDeleteButtonOnClickListener;
    }

    public void setDeleteButtonOnClickListener(View.OnClickListener deleteButtonOnClickListener) {
        this.mDeleteButtonOnClickListener = deleteButtonOnClickListener;
    }

    @Override
    public SwipeableItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_task_item, parent, false);
        SwipeableItemViewHolder viewHolder = new SwipeableItemViewHolder(view);

        viewHolder.mContainer.setOnClickListener(mItemOnClickListener);
        viewHolder.mDeleteButton.setOnClickListener(mDeleteButtonOnClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SwipeableItemViewHolder holder, int position) {

        final DataProvider.Data item = mDataProvider.getItem(position);

        holder.mTextView.setText(item.getText());

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

        return mDataProvider.getCount();
    }

    @Override
    public int onGetSwipeReactionType(SwipeableItemViewHolder holder, int position, int x, int y) {

        return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public void onSwipeItemStarted(SwipeableItemViewHolder holder, int position) {
        notifyDataSetChanged();
    }

    @Override
    public void onSetSwipeBackground(SwipeableItemViewHolder holder, int position, int type) {

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
                holder.itemView.setBackgroundResource(R.drawable.recycler_view_item_swipe_right_bg);
                break;
            default:
        }
    }

    @Override
    public SwipeResultAction onSwipeItem(SwipeableItemViewHolder holder, int position, int result) {

        switch (result) {
            case Swipeable.RESULT_SWIPED_LEFT :
                return new SwipeLeftResultAction(this, position);
            case Swipeable.RESULT_SWIPED_RIGHT :
                if(mDataProvider.getItem(position).isPinned()) {
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

    public static class SwipeableItemViewHolder extends AbstractSwipeableItemViewHolder {

        private FrameLayout mContainer;
        private RelativeLayout mBehindView;
        private TextView mTextView;
        private Button mDeleteButton;

        public SwipeableItemViewHolder(View itemView) {
            super(itemView);

            mContainer = itemView.findViewById(R.id.container);
            mBehindView = itemView.findViewById(R.id.behind_views);
            mTextView = itemView.findViewById(R.id.task_item_text);
            mDeleteButton = itemView.findViewById(R.id.task_item_delete_button);
        }

        @Override
        public View getSwipeableContainerView() {

            return mContainer;
        }
    }

    /**
     * 向左移动, 显示下面的按钮
     */
    private static class SwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {

        private SwipeableRVAdapter mAdapter;
        private final int mPosition;
        private boolean mPinned;

        SwipeLeftResultAction(SwipeableRVAdapter adapter, int position) {
            this.mAdapter = adapter;
            this.mPosition = position;
            this.mPinned = false;
        }

        @Override
        protected void onPerformAction() {

            super.onPerformAction();

            DataProvider.Data item = mAdapter.mDataProvider.getItem(mPosition);
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
    private static class SwipeRemoveActionResult extends SwipeResultActionRemoveItem {

        private SwipeableRVAdapter mAdapter;
        private final int mPosition;

        SwipeRemoveActionResult(SwipeableRVAdapter adapter, int position) {
            this.mAdapter = adapter;
            this.mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            mAdapter.mDataProvider.removeItem(mPosition);
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
    private static class UnPinResultAction extends SwipeResultActionDefault {

        private SwipeableRVAdapter mAdapter;
        private final int mPosition;

        UnPinResultAction(SwipeableRVAdapter adapter, int position) {
            this.mAdapter = adapter;
            this.mPosition = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            DataProvider.Data item = mAdapter.mDataProvider.getItem(mPosition);
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
