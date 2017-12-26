package cn.alpha2j.schedule.app.util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import cn.alpha2j.schedule.data.entity.TaskEntity;

/**
 * @author alpha
 */
public class TaskAlarm {
    private LinkedList<TaskEntity> taskList;
    private ReentrantLock lock;

    public TaskAlarm(List<TaskEntity> taskList) {
        this.taskList = new LinkedList<>();
        this.lock = new ReentrantLock();

        this.taskList.addAll(taskList);
    }

    public boolean isTaskEmpty() {
        return taskList.size() == 0;
    }

    public void addTask(TaskEntity task) {
        lock.lock();

        taskList.add(task);

        lock.unlock();
    }

    public LinkedList<TaskEntity> getTaskListAndRemove() {
        LinkedList<TaskEntity> resultTaskList;

        lock.lock();
        resultTaskList = new LinkedList<>();
        resultTaskList.addAll(taskList);

        taskList.clear();
        lock.unlock();

        return resultTaskList;
    }
}











