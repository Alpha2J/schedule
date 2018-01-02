package cn.alpha2j.schedule.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 表示与持久化交互的TaskEntity实体
 *
 * @author alpha
 * Created on 2017/11/4.
 */
@Entity(
        nameInDb = "Schedule_Task"
)
public class TaskEntity implements EntityIdentifier {

    @Id(autoincrement = true)
    private Long id;
    private String title;
    private String description;

    /**
     * 任务归属时间, 存储的是距离UTC时间1970-01-01T00:00:00 的时间毫秒数.
     */
    private long taskDate;

    /**
     * 是否提醒
     */
    private boolean alarm;

    /**
     * 任务提醒时间, 存储的是距离UTC时间1970-01-01T00:00:00 的时间毫秒数.
     */
    private long taskAlarmDateTime;

    /**
     * 表示该任务是否已完成
     */
    private boolean done;

    @Generated(hash = 397975341)
    public TaskEntity() {
    }

    @Generated(hash = 515043977)
    public TaskEntity(Long id, String title, String description, long taskDate, boolean alarm, long taskAlarmDateTime, boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskDate = taskDate;
        this.alarm = alarm;
        this.taskAlarmDateTime = taskAlarmDateTime;
        this.done = done;
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

    public long getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(long taskDate) {
        this.taskDate = taskDate;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public long getTaskAlarmDateTime() {
        return taskAlarmDateTime;
    }

    public void setTaskAlarmDateTime(long taskAlarmDateTime) {
        this.taskAlarmDateTime = taskAlarmDateTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean getAlarm() {
        return this.alarm;
    }

    public boolean getDone() {
        return this.done;
    }

    @Override
    public Long getIdentifier() {
        return getId();
    }
}
