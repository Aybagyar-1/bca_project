package model;

import java.util.Date;

public class Notice {
    private int noticeId;
    private String title;
    private String description;
    private Date date;

    // Constructor for new notice
    public Notice(String title, String description) {
        this.title = title;
        this.description = description;
        this.date = new Date();
    }

    // Constructor for retrieving from database
    public Notice(int noticeId, String title, String description, Date date) {
        this.noticeId = noticeId;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // Getters and Setters
    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
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
