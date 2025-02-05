package com.example.demo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtilClass {
	private final String SECRET_KEY = "my_Hekireky_key";
	private final long EXPIRE_TIME = 1000 * 60 * 60;		// 기본 1시간
	
	/**
	 * <p>jwt 토큰에 필요한 데이터 추가 가공 메소드.<br>
	 * 추가 데이터는 Map<>으로 만들어 claims부분에다가 넣으면 된다.</p>
	 * @author kephas_LEEMINJAE
	 * @param String
	 * @return String
	 * */
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		// JWT생성 시, 추가해서 저장하는 부분은 claims부분이다. 공부하기 좋은.
		claims.put("myKey", SECRET_KEY);
		claims.put("gimozzizzi", "gimozzizzi");
		return createToken(claims, userName);
	}
	
	
	/**
	 * <p>JWT 생성 메소드.<br>
	 * 데이터본체인 claims, subject, iat, 만료기간, 사인까지 설정해 Jwt토큰을 생성한다.</p>
	 * @author kephas_LEEMINJAE
	 * @param Map<String, Object> 
	 * @param String
	 * */
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}
	
	/**
	 * <p>JWT 검증 메소드.<br>
	 * cookie에 넣은 'jwt'정보를 파싱해서 받아온 token값을 가지고 현재시간과 비교해서 유효한지 검증을 한다.</p>
	 * @author kephas_LEEMINJAE
	 * @param String
	 * @return Claims
	 * */
	public Claims validateToken(String token) {
		try {
			// 공부하기좋은
			Claims claims = Jwts.parser()					// jwt 파싱
					.setSigningKey(SECRET_KEY)				// 생설할 때 만든 key로 무결성을 확인한다.
					.parseClaimsJws(token)					// 주어진 문자열을 파싱하고, 서명을 검증. 유효하다면 Jws<Claims>를 반환한다.
					.getBody();								// Jws<Claims>객체에서 클레임부분을 추출한다.
			
			// JWT 만료 시간 검증
			// 공부하기 좋은
			if (isJwtExpiration(claims)) {
				throw new CustomException("JWT token is expired");
			}
			
			return claims;
		} catch (SignatureException e) {
			// TODO: handle exception
			throw new CustomException("invalid JWT signature!!");
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	

	/**
	 * <p>만료기간 검증 메소드.<br>
	 * 생성된 JWT 만료시간이 유효한지 검증한다.
	 * true라면 만료했다고 본다.</p>
	 * @author kephas_LEEMINJAE
	 * @param Claims
	 * @return boolean
	 * */
	private boolean isJwtExpiration(Claims claims) {

		long expirationTime = claims.getExpiration().getTime();			// 만료시간을 밀리초로 가져온다.
		long currentTime = System.currentTimeMillis();					// 현새시간을 밀리초로 가져온다.
		
		// true면 jwt는 만료가 된 것이다.
		return expirationTime < currentTime;
	}
	
}
