package com.example.demo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitCycleTest2 {

	@BeforeEach
	public void beforeEach() {
		System.out.println("Hello!!");
	}
	
	@Test
	public void junitQuiz1() {
		System.out.println("This is first Test!!");
	}
	
	@Test
	public void junitQuiz2() {
		System.out.println("This is Second Test!!");
	}
	
	@AfterAll
	public static void afterAll() {
		System.out.println("Bye!!");
	}
}
