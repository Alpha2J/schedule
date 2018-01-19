package cn.alpha2j.schedule.data.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.repository.TaskRepository;
import cn.alpha2j.schedule.data.repository.impl.TaskRepositoryImpl;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.exception.AlarmDateTimeCanNotBeNullException;
import cn.alpha2j.schedule.exception.PrimaryKeyNotExistException;
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

    /**
     * 增加一个新的Task, 就算这个task设置了id也不会更新该id的task, 会直接忽略id
     *
     * @param task 需要增加的task, 不能为null
     * @return
     */
    @Override
    public long addTask(Task task) {

        if (task == null) {
            throw new NullPointerException("task不能为空");
        }

        return taskRepository.save(convertToTaskEntity(task));
    }

    /**
     * 新增或者更新一个task, 如果设置了id, 且该id的数据存在, 那么更新该条数据.
     * 如果该id的数据不存在, 那么会插入一条该id的数据.
     *
     * 如果id数据不存在, 那么直接新增一条数据, id为自增
     *
     * @param task 不能为null
     * @return
     */
    @Override
    public long addOrUpdateTask(Task task) {

        if (task == null) {
            throw new NullPointerException("task不能为空");
        }

//        如果id为空, 那么说明数据从来未插入过数据库, 是新的数据, 或者是自己设置的id
        Long id = task.getId();
        if(id == null) {
            return addTask(task);
        } else {
            TaskEntity taskEntity = convertToTaskEntity(task);
            taskEntity.setId(id);
            return taskRepository.saveOrUpdate(taskEntity);
        }
    }

    @Override
    public List<Task> findAllUnfinishedForToday() {

        long todayBegin = DefaultScheduleDateBuilder.now().toDateBegin().getResult().getEpochMillisecond();

        return convert(taskRepository.findTaskEntitiesByTaskDateAndDone(todayBegin, false));
    }

    @Override
    public List<Task> findAllFinishedForToday() {

        long todayBegin = DefaultScheduleDateBuilder.now().toDateBegin().getResult().getEpochMillisecond();

        return convert(taskRepository.findTaskEntitiesByTaskDateAndDone(todayBegin, true));
    }

    /**
     * 没写完
     * @param year 年
     * @param monthOfYear 月份 1到12
     * @return
     */
    @Override
    public List<Task> findAllForYearAndMonth(int year, int monthOfYear) {

        List<Task> resultList = new ArrayList<>();

        int dayBegin = 1;
        ScheduleDateTime scheduleDateTime = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayBegin).getResult();
        int maxDay = scheduleDateTime.getMonthDayNumber();
        for (int i = dayBegin; i <= maxDay; i++) {
            scheduleDateTime = DefaultScheduleDateBuilder.of(scheduleDateTime).toDayOfMonth(i).getResult();
            resultList.addAll(convert(taskRepository.findTaskEntitiesByTaskDate(scheduleDateTime.getEpochMillisecond())));
        }

        return resultList;
    }

    @Override
    public void setDone(Task task) {

        if (task == null) {
            throw new NullPointerException("task不能为空");
        }

        Long id = task.getId();
        if (id == null) {
            throw new PrimaryKeyNotExistException("参数task不存在标识主键id");
        }

        if (id < 0) {
            throw new IllegalArgumentException("传入的参数的标识主键id小于0");
        }

        TaskEntity taskEntity = taskRepository.findOne(id);
        taskEntity.setDone(true);
        taskRepository.update(taskEntity);
    }

    @Override
    public void setUnDone(Task task) {

        if (task == null) {
            throw new NullPointerException("task不能为空");
        }

        Long id = task.getId();
        if (id == null) {
            throw new PrimaryKeyNotExistException("参数task不存在标识主键id");
        }

        if (id < 0) {
            throw new IllegalArgumentException("传入的参数的标识主键id小于0");
        }

        TaskEntity taskEntity = taskRepository.findOne(id);
        taskEntity.setDone(false);
        taskRepository.update(taskEntity);
    }

    @Override
    public int countFinishedForDate(int year, int monthOfYear, int dayOfMonth) {

        ScheduleDateTime scheduleDateTime = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayOfMonth).toDateBegin().getResult();

        return (int) taskRepository.countTaskEntitiesByTaskDateAndDone(scheduleDateTime.getEpochMillisecond(), true);
    }

    @Override
    public int countUnfinishedForDate(int year, int monthOfYear, int dayOfMonth) {

        ScheduleDateTime scheduleDateTime = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayOfMonth).toDateBegin().getResult();

        return (int) taskRepository.countTaskEntitiesByTaskDateAndDone(scheduleDateTime.getEpochMillisecond(), false);
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

    private TaskEntity convertToTaskEntity(Task task) {
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

        return taskEntity;
    }
}
