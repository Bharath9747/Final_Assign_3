package com.accolite.app.entity1;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(columnDefinition = "TEXT")
    @Lob
    private String content;
    private LocalDateTime timestamp;
    private Long uid;

    @Override
    public String toString() {
        return  "id=" + id +
                ", content : " + content  +
                ", timestamp : " + timestamp +
                ", uid : " + uid ;
    }

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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Notification(int id, String content, LocalDateTime timestamp, Long uid) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.uid = uid;
    }
}
