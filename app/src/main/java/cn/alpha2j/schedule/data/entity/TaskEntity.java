package cn.alpha2j.schedule.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

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
    @NotNull
    private String title;
    private String description;
    /**
     * 任务归属时间, 存储的是距离UTC时间1970-01-01T00:00:00 的时间毫秒数.
     */
    private long time;

    /**
     * 是否提醒
     */
    private boolean remind;

    /**
     * 任务提醒时间, 存储的是距离UTC时间1970-01-01T00:00:00 的时间毫秒数.
     */
    private long remindTime;

    /**
     * 表示该任务是否已完成
     */
    private boolean done;

    @Generated(hash = 397975341)
    public TaskEntity() {
    }

    @Generated(hash = 1230521272)
    public TaskEntity(Long id, @NotNull String title, String description, long time, boolean remind, long remindTime,
            boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.remind = remind;
        this.remindTime = remindTime;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public long getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(long remindTime) {
        this.remindTime = remindTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public Long getIdentifier() {
        return getId();
    }

    public boolean getRemind() {
        return this.remind;
    }

    public boolean getDone() {
        return this.done;
    }
}
