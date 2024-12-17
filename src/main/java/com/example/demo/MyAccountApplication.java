package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.mapper") // 매퍼 인터페이스가 위치한 패키지 경로
public class MyAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyAccountApplication.class, args);
	}

}
