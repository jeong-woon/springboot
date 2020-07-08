package com.springboot.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppProperties implements ApplicationRunner {

    @Value("${jw.name}")
    private String name;

    @Value("${jw.fullname}")
    private String fullname;

    @Value("${jw.age}")
private String age;

@Override
public void run(ApplicationArguments args) throws Exception {
        System.out.println("==================================");
        System.out.println("name = " + name);
        System.out.println("name = " + fullname);
        System.out.println("age = " + age);
        System.out.println("==================================");
        }
        }
