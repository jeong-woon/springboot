package com.springboot.app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        // SpringApplication.run(AppApplication.class, args); 이렇게 쓰면 커스터마이징 불가함.

//        SpringApplication app = new SpringApplication(AppApplication.class); // 인스턴스로 만들어서
//        app.setBanner(new Banner() {
//            @Override
//            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
//                out.println("=============================");
//                out.println("JW SPRINGBOOT");
//                out.println("=============================");
//            }
//        });
//        app.setBannerMode(Banner.Mode.OFF); // 배너 제거
//        app.run(args);  // 실행!

        new SpringApplicationBuilder()
                .sources(AppApplication.class)
                .run(args);
    }

}