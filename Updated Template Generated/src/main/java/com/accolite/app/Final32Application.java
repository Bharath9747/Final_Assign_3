package com.accolite.app;

import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.accolite.app.Service.*;

@SpringBootApplication
public class Final32Application {

	@Autowired
	Services services;
	 public static List<User> users = new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(Final32Application.class, args);
	}

	@Scheduled(fixedRateString = "PT1H")
	void callUserService(){
		services.addUser();
		System.out.println("Users Added");
	}
	@Scheduled(initialDelay = 10000,fixedRateString = "PT2M")
	void sendNotification(){
		users.forEach(
				x->freeMarker(x.getId(),x.getName())
		);
		System.out.println("Notification Sent "+ LocalDateTime.now());
	}
	public void freeMarker(int uid, String uname) {
		freemarker.template.Configuration freeMarkerConfig = FreeMarkerConfig.getConfig();

		Template template = null;
		try {
			template = freeMarkerConfig.getTemplate("notification.ftl");
			Map<String, Object> model = new HashMap<>();
			model.put("user", new User(uname));
			LocalDateTime time = LocalDateTime.now();
			model.put("notification", new Notification("Good Morning", time));
			StringWriter writer = new StringWriter();
			template.process(model, writer);

			String content = writer.toString();
			services.addNotification(content, uid, time);
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
