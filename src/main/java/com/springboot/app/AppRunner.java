package com.springboot.app;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1) // 러너가 여러개 일때 그 순서를 지정할 수 있음. 숫자가 작을수록 우선순위임.
//public class AppRunner implements ApplicationRunner {
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("foo : " + args.containsOption("foo")); // 없음. --jvm 아규먼트
//        System.out.println("bar : " + args.containsOption("bar")); // 있음. --로 오는것만 아규먼트로 씀.
//    }
//}
public class AppRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(args).forEach(System.out::println);
    }
}