package com.accolite.app;


import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.accolite.app.Final32Application.users;

public class Service {

    static Connection con = null;

    public static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Template", "root", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUsers() {
        try {

            Statement truncateStatement = con.createStatement();
            String truncateQuery = "Truncate table notification";
            truncateStatement.executeUpdate(truncateQuery);
            truncateQuery = "Truncate table user";
            truncateStatement.executeUpdate(truncateQuery);
            String name = "ABC ";
            for (int i = 1; i <= 20; i++) {
                User user = new User(i,name+i);
                users.add(user);
                String insert = "Insert into user(name) values(?)";
                PreparedStatement statement = con.prepareStatement(insert);
                statement.setString(1, name + i);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void freeMarker(int uid,String uname) {
        Configuration freeMarkerConfig = FreeMarkerConfig.getConfig();

        Template template = null;
        try {
            template = freeMarkerConfig.getTemplate("notification.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("user", new User(uname ));
            LocalDateTime time = LocalDateTime.now();
            model.put("notification",new Notification("Good Morning",time ));
            StringWriter writer = new StringWriter();
            template.process(model, writer);

            String content = writer.toString();
            addNotification(content,uid,time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addNotification(String content, int uid, LocalDateTime time) {
        String insert = "Insert into notification(content,time_,date_,uid) values(?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = con.prepareStatement(insert);
            statement.setString(1, content);
            statement.setTime(2, Time.valueOf(time.toLocalTime()));
            statement.setDate(3, Date.valueOf(time.toLocalDate()));
            statement.setInt(4,uid);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
