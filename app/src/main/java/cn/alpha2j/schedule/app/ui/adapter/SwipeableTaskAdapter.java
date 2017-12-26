package cn.alpha2j.schedule.app.ui.adapter;

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

import java.util.List;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.app.ui.data.RecyclerViewTaskItem;

/**
 * 可左右滑动的RecyclerView Adapter
 * @author alpha
 */
public class SwipeableTaskAdapter
        extends RecyclerView.Adapter<SwipeableTaskAdapter.SwipeableItemViewHolder>
        implements SwipeableItemAdapter<SwipeableTaskAdapter.SwipeableItemViewHolder> {

    private static final String TAG = "SwipeableTaskAdapter";

    private List<RecyclerViewTaskItem> taskItemList;

    /**
     * 在item上完成了各种事件后会回调该接口中的相应方法
     */
    private EventListener eventListener;
    /**
     * 包裹着item的container的点击事件
     */
    private View.OnClickListener taskItemOnClickListener;
    /**
     * 在包裹着item的container的下面层的button的点击事件
     */
    private View.OnClickListener deleteButtonOnClickListener;

    public SwipeableTaskAdapter(List<RecyclerViewTaskItem> taskItemList) {
        this.taskItemList = taskItemList;

        taskItemOnClickListener = view -> {
            onItemClick(view);
        };

        deleteButtonOnClickListener = view -> {
            onDeleteButtonClick(view);
        };

        setHasStableIds(true);
    }

    private void onItemClick(View view) {
        if(eventListener != null) {
            eventListener.onItemViewClicked(view, EventListener.TASK_ITEM_CLICK_EVENT);
        }
    }

    private void onDeleteButtonClick(View view) {
        if(eventListener != null) {
            eventListener.onItemViewClicked(view, EventListener.DELETE_BUTTON_CLICK_EVENT);
        }
    }

    @Override
    public long getItemId(int position) {
        return taskItemList.get(position).getTask().getId();
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
        void onItemRemoved(int position, RecyclerViewTaskItem taskItem);

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

    public List<RecyclerViewTaskItem> getTaskItemList() {
        return taskItemList;
    }

    public void setTaskItemList(List<RecyclerViewTaskItem> taskItemList) {
        this.taskItemList = taskItemList;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public View.OnClickListener getTaskItemOnClickListener() {
        return taskItemOnClickListener;
    }

    public void setTaskItemOnClickListener(View.OnClickListener taskItemOnClickListener) {
        this.taskItemOnClickListener = taskItemOnClickListener;
    }

    public View.OnClickListener getDeleteButtonOnClickListener() {
        return deleteButtonOnClickListener;
    }

    public void setDeleteButtonOnClickListener(View.OnClickListener deleteButtonOnClickListener) {
        this.deleteButtonOnClickListener = deleteButtonOnClickListener;
    }

    //-----
    @Override
    public SwipeableItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_task_item, parent, false);
        SwipeableItemViewHolder viewHolder = new SwipeableItemViewHolder(view);

        viewHolder.container.setOnClickListener(taskItemOnClickListener);
        viewHolder.deleteButton.setOnClickListener(deleteButtonOnClickListener);

        return new SwipeableItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SwipeableItemViewHolder holder, int position) {
        final RecyclerViewTaskItem item = taskItemList.get(position);

        holder.textView.setText(item.getTask().getTitle());

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

            holder.container.setBackgroundResource(bgResId);
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
        return taskItemList.size();
    }

    //---

    @Override
    public int onGetSwipeReactionType(SwipeableItemViewHolder holder, int position, int x, int y) {
        return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public void onSetSwipeBackground(SwipeableItemViewHolder holder, int position, int type) {

        switch (type) {
            case Swipeable.DRAWABLE_SWIPE_NEUTRAL_BACKGROUND :
                holder.behindView.setVisibility(View.GONE);
                holder.itemView.setBackgroundResource(R.drawable.recycler_view_item_swipe_neutral_bg);
                break;
            case Swipeable.DRAWABLE_SWIPE_LEFT_BACKGROUND :
                holder.behindView.setVisibility(View.VISIBLE);
                break;
            case Swipeable.DRAWABLE_SWIPE_RIGHT_BACKGROUND :
                holder.behindView.setVisibility(View.GONE);
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
                if(taskItemList.get(position).isPinned()) {
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

    //---

    public static class SwipeableItemViewHolder extends AbstractSwipeableItemViewHolder {

        private FrameLayout container;
        private RelativeLayout behindView;
        private TextView textView;
        private Button deleteButton;

        public SwipeableItemViewHolder(View itemView) {
            super(itemView);

            container = (FrameLayout) itemView.findViewById(R.id.container);
            behindView = (RelativeLayout) itemView.findViewById(R.id.behind_views);
            textView = (TextView) itemView.findViewById(R.id.task_item_text);
            deleteButton = (Button) itemView.findViewById(R.id.task_item_delete_button);
        }

        @Override
        public View getSwipeableContainerView() {
            return container;
        }
    }

    /**
     * 向左移动, 显示下面的按钮
     */
    private static class SwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {
        private SwipeableTaskAdapter adapter;
        private final int position;
        private boolean setPinned;

        SwipeLeftResultAction(SwipeableTaskAdapter adapter, int position) {
            this.adapter = adapter;
            this.position = position;
            setPinned = false;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            RecyclerViewTaskItem item = adapter.taskItemList.get(position);
            if(!item.isPinned()) {
                item.setPinned(true);
                adapter.notifyItemChanged(position);
                setPinned = true;
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();

            if(setPinned && adapter.eventListener != null) {
                adapter.eventListener.onItemPinned(position);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();

            adapter = null;
        }
    }

    /**
     * 删除该项
     */
    private static class SwipeRemoveActionResult extends SwipeResultActionRemoveItem {
        private SwipeableTaskAdapter adapter;
        private final int position;
        private RecyclerViewTaskItem taskItem;

        SwipeRemoveActionResult(SwipeableTaskAdapter adapter, int position) {
            this.adapter = adapter;
            this.position = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            taskItem = adapter.taskItemList.remove(position);
            adapter.notifyItemRemoved(position);
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();

            if(adapter.eventListener != null) {
                adapter.eventListener.onItemRemoved(position, taskItem);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();

            adapter = null;
        }
    }

    /**
     * 还原成初始状态, 没有打开显示下面的按钮
     */
    private static class UnPinResultAction extends SwipeResultActionDefault {
        private SwipeableTaskAdapter adapter;
        private final int position;

        UnPinResultAction(SwipeableTaskAdapter adapter, int position) {
            this.adapter = adapter;
            this.position = position;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            RecyclerViewTaskItem item = adapter.taskItemList.get(position);
            if(item.isPinned()) {
                item.setPinned(false);
                adapter.notifyItemChanged(position);
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();

            adapter = null;
        }
    }
}
