package com.task.tracker;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("timeapp")
public class TaskTrackerApiApplication {

	public static void main(String[] args) {
		System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "dev1");
		SpringApplication.run(TaskTrackerApiApplication.class, args);
	}

	@GetMapping("/welcome")
	public String getApplicationStatus(){
		return "Welcome to task tracker api.";
	}
}
