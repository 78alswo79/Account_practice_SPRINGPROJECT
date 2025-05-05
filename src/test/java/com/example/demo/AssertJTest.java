package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssertJTest {

	@DisplayName("assertJ 테스트입니다.")
	@Test
	public void junitTest() {
		String name1 = "홍길동";
		String name2 = "홍길동";
		String name3 = "연시은";
		
		// 모든 변수가 Null이 아닌지 판별
		assertThat(name1).isNotNull();
		assertThat(name2).isNotNull();
		assertThat(name3).isNotNull();
		
		// name1과 name2가 같은지
		assertThat(name1).isEqualTo(name2);
		// name1과 name3이 다른지
		assertThat(name1).isNotEqualTo(name3);
	}
	
	@DisplayName("이번엔 int로 비교합니다.")
	@Test
	public void junitTest2() {
		int number1 = 15;
		int number2 = 0;
		int number3 = -5;
		
		// number1이 양수인지 판별
		assertThat(number1).isPositive();
		// number2이 0인지 판별
		assertThat(number2).isZero();
		// number3이 음수인지 판별
		assertThat(number3).isNegative();
		// number1이 number2보다 큰지 판별
		assertThat(number1).isGreaterThan(number2);
		// number3은 number2보다 작은지 판별
		assertThat(number3).isLessThan(number2);
	}
}
