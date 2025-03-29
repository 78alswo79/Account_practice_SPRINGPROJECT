//package com.example.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig implements WebMvcConfigurer{
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(); // 비밀번호 인코더 설정
//    }
//    
//    
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf((auth) -> auth.disable());				// csrf disabled
//		http.formLogin((auth) -> auth.disable());			// form 로그인 방식 disable
////		http.formLogin((form) -> form
////                .loginPage("/login/loginForm.do") 				// 커스텀 로그인 페이지 URL
////                .permitAll() 							// 모든 사용자에게 로그인 페이지 접근 허용
////                .defaultSuccessUrl("/index.do", true) 		// 로그인 성공 후 리다이렉션 URL
////                .failureUrl("/login/loginForm.do?error=true")); 	// 로그인 실패 시 리다이렉션 URL); // 모든 사용자에게 로그인 페이지 접근 허용
//		http.httpBasic((auth) -> auth.disable());			// http basic 인증 방식 disabled
//		
//        //경로별 인가 작업
//		http
//        .authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/index.do").permitAll()
//				.requestMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().authenticated());
//		// 모든 경로에 대해 permitAll 설정
////        http.authorizeHttpRequests((auth) -> auth
////                .anyRequest().permitAll()); // 모든 요청을 허용
//		
//		
////		http.authorizeHttpRequests((auth) -> auth
////		        .requestMatchers("/index.do").permitAll() // 로그인 페이지는 허용
////		        .anyRequest().authenticated()); // 나머지 요청은 인증 필요
//
//        // 세션 설정 
//        http.sessionManagement((session) -> session
//        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//     // 세션 설정 
////        http.sessionManagement((session) -> session
////                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)); // 세션 사용
//
//        return http.build();
//
//    }
////	@Bean
////    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
////        AuthenticationManagerBuilder authenticationManagerBuilder = 
////            http.getSharedObject(AuthenticationManagerBuilder.class);
////        authenticationManagerBuilder
////            .inMemoryAuthentication()
////            .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
////            .and()
////            .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
////        return authenticationManagerBuilder.build();
////    }
//
////    @Override
////    public void configurePathMatch(PathMatchConfigurer configurer) {
////        configurer.setUseSuffixPatternMatch(true); // UseSuffixPatternMatch 옵션 추가
////        configurer.setPatternParser(null); // AntPathMatcher 사용
////    }
//	
//
//}
