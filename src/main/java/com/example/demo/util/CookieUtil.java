package com.example.demo.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {
	
	/**
	 * <p> 쿠키를 프로토콜, 경로, 유효기간을 설정해서 생성한다.</p>
	 * @author LMJ
	 * @param String
	 * @return Cookie
	 * */
	public Cookie createCookies(String token) {
		Cookie cookie = new Cookie("jwt", token);
		cookie.setHttpOnly(false); 			// JavaScript에서 접근 불가
		cookie.setSecure(true); 			// HTTPS에서만 전송
		cookie.setPath("/"); 				// 쿠키의 유효 경로
		cookie.setMaxAge(86400); 			// 쿠키의 유효 기간 (초)
		
		return cookie;
	}
	/**
	 * <p>쿠키안 jwt토클을 파싱한다.<br>
	 * 쿠키자체가 없으면 ""반환.</p>
	 * @author LMJ
	 * @param HttpServletRequest
	 * @return String
	 * */
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
	
	/**
	 * <p>jwt가 만료되면 삭제한다.</p>
	 * @author LMJ
	 * @param HttpServletRequest
	 * @return void
	 * */
	public static void removeJwtCookie(HttpServletRequest req, HttpServletResponse response) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("jwt")) {
					cookie.setHttpOnly(false);
					cookie.setSecure(true);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
	}
}
