package com.accolite.app;

import java.time.LocalDateTime;

public class Notification {
    private int id;
    private String content;
    private LocalDateTime timestamp;
    private int uid;

    public Notification() {
    }

    public Notification(String content, LocalDateTime timestamp) {
        this.content=content;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Notification(int id, String content, LocalDateTime timestamp, int uid) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.uid = uid;
    }
}
