package cn.alpha2j.schedule.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.alpha2j.schedule.data.Task;
import cn.alpha2j.schedule.data.entity.TaskEntity;
import cn.alpha2j.schedule.data.repository.TaskRepository;
import cn.alpha2j.schedule.data.repository.impl.TaskRepositoryImpl;
import cn.alpha2j.schedule.data.service.TaskService;
import cn.alpha2j.schedule.exception.PrimaryKeyNotExistException;
import cn.alpha2j.schedule.exception.RemindTimeCanNotBeNullException;
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
     * @return id
     */
    @Override
    public long addTask(Task task) {

        validateTask(task);

        return taskRepository.save(convertToTaskEntity(task));
    }

    @Override
    public void delete(Task task) {

        taskRepository.delete(convertToTaskEntity(task));
    }

    /**
     * 新增或者更新一个task, 如果设置了id, 且该id的数据存在, 那么更新该条数据.
     * 如果该id的数据不存在, 那么会插入一条该id的数据.
     *
     * 如果id数据不存在, 那么直接新增一条数据, id为自增
     *
     * @param task 不能为null
     * @return id
     */
    @Override
    public long addOrUpdateTask(Task task) {

        validateTask(task);

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

        long startTime = DefaultScheduleDateBuilder.now().toDateBegin().getResult().getEpochMillisecond();
//        因为底层用的是between...and...语句, 包括上下限, 所以上限需要-1;表示最接近明天的最后一毫秒
        long endTime = getEndTime(startTime);

        return convert(taskRepository.findTaskEntitiesByDoneAndTimeBetween(false, startTime, endTime));
    }

    @Override
    public List<Task> findAllFinishedForToday() {

        long startTime = DefaultScheduleDateBuilder.now().toDateBegin().getResult().getEpochMillisecond();
//        因为底层用的是between...and...语句, 包括上下限, 所以上限需要-1;表示最接近明天的最后一毫秒
        long endTime = getEndTime(startTime);

        return convert(taskRepository.findTaskEntitiesByDoneAndTimeBetween(true, startTime, endTime));
    }

    /**
     * 没写完
     * @param year 年
     * @param monthOfYear 月份 1到12
     * @return ..
     */
    @Override
    public List<Task> findAllForYearAndMonth(int year, int monthOfYear) {

        List<Task> resultList = new ArrayList<>();

        int dayBegin = 1;
        ScheduleDateTime yearAndMonth = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayBegin).toDateBegin().getResult();
        int maxDay = yearAndMonth.getMonthDayNumber();
        for (int i = dayBegin; i <= maxDay; i++) {
            long startTime = DefaultScheduleDateBuilder.of(yearAndMonth).toDayOfMonth(i).getResult().getEpochMillisecond();
            long endTime = getEndTime(startTime);

            resultList.addAll(convert(taskRepository.findTaskEntitiesByTimeBetween(startTime, endTime)));
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

        long startTime = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayOfMonth).toDateBegin().getResult().getEpochMillisecond();
        long endTime = getEndTime(startTime);

        return (int) taskRepository.countTaskEntitiesByDoneAndTimeBetween(true, startTime, endTime);
    }

    @Override
    public int countUnfinishedForDate(int year, int monthOfYear, int dayOfMonth) {

        long startTime = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayOfMonth).toDateBegin().getResult().getEpochMillisecond();
        long endTime = getEndTime(startTime);

        return (int) taskRepository.countTaskEntitiesByDoneAndTimeBetween(false, startTime, endTime);
    }

    @Override
    public Map<Integer, List<Task>> findAndMap(int year, int monthOfYear) {

        int dayBegin = 1;
        ScheduleDateTime yearAndMonth = DefaultScheduleDateBuilder.now().toDate(year, monthOfYear, dayBegin).toDateBegin().getResult();
        int maxDay = yearAndMonth.getMonthDayNumber();

        Map<Integer, List<Task>> resultMap = new HashMap<>(maxDay);
        for (int i = dayBegin; i <= maxDay; i++) {
            long startTime = DefaultScheduleDateBuilder.of(yearAndMonth).toDayOfMonth(i).getResult().getEpochMillisecond();
            long endTime = getEndTime(startTime);

            List<Task> temp = convert(taskRepository.findTaskEntitiesByTimeBetween(startTime, endTime));
            if(temp.size() != 0) {
                resultMap.put(i, temp);
            }
        }

        return resultMap;
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
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setDone(taskEntity.isDone());

        task.setRemind(taskEntity.isRemind());
        task.setRemindTime(ScheduleDateTime.of(taskEntity.getRemindTime()));

        task.setTime(ScheduleDateTime.of(taskEntity.getTime()));

        return task;
    }

    private TaskEntity convertToTaskEntity(Task task) {

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setDone(task.isDone());
        taskEntity.setRemind(task.isRemind());
//        这里如果不进行判断, 可能会抛空指针
        if(task.isRemind()) {
            taskEntity.setRemindTime(task.getRemindTime().getEpochMillisecond());
        }

//        所有Task到TaskEntity的转换都是这里处理
//        时间精确到分钟, 秒以后的全部去除
        //对任务的时间进行处理, 确保任务的时间是当天零点
        ScheduleDateTime time = task.getTime();
        if (time == null) {
//            如果没有设置时间, 那么将时间设置到当天0时
            time = DefaultScheduleDateBuilder.now().toDateBegin().getResult();
        } else {
//            如果设置了时间, 那么将时间精确到分
            time = DefaultScheduleTimeBuilder.of(time).toMinuteStart().getResult();
        }
        taskEntity.setTime(time.getEpochMillisecond());

        return taskEntity;
    }

    private void validateTask(Task task) {

        if(task == null) {
            throw new NullPointerException("task不能为空");
        }

//        标题不能为null, 也不能为空串
        //        任务不能没有所属时间
        if(task.getTitle() == null || "".equals(task.getTitle()) || task.getTime() == null) {
            throw new IllegalArgumentException("task字段不合法, title为null或者空串? time字段为null?");
        }

//        设置了提醒, 但是提醒时间为null
        if(task.isRemind() && task.getRemindTime() == null) {
            throw new RemindTimeCanNotBeNullException("设置了提醒, 那么提醒时间不能为null");
        }
    }

    private long getEndTime(long startTime) {

        return (startTime + 24 * 60 * 60 * 1000) - 1;
    }
}
