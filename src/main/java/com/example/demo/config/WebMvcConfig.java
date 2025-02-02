package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	// 공부하기 좋은.
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 서버 실행 시, 루트 진입 설정해주기.
		registry.addRedirectViewController("/", "/index.do");
	}
	
	
}
