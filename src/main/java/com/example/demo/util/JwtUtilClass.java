package com.example.demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtilClass {
	private final String SECRET_KEY = "my_Hekireky_key";
	private final long EXPIRE_TIME = 1000 * 60 * 60;		// 기본 1시간
	
	// JWT  생성
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		// JWT생성 시, 추가해서 저장하는 부분은 claims부분이다. 공부하기 좋은.
		claims.put("myKey", SECRET_KEY);
		claims.put("gimozzizzi", "gimozzizzi");
		return createToken(claims, userName);
	}
	
	// JWT 생성 메소드
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	// TODO JWT 검증 메소드 구현하기
	// TODO 클레임 추출 메소드
	
}
