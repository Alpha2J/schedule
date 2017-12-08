package cn.alpha2j.schedule.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.alpha2j.schedule.R;
import cn.alpha2j.schedule.data.entity.Task;

/**
 * 该类已经不再使用. 项目开始时为task列表使用的recycler adapter
 *
 * @author alpha
 * Created on 2017/11/4.
 */
@Deprecated
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> taskList;

    public TaskListAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_task_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.taskTitle.setText(taskList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle;

        public ViewHolder(View itemView) {
            super(itemView);
//            taskTitle = (TextView) itemView.findViewById(R.id.today_task_item_task_title);
        }
    }
}
