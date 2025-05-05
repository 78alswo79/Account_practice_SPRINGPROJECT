package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.service.AccountService;

public class BaseControllerTestClass {

	@Mock
	private AccountService accountService;
	
	@BeforeEach
    public void setUp() {
		MockitoAnnotations.openMocks(this);
        // Mocking the behavior of accountService
        when(accountService.getDateYear()).thenReturn(List.of(2025, 2034));
    }
	
	@Test
	public void getDateYear() {
		List<Integer> expectList = List.of(2025,2034);
		List<Integer> showYearList = accountService.getDateYear();
		
		assertNotNull(showYearList);			// List객체가 널인지 판단
		
		assertFalse(showYearList.isEmpty());	// 인자가 false일 때, 테스트 성공. 인자가 true면 테스트 실패.
		
		assertIterableEquals(expectList, showYearList, "둘 다 다릅니다!!");		// List 컬렉션 객체
//		System.out.println(assertIterableEquals(expectList, showYearList, "둘 다 다릅니다!!"));
	}
	
	
	
	
	
}
