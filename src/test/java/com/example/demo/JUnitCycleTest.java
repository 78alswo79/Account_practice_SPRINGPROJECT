package com.example.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitCycleTest {

	
	@BeforeAll                             // 전체 테스트를 시작하기 전에 1회 실행하므로 static으로 선언.
	static void beforeAll() {
		System.out.println("@BeforeAll~!!");
	}
	
	@BeforeEach                            // 테스트 케이스를 시작하기 전마다 실행.
	public void beforeEach() {
		System.out.println("@BeforeEach!!");
	}
	
	@Test
	public void test1() {
		System.out.println("test1!~~~");
	}
	
	@Test
	public void test2() {
		System.out.println("test2!~~~");
	}
	
	@Test
	public void test3() {
		System.out.println("test3!~~~");
	}
	
	@AfterAll
	static void afterAll() {              // 전체테스트를 마치고 종료하기 전에 1회 실행하므로 메소드는 static으로 선언
		System.out.println("@AfterAll~!!!");
	}
	
	@AfterEach
	public void afterEach() {             // 테스트 케이스를 종료하기 전마다 실행
		System.out.println("@AfterEach~!!!");
	}
}
