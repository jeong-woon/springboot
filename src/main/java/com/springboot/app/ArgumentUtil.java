package com.springboot.app;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ArgumentUtil {

    public ArgumentUtil(ApplicationArguments arguments) {
        System.out.println("foo : " + arguments.containsOption("foo")); // 없음. --jvm 아규먼트
        System.out.println("bar : " + arguments.containsOption("bar")); // 있음. --로 오는것만 아규먼트로 씀.
    }

}
