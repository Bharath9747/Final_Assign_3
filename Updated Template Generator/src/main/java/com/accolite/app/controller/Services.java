package com.accolite.app.controller;


import com.accolite.app.entity1.Notification;
import com.accolite.app.entity2.NotificationLog;
import com.accolite.app.entity2.User;
import com.accolite.app.repo1.NotificationRepo;
import com.accolite.app.repo2.NotificationLogRepo;
import com.accolite.app.repo2.UserRepo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Service
public class Services implements Service {

    @Autowired
    NotificationLogRepo logRepo;
    @Autowired
    UserRepo repo;
    @Autowired
    NotificationRepo notificationRepo;


    @Override
    public void addNotification(Template template, User user) {
        List<NotificationLog> logs = logRepo.findAll().stream().filter(x -> x.getUserId() == user.getId()).toList();
        final int[] i = {0};
        Notification latestNotification = notificationRepo.findAll().stream().filter(x -> x.getUid() == user.getId()).toList().stream().sorted(Comparator.comparing(Notification::getTimestamp).reversed()).findFirst().orElse(null);
        NotificationLog latestLog = logs.stream().sorted(Comparator.comparing(NotificationLog::getTimestamp).reversed()).findFirst().orElse(null);
        if (latestNotification != null && latestLog.getTimestamp().equals(latestNotification.getTimestamp())) {
            System.out.println();
            System.out.println("No New Notification for User " + user.getId());
            System.out.println();
            return;
        }
        logs.forEach(
                x -> {
                    i[0]++;
                    Notification notification = new Notification();
                    notification.setContent(x.getContent());
                    notification.setTimestamp(x.getTimestamp());
                    notification.setUid(x.getUserId());
                    Map<String, Object> model = new HashMap<>();
                    model.put("user", user);
                    LocalDateTime time = LocalDateTime.now();
                    model.put("notification", notification);
                    StringWriter writer = new StringWriter();
                    try {
                        template.process(model, writer);
                    } catch (TemplateException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    String content = writer.toString();
                    notificationRepo.save(notification);
                    System.out.println();
                    System.out.println(content);
                    System.out.println();

                }

        );
        if (i[0] == 0) {
            System.out.println();
            System.out.println("No New Notification for User " + user.getId());
            System.out.println();

        }


    }

    @Override
    public List<User> getUser() {
        return repo.findAll();
    }
}
