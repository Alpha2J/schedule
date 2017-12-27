package cn.alpha2j.schedule.data.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.repository.TaskRepository;
import cn.alpha2j.schedule.data.repository.impl.TaskRepositoryImpl;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.exception.AlarmDateTimeCanNotBeNullException;
import cn.alpha2j.schedule.time.ScheduleDateTime;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleDateBuilder;
import cn.alpha2j.schedule.time.builder.impl.DefaultScheduleTimeBuilder;

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
            throw new NullPointerException("task 不能为null");
        }

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setAlarm(task.isAlarm());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setDone(task.isDone());
        taskEntity.setTitle(task.getTitle());

        //对任务的时间进行处理, 确保任务的时间是当天零点
        ScheduleDateTime taskScheduleDateTime = task.getTaskDate();
        if (taskScheduleDateTime == null) {
            taskScheduleDateTime = DefaultScheduleDateBuilder.now().toDateBegin().getResult();
        } else {
            taskScheduleDateTime = DefaultScheduleDateBuilder.of(taskScheduleDateTime).toDateBegin().getResult();
        }
        long taskDate = taskScheduleDateTime.getEpochMillisecond();
        taskEntity.setTaskDate(taskDate);

        //对任务的提醒时间进行处理, 确保提醒时间精确到分钟
        if (task.isAlarm() == true) {
            ScheduleDateTime alarmScheduleDateTime = task.getTaskAlarmDateTime();
            if (alarmScheduleDateTime == null) {
                throw new AlarmDateTimeCanNotBeNullException("如果想要进行提醒, 那么需要设置提醒时间");
            } else {
                long taskAlarmDateTime = DefaultScheduleTimeBuilder.of(alarmScheduleDateTime).toMinuteStart().getResult().getEpochMillisecond();
                taskEntity.setTaskAlarmDateTime(taskAlarmDateTime);
            }
        }

        return taskRepository.save(taskEntity).getId();
    }

    @Override
    public List<Task> findAllForToday() {

        long todayBegin = DefaultScheduleDateBuilder.now().toDateBegin().getResult().getMillisOfSecond();

        return convert(taskRepository.findTaskEntitiesByTaskDate(todayBegin));
    }

    @Override
    public List<Task> findAllUnfinishedForToday() {

        long todayBegin = DefaultScheduleDateBuilder.now().toDateBegin().getResult().getMillisOfSecond();

        return convert(taskRepository.findTaskEntitiesByTaskDateAndDone(todayBegin, false));
    }

    @Override
    public List<Task> findAllFinishedForToday() {

        long todayBegin = DefaultScheduleDateBuilder.now().toDateBegin().getResult().getMillisOfSecond();

        return convert(taskRepository.findTaskEntitiesByTaskDateAndDone(todayBegin, true));
    }

    @Override
    public void setDone(Task task) {

        taskRepository.updateTaskEntity(task.getId(), "done", true);
    }

    @Override
    public void setUnDone(Task task) {

        taskRepository.updateTaskEntity(task.getId(),"done", false);
    }

    private List<Task> convert(List<TaskEntity> taskEntities) {

        List<Task> tasks = new ArrayList<>();
        for (TaskEntity taskEntity : taskEntities) {
            tasks.add(convert(taskEntity));
        }

        return tasks;
    }

    private Task convert(TaskEntity taskEntity) {

        Task task = new Task();
        task.setId(taskEntity.getId());
        task.setAlarm(taskEntity.isAlarm());
        task.setDone(taskEntity.isDone());
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());

        ScheduleDateTime taskAlarmDateTime = ScheduleDateTime.of(taskEntity.getTaskAlarmDateTime());
        task.setTaskAlarmDateTime(taskAlarmDateTime);

        ScheduleDateTime taskDate = ScheduleDateTime.of(taskEntity.getTaskDate());
        task.setTaskDate(taskDate);

        return task;
    }
}
