package com.girikgarg.learningspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LearningspringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningspringbootApplication.class, args);
	}

}
