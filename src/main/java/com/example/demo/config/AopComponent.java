package com.example.demo.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.util.CookieUtil;
import com.example.demo.util.TokenValidException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AopComponent {

	private final HttpServletRequest  req;
	private final HttpServletResponse res;
	
	@Autowired 
	private CookieUtil cookieUtil;
	
	@Pointcut("execution(* com.example.demo.controller..*(..)) &&" +
			"!execution(* com.example.demo.controller.APIController.login(..)) "
			)
	public void ExcludeMethod() {}
	
	@Before("ExcludeMethod()")
	public void jwtValidation() {
		String token = cookieUtil.getJwtFromCookie(req);
		System.out.println("기모찌>>>>>>>>>>>>>>>>>");
		if (token.isBlank()) {
			throw new TokenValidException("token is invalid");
		}
	}
}
