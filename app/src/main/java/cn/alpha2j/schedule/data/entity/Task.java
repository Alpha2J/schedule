package cn.alpha2j.schedule.data.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 因为intent.putExtract() 传对象的话需要实现Serializable接口, 所以这里实现该接口
 *
 * @author alpha
 * Created on 2017/11/4.
 */
public class Task implements Serializable {
    private Integer id;
    private String title;
    private String description;
    /**
     * Task 属于哪天. date字段存的是当天00 : 00 : 00 距离1970年1月1日00 : 00 : 00 的秒数
     */
    private Date date;

    private boolean isAlarm;

    /**
     * 提醒时间设置, 哪天哪个时间点进行提醒. alarmDateTime存的是该时间距离1970年1月1日00 : 00 : 00 的秒数
     */
    private Date alarmDateTime;

    /**
     * 表示该任务是否已完成
     */
    private boolean isDone;

    public Task() {}

    public Task(Integer id, String title, String description, Date date, boolean isAlarm, Date alarmDateTime, boolean isDone) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isAlarm = isAlarm;
        this.alarmDateTime = alarmDateTime;
        this.isDone = isDone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public Date getAlarmDateTime() {
        return alarmDateTime;
    }

    public void setAlarmDateTime(Date alarmDateTime) {
        this.alarmDateTime = alarmDateTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
