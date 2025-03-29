package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.PageVO;
import com.example.demo.dto.Test;
import com.example.demo.service.AccountService;
import com.example.demo.util.CookieUtil;
import com.example.demo.util.CustomException;
import com.example.demo.util.JwtUtilClass;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BaseController {

	@Autowired
	JwtUtilClass jwtUtil;
	
	@Autowired
	private AccountService accoutService;
	
	@Autowired
	private CookieUtil cookieUtil;
	
	// application.properties에 정의된 값을 가져올 때. 공부하기 좋은.
	@Value("${spring.datasource.url}")
	private String datasourceUrl;
	

	@GetMapping("/index.do")
	public ModelAndView index() {
		//24년 / OO월 불러오기
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
				
		List<Integer> showYearList = accoutService.getDateYear();
		
		mav.addObject("showYearList", showYearList);
		System.out.println(datasourceUrl);
		
		return mav;
	}
	
	@GetMapping("/getDateMonth.do")
	public ModelAndView getMethodName(@RequestParam String year) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("getMonth");
		Test test = new Test();
		
		test.setYear(year);
		
		List<Integer> showMonthList = accoutService.getDateMonth();
		mav.addObject("test", test);
		mav.addObject("showMonthList", showMonthList);
		
		return mav;
	}
	
	@GetMapping("/getMyAccountList.do")
	public ModelAndView getMethodName(@RequestParam String year, @RequestParam String month
			, @RequestParam(required = false, defaultValue = "0") int currentPage
			, @RequestParam(required = false/* , defaultValue = "" */) String filterOption
			, @RequestParam(required = false/* , defaultValue = "15" */) Integer perPage
			, HttpSession session
			, HttpServletRequest req) {
		
		ModelAndView mav = new ModelAndView();
		List<Test> testList = new ArrayList<>();
		PageVO pageVO;
		Test test = new Test();
		// 공부하기 좋은.
		// 방법 1. 예외처리를 하거나
		// 방법 2. 리다이렉트로 처리한다.
		// 얘는 결국 주석처리 한다.
//		if (session.getAttribute("user") == null) { 
//			// throw new CustomException("검증된 유저가 아닙니다. 접근이 안됩니다."); 
//			mav.setViewName("redirect:/login/loginForm.do");
//			return mav;
//		}
		
		String token = cookieUtil.getJwtFromCookie(req);
		if (token.isBlank()) {
			mav.setViewName("redirect:/login/loginForm.do");
			return mav;
		}
		// Jwt 토큰으로 유효성음 검증.
		Claims claims = jwtUtil.validateToken(token);
		if (!claims.isEmpty()) {
			
			mav.setViewName("getMyAccountList");
			test = accoutService.setDateYearMonth(year, month);
			
			// 첫 리스트 진입 시. 현재 페이지 넘버 세팅
			if (currentPage == 0) {
				currentPage = 1;
			}
			
			// 첫 진입 시, 필터옵션 값 세팅. == content 필드를 의미한다.
			if (filterOption == null) {
				filterOption = "";
			}
			
			// 첫 진입 시, 15로 세팅.
			if (perPage == null) {
				perPage = 15;
			}
			
			
			int totalListCnt = accoutService.getTotalListCnt(test);
			pageVO = new PageVO(currentPage, perPage, totalListCnt);
			//화면에 쁘려준다.
			testList = accoutService.getMyAccountList(test, pageVO, filterOption);
			
//		System.out.println("totalListCnt" + totalListCnt);
//		System.out.println("currentPage" + currentPage);
//		System.out.println("perPage" + perPage);
//		System.out.println("filterOption" + filterOption);
//		System.out.println("페이징 추가한 testList" + testList);
//		System.out.println("페이징 표시할 총 페이지 수" + pageVO.getTotalPages());
//		System.out.println("pageVO" + pageVO);
			
			// 필터 옵션(== 콘탠트)
			List<String> getContentList = accoutService.getFilterList(test);
			
			if (testList != null && !testList.isEmpty()) {			
				// 이 화면에는 가계부를 적을 수 있는 기능이있다.
				mav.addObject("testList", testList);
				mav.addObject("getContentList", getContentList);
				mav.addObject("pageVO", pageVO);
			} else {
				mav.addObject("testList", "");
			}
			
			mav.addObject("year", year);
			mav.addObject("month", month);	
		}
		return mav;
	}
	
	@RequestMapping(value = "/addAccount.do" , method = { RequestMethod.GET, RequestMethod.POST}) // @RequestMapping 옵션값 공부.
	public ModelAndView addAccount(@RequestParam String year, @RequestParam String month
									, @RequestParam (required = false) String seqArray
									, HttpServletRequest req) {
		
		ModelAndView mav = new ModelAndView();
		
		// Jwt유횽성 검증로직.
		String token = cookieUtil.getJwtFromCookie(req);
		if (token.isBlank()) {
			mav.setViewName("redirect:/login/loginForm.do");
			return mav;
		}
		// Jwt 토큰으로 유효성음 검증.
		Claims claims = jwtUtil.validateToken(token);
		if (!claims.isEmpty()) {
			mav.setViewName("addAccount");
			mav.addObject("year", year);
			mav.addObject("month", month);
		}
		return mav;
	}
}