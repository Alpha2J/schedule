package cn.alpha2j.schedule.service.impl;

import java.util.Calendar;
import java.util.List;

import cn.alpha2j.schedule.MyApplication;
import cn.alpha2j.schedule.entity.Task;
import cn.alpha2j.schedule.repository.TaskRepository;
import cn.alpha2j.schedule.repository.impl.TaskRepositoryImpl;
import cn.alpha2j.schedule.service.TaskService;

/**
 * @author alpha
 */
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl() {
        this.taskRepository = new TaskRepositoryImpl(MyApplication.getDatabaseHelper());
    }

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public boolean addTask(Task task) {
        if(task == null) {
            return false;
        }

        long id = taskRepository.addTask(task);

        return id != -1;
    }

    @Override
    public List<Task> findAllForToday() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);

        return taskRepository.findAllByDate(today.getTime());
    }
}
