package com.springboot.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// @EnableWebMvc 이 어노테이션을 붙이면 스프링 부트가 제공하는 모든 mvc 기능 사라지고, 직접 정의해야줘야 함.
// 무슨 컨텐츠 네고시에이션??? 이런거 설정하는게 귀찮다고 함.
public class WebConfig implements WebMvcConfigurer {
    // 여기서 추가기능 설정 가능

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**") //디렉토리 추가
                .addResourceLocations("classpath:/m/")  // 반드시! / 로 끝나야함! 이렇게 안하면 맵핑이 잘 안됨!
                .setCachePeriod(20);    // 여기서 추가하는 핸들러는 캐시 정책이 안들어가기때문에 직접 설정해줘야함.
    }
}
