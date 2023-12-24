package com.accolite.app;


import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.accolite.app.Final32Application.users;

@org.springframework.stereotype.Service
public class Service implements Services {
    @Autowired
    UserRepo repo;
    @Autowired
    NotificationRepo notificationRepo;


    @Override
    public void addNotification(String content, int uid, LocalDateTime time) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setTimestamp(time);
        notification.setUid(uid);
        notificationRepo.save(notification);
    }

    @Override
    public void addUser() {
        String name = "ABC ";
        for (int i = 1; i <= 20; i++) {
            User user = new User(i, name + i);
            users.add(user);
            repo.save(user);
        }
    }
}
