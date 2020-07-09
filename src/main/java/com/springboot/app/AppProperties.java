package com.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppProperties implements ApplicationRunner {

    @Autowired
    private JwProperties jwProperties;

//    @Value("${jw.name}")
//    private String name;
//
//    @Value("${jw.fullname}")
//    private String fullname;
//
//    @Value("${jw.age}")
//    private String age;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("==================================");
        System.out.println("name = " + jwProperties.getName());
        System.out.println("fullname = " + jwProperties.getFullname());
        System.out.println("age = " + jwProperties.getAge());
        System.out.println("sessionTimeout = " + jwProperties.getSessionTimeout());
        System.out.println("==================================");
    }
}
