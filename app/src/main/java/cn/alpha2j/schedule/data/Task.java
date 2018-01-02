package cn.alpha2j.schedule.data;

import java.io.Serializable;

import cn.alpha2j.schedule.time.ScheduleDateTime;

/**
 * 表示项目使用的Task对象.
 * 因为使用很多接口都需要实现Serializable接口, 比如intent.putExtract() 传对象的话需要实现Serializable接口
 * 所以实现Serializable接口
 *
 * @author alpha
 */
public class Task implements Serializable {

    /**
     * 在TaskEntity中用的是Long, 可以让GreenDao识别设置自动增长键.
     * 但是这里为long, 可以让程序在使用的时候避免发生空指针
     * 2018 01 02更正, 使用回Long, 如果不适当的时候调用使用id应该让他进行空指针运行时报异常
     */
    private Long id;
    private String title;
    private String description;
    /**
     * 任务归属时间
     */
    private ScheduleDateTime taskDate;

    private boolean isAlarm;

    /**
     * 任务提醒时间
     */
    private ScheduleDateTime taskAlarmDateTime;

    /**
     * 表示该任务是否已完成
     */
    private boolean isDone;

    public Task() {}

    public Task(Long id, String title, String description, ScheduleDateTime taskDate, boolean isAlarm, ScheduleDateTime taskAlarmDateTime, boolean isDone) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskDate = taskDate;
        this.isAlarm = isAlarm;
        this.taskAlarmDateTime = taskAlarmDateTime;
        this.isDone = isDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ScheduleDateTime getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(ScheduleDateTime taskDate) {
        this.taskDate = taskDate;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public ScheduleDateTime getTaskAlarmDateTime() {
        return taskAlarmDateTime;
    }

    public void setTaskAlarmDateTime(ScheduleDateTime taskAlarmDateTime) {
        this.taskAlarmDateTime = taskAlarmDateTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
