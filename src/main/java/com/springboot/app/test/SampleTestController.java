package com.springboot.app.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleTestController {

    @Autowired
    private SampleTestService sampleTestService;

    @GetMapping("/hello")
    public String hello(){
        return "hello "+sampleTestService.getName();
    }
}
