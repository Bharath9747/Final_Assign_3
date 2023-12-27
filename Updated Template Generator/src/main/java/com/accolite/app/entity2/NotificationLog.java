package com.accolite.app.entity2;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notificationlog")
public class NotificationLog {
    @Id
    private Long id;

    @Override
    public String toString() {
        return  "id : " + id +
                ", userId : " + userId +
                ", content : " + content +
                ", timestamp : " + timestamp ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Column(name = "user_id")
    private Long userId;

    @Column(columnDefinition = "TEXT")
    @Lob
    private String content;
    private LocalDateTime timestamp;
}
