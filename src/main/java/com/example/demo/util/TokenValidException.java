package com.example.demo.util;


/**
 * <p>AOP로 토큰 존재유무를 판별하여, 로그인 폼 페이지로 보내주는 익셉션</p>
 * @author Jcob LEE
 * */
public class TokenValidException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7376553828495970969L;

	public TokenValidException (String message) {
		super(message);
	}
}
