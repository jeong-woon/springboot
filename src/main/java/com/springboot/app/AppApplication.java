package com.springboot.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        // SpringApplication.run(AppApplication.class, args); 이렇게 쓰면 커스터마이징 불가함.
        SpringApplication app = new SpringApplication(AppApplication.class);
        app.run(args);
    }

}