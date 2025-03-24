package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.PageVO;
import com.example.demo.dto.Test;
import com.example.demo.service.AccountService;
import com.example.demo.service.ExcellService;
import com.example.demo.util.CookieUtil;
import com.example.demo.util.CustomException;
import com.example.demo.util.JwtUtilClass;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
public class APIController {
	
	@Autowired
	private AccountService accoutService;
	@Autowired
	JwtUtilClass jwtUtil;
	@Autowired
	CookieUtil cookieUtil;
	@Autowired
	ExcellService excellService;
	
	// 임시. 사용자 데이터
	// map 선언과 동시에 초기화. 공부하기 좋은.
	private Map<String, Object> userTest = new HashMap<>() {{
		put("user1", "1234");
		put("user2", "1234");
	}}; 
	
	// 로그인 엔드포인트
	@PostMapping("/loginProcess.do")
	// 공부하기좋은. @RequestBody(Map<String, Object>)로 받을 경우에는 프론트에서 JSON형식으로. 
	// 공부하기호은. @RequestParam으로 받을 경우엔, 각각 Stringd로 받는다.
	public /*Map<String, Object>*/ void login(@RequestParam String username, @RequestParam String password
			, HttpServletRequest request
			, HttpServletResponse resP
			, HttpSession session) throws IOException {
		if (username.isBlank() || password.isBlank()) {
			throw new CustomException("user is not NUll!!!");
		}
		
		// 사용자 인증 로직
		if (userTest.containsKey(username) && userTest.get(username).equals(password)) {
			String token = jwtUtil.generateToken(username);
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("token", token);
			
			if (!token.isBlank()) {
				resP.addCookie(cookieUtil.createCookies(token));
				
				accoutService.handleRedirect(request, resP);
				
				// user 세션 여기에서 첫 생성.
				session.setAttribute("user", username);
			}
            //return response;
		} else {
			resP.sendRedirect("/login/loginForm.do");
		}
	}
	
	@GetMapping("/logoutProcess.do")
	public void login(HttpServletRequest request, HttpServletResponse response
			, HttpSession session) throws IOException {

		cookieUtil.removeJwtCookie(request, response);
		response.sendRedirect("/login/loginForm.do");
	}
	
	@GetMapping(value="/getFilteredList.do")
	public ResponseEntity<Map<String, Object>> getFilteredList(@RequestParam String year, @RequestParam String month
			, @RequestParam(required = false, defaultValue = "0") int currentPage
			, @RequestParam(required = false/* , defaultValue = "" */) String filterOption
			, @RequestParam(required = false/* , defaultValue = "15" */) Integer perPage
			, HttpServletRequest req
			, HttpServletResponse res) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Test> testList = new ArrayList<>();
		PageVO pageVO;
		ModelAndView mav = new ModelAndView();
		
		String token = cookieUtil.getJwtFromCookie(req);
		if (token.isBlank()) { //token.isBlank()  currentPage == 0
			map.put("responseUrl", "/login/loginForm.do");
			ResponseEntity.ok(map);
		}
		// Jwt 토큰으로 유효성음 검증.
		Claims claims = jwtUtil.validateToken(token);
		if (!claims.isEmpty()) {
			if (year.isBlank() || month.isBlank()) {
				throw new CustomException("year, month NULL!!!");
			}
			
			if (currentPage == 0) {
				currentPage = 1;
			}
			
			if (filterOption == null) {
				filterOption = "";
			}
			
			if (perPage == null) {
				perPage = 15;
			}
			
			Test test = new Test();	
			test = accoutService.setDateYearMonth(year, month);
			
			int totalListCnt = accoutService.getTotalListCnt(test);
			pageVO = new PageVO(currentPage, perPage, totalListCnt);
			//화면에 쁘려준다.
			testList = accoutService.getMyAccountList(test, pageVO, filterOption);
			
			if (testList == null) {
				String resCode = "";
				map.put("resCode", 500);
			} else {
				map.put("resCode", 200);
				map.put("testList", testList);
			}
		}
		
