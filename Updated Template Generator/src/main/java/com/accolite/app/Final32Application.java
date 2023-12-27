package com.accolite.app;

import com.accolite.app.controller.Service;
import com.accolite.app.controller.Services;
import com.accolite.app.entity2.User;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication()

public class Final32Application {
	List<User> users = new ArrayList<>();

	@Autowired
	Service service;
	public static void main(String[] args) {
		SpringApplication.run(Final32Application.class, args);
	}

	@Scheduled(fixedRateString = "PT1H")
	void callUserService(){
		users = service.getUser();
		if(users.size()==0)
			System.out.println("No Registration");
		else
			System.out.println("Users Added");
	}
	@Scheduled(initialDelay = 10000,fixedRateString = "PT1M")
	void sendNotification(){
		if(users.size()==0)
			System.out.println("Waiting for Users to register");
		else
		{
			users.forEach(
					x->freeMarker(x)
			);
		}

	}
	public void freeMarker(User user) {
		freemarker.template.Configuration freeMarkerConfig = FreeMarkerConfig.getConfig();

		Template template = null;
		try {
			template = freeMarkerConfig.getTemplate("notification.ftl");
			service.addNotification(template,user);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration{

}
