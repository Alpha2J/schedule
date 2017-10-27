package cn.alpha2j.schedule;

import java.util.Date;

/**
 * Created by alpha on 2017/10/26.
 */
public class Task {
    private Integer id;
    private String title;
    private String description;
    private Date date;

    public Task() {}

    public Task(Integer id, String title, String description, Date date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
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
}
