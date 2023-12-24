package com.accolite.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.accolite.app.Service.*;

@SpringBootApplication
public class Final32Application {

	 public static List<User> users = new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(Final32Application.class, args);
	}

	@Scheduled(fixedRateString = "PT1H")
	void callUserService(){
		createConnection();
		addUsers();
		System.out.println("Users Added");
	}
	@Scheduled(initialDelay = 10000,fixedRateString = "PT2M")
	void sendNotification(){
		users.forEach(
				x->freeMarker(x.getId(),x.getName())
		);
		System.out.println("Notification Sent "+ LocalDateTime.now());
	}

}

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration{

}
