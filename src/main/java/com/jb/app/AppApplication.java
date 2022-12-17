package com.jb.app;

import com.jb.app.utils.ValidationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);

	}

}
