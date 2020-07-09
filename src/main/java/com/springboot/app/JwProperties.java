package com.springboot.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.time.Duration;

@Component
@ConfigurationProperties("jw")
@Validated // 하이버네이트 밸리데이터 사용함.
public class JwProperties {
    // JSR 303 구현체?? 하이버네이트? 이게 뭔소린지 잘..
    @NotEmpty   // 이렇게 선언하고 프로퍼티에서 값 지우면 에러남.
    private String name;
    private int age;
    private String fullname;

//    @DurationUnit(ChronoUnit.SECONDS) 이 어노테이션이 없어도 s 등의 단위 표기만 잘 해주면 나옴.
    private Duration sessionTimeout = Duration.ofSeconds(30);

    public Duration getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Duration sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
