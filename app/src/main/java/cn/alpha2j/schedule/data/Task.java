package cn.alpha2j.schedule.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    private Long mId;
    private String mTitle;
    private String mDescription;
    /**
     * 任务归属时间
     */
    private ScheduleDateTime mTime;

    private boolean mRemind;

    /**
     * 任务提醒时间
     */
    private ScheduleDateTime mRemindTime;

    /**
     * 表示该任务是否已完成
     */
    private boolean mDone;

    public Task() {
    }

    public Task(Long id, String title, String description, ScheduleDateTime time, boolean remind, ScheduleDateTime remindTime, boolean done) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mTime = time;
        mRemind = remind;
        mRemindTime = remindTime;
        mDone = done;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public ScheduleDateTime getTime() {
        return mTime;
    }

    public void setTime(ScheduleDateTime time) {
        mTime = time;
    }

    public boolean isRemind() {
        return mRemind;
    }

    public void setRemind(boolean remind) {
        mRemind = remind;
    }

    public ScheduleDateTime getRemindTime() {
        return mRemindTime;
    }

    public void setRemindTime(ScheduleDateTime remindTime) {
        mRemindTime = remindTime;
    }

    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean done) {
        mDone = done;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == null) {
            return false;
        }

        if(obj == this) {
            return true;
        }

        if(obj.getClass() != getClass()) {
            return false;
        }

        Task task = (Task) obj;

        return new EqualsBuilder()
                .append(mId, task.mId)
                .isEquals();
    }

    @Override
    public int hashCode() {

        return new HashCodeBuilder(7, 17)
                .append(mId)
                .toHashCode();
    }
}
