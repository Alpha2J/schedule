package cn.alpha2j.schedule.app.ui.data;

import cn.alpha2j.schedule.data.Task;

/**
 * @author alpha
 */
@Deprecated
public class RecyclerViewTaskItem {
    private Task task;
    private boolean isPinned;

    public RecyclerViewTaskItem() {

    }

    public RecyclerViewTaskItem(Task task, boolean isPinned) {
        this.task = task;
        this.isPinned = isPinned;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }
}
