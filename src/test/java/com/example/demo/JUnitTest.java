package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {

	@DisplayName("1 + 2 = 3이다") 		// 테스트 이름
	@Test
	public void junitTest() {
		int a = 1;
		int b = 2;
		
		int sum = 3;
		
		assertEquals(sum, a + b);
		
	}
	
	@DisplayName("1 + 3 = 4이다")
	@Test
	public void junitFaildTest() {
		int a = 1;
		int b = 3;
		
		int sum = 3;
		assertEquals(sum, a + b);			// 실패하는 케이스
	}
}
