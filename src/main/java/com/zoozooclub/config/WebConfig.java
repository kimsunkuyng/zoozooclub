package com.zoozooclub.config; // 패키지명은 자유

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 스프링 설정 클래스임을 알려줌
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/** 요청을 C:/img 폴더로 매핑
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/uploadFiles/");
    }
}