		return ResponseEntity.ok(map);
	}
	
	
	@PostMapping("insertAccout.do")
	public ResponseEntity<Map<String, Object>> postMethodName(@RequestBody Test entity
			, @ModelAttribute("test") Test test
			, HttpServletRequest req
			, HttpServletResponse res) throws IOException{
		
		// 응답을 JSON 형태로 반환
        Map<String, Object> response = new HashMap<>();
        String token = cookieUtil.getJwtFromCookie(req);
		if (token.isBlank()) { //token.isBlank()  currentPage == 0
			response.put("responseUrl", "/login/loginForm.do");
			return ResponseEntity.ok(response);
		}
		// Jwt 토큰으로 유효성음 검증.
		Claims claims = jwtUtil.validateToken(token);
		if (!claims.isEmpty()) {
			
			if (entity == null || test == null || accoutService == null) {
				// 적절한 예외 처리
				throw new CustomException("Entity, Test, or AccountService cannot be null");
			}	
			int getSeq = accoutService.getSeq(entity);
			entity.setSeq(getSeq);
			
			List<Test> getList = accoutService.getTestList(entity);
			Map<String, Object> resMap = accoutService.getAccountList(getList);
			
			// Map<?,?> 객체의 유효성을 판단을 할 때는, value판단보다는 key가 null인지 아닌지 판단하는게 좋을듯하다. 공부하기 좋은.
			String returnMessage = (String) resMap.get("returnMessage");
			int resCode = (int) resMap.get("resCode");
			if (returnMessage != null && !getList.isEmpty()) {
				response.put("returnMessage", returnMessage);
				response.put("resCode", resCode);
				response.put("testList", getList);
				response.put("year", getList.get(0).getYear());
				response.put("month", getList.get(0).getMonth());
			}
		}
		
        return ResponseEntity.ok(response);
	}
	
	// 공부하기 좋은. 프론트에서 [{}] json형태로 통신하면 서버에서는 List<Map<>>으로 받을 수 있구나
	@PostMapping("updateAccout.do")
	public ResponseEntity<Map<String, Object>> updateAccout(@RequestBody List<Map<String, Object>> entity
			, HttpServletRequest req) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		String token = cookieUtil.getJwtFromCookie(req);
		if (token.isBlank()) { //token.isBlank()  currentPage == 0
			response.put("responseUrl", "/login/loginForm.do");
			return ResponseEntity.ok(response);
		}
		// Jwt 토큰으로 유효성음 검증.
		Claims claims = jwtUtil.validateToken(token);
		if (!claims.isEmpty()) {
			if (entity.isEmpty()) {			
				response.put("resMessage", "업데이트 할 entity가 없습니다");
				return ResponseEntity.ok(response);
			} 
			
			int resCnt = accoutService.updateMyAccountList(entity);
			
			// 업데이트 할 행수와 같다.
			if (resCnt == entity.size()) {
				System.out.println("뿌우");
				response.put("resCode", "200");
				response.put("resMessage", "업데이트를 성공적으로 완수했습니다.");
			} else {
				response.put("resCode", "500");
				response.put("resMessage", "업데이트를 성공적으로 완료하지 못했습니다.");
			}
		}
		return ResponseEntity.ok(response);
	}
	
	// Jackson 라이브러리 연습 컨트롤러
//	@PostMapping("updateAccout.do")
//	public ResponseEntity<Map<String, Object>> updateAccout(@RequestBody List<Test> entity) {
//		Map<String, Object> response = new HashMap<String, Object>();
//		
//		if (entity.isEmpty()) {			
//			response.put("resMessage", "업데이트 할 entity가 없습니다");
//			return ResponseEntity.ok(response);
//		} 
//		
//		System.out.println("업데이트 할 entity는 >>>>>>" + entity);
//		int resCnt = accoutService.updateMyAccountList(entity);
//		
//		// 업데이트 할 행수와 같다.
//		if (resCnt == entity.size()) {
//			System.out.println("뿌우");
//			response.put("resCode", "200");
//			response.put("resMessage", "업데이트를 성공적으로 완수했습니다.");
//		} else {
//			response.put("resCode", "500");
//			response.put("resMessage", "업데이트를 성공적으로 완료하지 못했습니다.");
//		}
//		
//		return ResponseEntity.ok(response);
//	}
	
	// fetchAPI JSP리턴 컨트롤러 연습.
