package cn.alpha2j.schedule.app.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import cn.alpha2j.schedule.data.entity.Task;

/**
 * @author alpha
 */
public class TaskAlarm {
    private LinkedList<Task> taskList;
    private ReentrantLock lock;

    public TaskAlarm(List<Task> taskList) {
        this.taskList = new LinkedList<>();
        this.lock = new ReentrantLock();

        this.taskList.addAll(taskList);
    }

    public boolean isTaskEmpty() {
        return taskList.size() == 0;
    }

    public void addTask(Task task) {
        lock.lock();

        taskList.add(task);

        lock.unlock();
    }

    public LinkedList<Task> getTaskListAndRemove() {
        LinkedList<Task> resultTaskList;

        lock.lock();
        resultTaskList = new LinkedList<>();
        resultTaskList.addAll(taskList);

        taskList.clear();
        lock.unlock();

        return resultTaskList;
    }
}











