package cn.alpha2j.schedule.service.impl;

import java.util.Date;
import java.util.List;

import cn.alpha2j.schedule.entity.Task;
import cn.alpha2j.schedule.repository.TaskRepository;
import cn.alpha2j.schedule.repository.impl.TaskRepositoryImpl;
import cn.alpha2j.schedule.service.TaskService;
import cn.alpha2j.schedule.util.DateUtils;

/**
 * @author alpha
 */
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private static TaskService taskService;

    private TaskServiceImpl() {
        taskRepository = TaskRepositoryImpl.getInstance();
    }

    public static TaskService getInstance() {
        if(taskService == null) {
            synchronized (TaskServiceImpl.class) {
                if(taskService == null) {
                    taskService = new TaskServiceImpl();
                }
            }
        }

        return taskService;
    }

    @Override
    public long addTask(Task task) {
        if(task == null) {
            return -1;
        }

        //需要对task对象的date字段做处理, 如果存在date字段, 那么date应该指向当天 00:00:00
        Date date = task.getDate();
        if(date != null) {
            date = DateUtils.transformDateToDateBegin(date);
            task.setDate(date);
        }

        long id = taskRepository.addTask(task);

        return id;
    }

    @Override
    public List<Task> findAllForToday() {
        Date today = DateUtils.generateDateBeginForToday();

        return taskRepository.findAllByDate(today);
    }

    @Override
    public List<Task> findAllUnfinishedForToday() {
        Date today = DateUtils.generateDateBeginForToday();

        return taskRepository.findAllUnfinishedByDate(today);
    }

    @Override
    public List<Task> findAllFinishedForToday() {
        Date today = DateUtils.generateDateBeginForToday();

        return taskRepository.findAllFinishedByDate(today);
    }

    @Override
    public void setDone(Task task) {
        taskRepository.updateIsDone(task.getId(), true);
    }

    @Override
    public void setUnDone(Task task) {
        taskRepository.updateIsDone(task.getId(), false);
    }
}