//	@PostMapping("updateAccout.do")
//	public ModelAndView updateAccout(@RequestBody String entity) {
//		ModelAndView mav = new ModelAndView();
//		
//		mav.setViewName("test2");
//		mav.addObject("param1", 1);
//		mav.addObject("param2", 2);
//		
//		return mav;
//	}
	

	@PostMapping("deleteAccount.do")
	public ResponseEntity<Map<String, Object>> deleteAccount(@RequestBody String entity
			, HttpServletRequest req) {
		Map<String, Object> response = new HashMap<String, Object>();
		
		String token = cookieUtil.getJwtFromCookie(req);
		if (token.isBlank()) { //token.isBlank()  currentPage == 0
			response.put("responseUrl", "/login/loginForm.do");
			return ResponseEntity.ok(response);
		}
		// Jwt 토큰으로 유효성음 검증.
		Claims claims = jwtUtil.validateToken(token);
		if (!claims.isEmpty()) {
			String[] seqArr = entity.split(","); 
			System.out.println("시이버 "+  entity.split(","));
			String resStr = "";
			
			int deleteCnt = accoutService.delete(seqArr);
			if (deleteCnt > 0) {
				resStr = "삭제 성공했습니다.";
				response.put("resMessage", resStr);
				response.put("resCode", "200");
			} else {
				resStr = "삭제 실패했습니다.";
				response.put("resMessage", resStr);
				response.put("resCode", "500");
			}
		}
		
		return ResponseEntity.ok(response);
	}
	
	//엑셀로 내려받기, 공부하기 좋은
	@GetMapping("/excelDown.do")
	public ResponseEntity<?> excelDown (@RequestParam String year		// ResponseEntity<?> 경우, 안에 객체를 느슨하게 사용할 수 있다.
			, @RequestParam String month
			, HttpServletRequest req
			, HttpServletResponse res) throws IOException{

		Test test = new Test();
		Workbook workBook = new XSSFWorkbook();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] bytes = outputStream.toByteArray();
		HttpHeaders headers = new HttpHeaders();
		
		try {
			//response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			//response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
			String token = cookieUtil.getJwtFromCookie(req);
			if (token.isBlank()) { //token.isBlank()  currentPage == 0
				Map<String, String> responseMap = new HashMap<>();
				responseMap.put("responseUrl", "/login/loginForm.do");
				res.sendRedirect("/login/loginForm.do");
				return null;
			}
			// Jwt 토큰으로 유효성음 검증.
			Claims claims = jwtUtil.validateToken(token);
			if (!claims.isEmpty()) {
				if (year == null || month == null) {
					// 적절한 예외 처리
					throw new CustomException("year, month or AccountService cannot be null");
				}
				
				test.setYear(year);
				test.setMonth(month);
				List<Test> getList = accoutService.getFilteredList(test, "");
				
				System.out.println("다운 받을 액셀 리스트는??" + getList);
				// 다운로드 액셀 구성
				outputStream = accoutService.exportToExcel(getList);
				bytes = outputStream.toByteArray(); 				// 바이트 배열 가져오기
				headers.add("Content-Disposition", "attachment; filename=test.xlsx");
			}
		} catch (IOException e) {
			e.getStackTrace();
			e.getMessage();
		} catch (Exception e) {
			e.getStackTrace();
			e.getMessage();
		} 
		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
	}
	
	//TODO 액셀 업로드 구현. 시험적으로. 
	@PostMapping("/uploadExcelFile")
	public String uploadExcelFile(MultipartFile file, ModelAndView mav) throws IOException {
		// TODO 이어서
		String result = "";
		
		try {
			excellService.saveFile(file);
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new CustomException("uploaded file is parsing NullPointer Error");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("fileUpload is failure!!");
		}
		
		
		// TODO getMyList로 리다이렉트처리
		// TODO 업로드 성곻했으면, 메시지하나 뿌려주기.
		return result;
		
	}
}
