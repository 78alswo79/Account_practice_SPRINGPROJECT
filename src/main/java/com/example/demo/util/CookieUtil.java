package com.example.demo.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {
	
	public Cookie createCookies(String token) {
		Cookie cookie = new Cookie("jwt", token);
		cookie.setHttpOnly(false); 			// JavaScript에서 접근 불가
		cookie.setSecure(true); 			// HTTPS에서만 전송
		cookie.setPath("/"); 				// 쿠키의 유효 경로
		cookie.setMaxAge(86400); 			// 쿠키의 유효 기간 (초)
		
		return cookie;
	}
	
	public String getJwtFromCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		String res = "";
		
		if (cookies == null) return res = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("jwt")) {
					res = cookie.getValue();
					break;
				}
			}
		} 
		return res;
		
	}
}
