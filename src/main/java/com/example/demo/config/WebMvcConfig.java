package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.handler.MyInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Autowired
	MyInterceptor myInterceptor;
	
	// 공부하기 좋은.
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// 서버 실행 시, 루트 진입 설정해주기.
		registry.addRedirectViewController("/", "/index.do");
	}

	@Override
	// 인터셉터 등록. 공부하기 좋은
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(myInterceptor)
				.addPathPatterns("/addAccount.do", "/getDateMonth.do", "/getMyAccountList.do");					// 인터셉터를 적용할 URL 매핑
	}
	
	
	
	
}
