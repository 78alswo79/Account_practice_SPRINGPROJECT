package com.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.util.CustomException;
import com.example.demo.util.TokenValidException;

import jakarta.servlet.http.HttpServletResponse;

// 공부하기 좋은.
@ControllerAdvice("com.example.demo")
public class CustomExceptionHandler {
	
	@ExceptionHandler(CustomException.class)
	public ModelAndView handleIllgalException(CustomException e, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("exceptionMessage", e.getMessage());
		mav.addObject("statusCode", HttpStatus.OK.value());
		mav.setViewName("/error/illegalException");		
		
		 // 응답 코드 설정
	    response.setStatus(HttpStatus.OK.value()); // 예를 들어 400 Bad Request로 설정
		
		return mav;
	}
	
	@ExceptionHandler(TokenValidException.class)
	public ModelAndView handleTokenValidException(HttpServletResponse response, TokenValidException e) {
		ModelAndView mav = new ModelAndView();
		
//		mav.addObject("exception", e);
//		mav.addObject("rowMessage", message);
//		mav.addObject("statusCode", HttpStatus.OK.value());
		mav.setViewName("/loginForm");		
		
		 // 응답 코드 설정
	    response.setStatus(HttpStatus.OK.value()); // 예를 들어 400 Bad Request로 설정
	    System.out.println("토큰이 만료됐으니 로그인폼으로 넘깁니다.");
		
		return mav;
	}
}
