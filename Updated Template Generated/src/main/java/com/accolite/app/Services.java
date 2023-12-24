package com.accolite.app;

import java.time.LocalDateTime;

public interface Services {
    void addUser();


    void addNotification(String content, int uid, LocalDateTime time);
}
