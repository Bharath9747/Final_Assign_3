package com.accolite.app.controller;

import com.accolite.app.entity2.User;
import freemarker.template.Template;

import java.util.List;

public interface Service {

    List<User> getUser();

    void addNotification(Template template, User user);
}
