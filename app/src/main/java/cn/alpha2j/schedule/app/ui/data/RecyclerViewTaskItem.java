package cn.alpha2j.schedule.app.ui.data;

import cn.alpha2j.schedule.data.entity.TaskEntity;

/**
 * @author alpha
 */
public class RecyclerViewTaskItem {
    private TaskEntity task;
    private boolean isPinned;

    public RecyclerViewTaskItem() {

    }

    public RecyclerViewTaskItem(TaskEntity task, boolean isPinned) {
        this.task = task;
        this.isPinned = isPinned;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }
}
