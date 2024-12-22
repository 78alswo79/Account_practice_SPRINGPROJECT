package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.PageVO;
import com.example.demo.dto.Test;
import com.example.demo.service.AccountService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
public class IndexController {
	
	// application.properties에 정의된 값을 가져올 때. 공부하기 좋은.
	@Value("${spring.datasource.url}")
	private String datasourceUrl;
	
	@Autowired
	private AccountService accoutService;

	@GetMapping("/")
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
			, @RequestParam(required = false/* , defaultValue = "15" */) Integer perPage) {
		List<Test> testList = new ArrayList<>();
		ModelAndView mav = new ModelAndView();
		PageVO pageVO;
		
		mav.setViewName("getMyAccountList");
		
		Test test = new Test();
		
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
		
		if (!testList.isEmpty()) {			
			// 이 화면에는 가계부를 적을 수 있는 기능이있다.
			mav.addObject("testList", testList);
			mav.addObject("getContentList", getContentList);
			mav.addObject("pageVO", pageVO);
		} else {
			mav.addObject("testList", "");
		}
		
		mav.addObject("year", year);
		mav.addObject("month", month);	

		return mav;
	}
	
	
	
	
	
	@GetMapping(value="/getFilteredList.do")
	public ResponseEntity<Map<String, Object>> getFilteredList(@RequestParam String year, @RequestParam String month
			, @RequestParam(required = false, defaultValue = "0") int currentPage
			, @RequestParam(required = false/* , defaultValue = "" */) String filterOption
			, @RequestParam(required = false/* , defaultValue = "15" */) Integer perPage) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Test> testList = new ArrayList<>();
		PageVO pageVO;
		
		if (year.isBlank() || month.isBlank()) {
			throw new Exception("year, month NULL!!!");
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
		
		if (testList == null && testList.size() == 0) {
			String resCode = "";
			map.put("resCode", 500);
		} else {
			map.put("resCode", 200);
			map.put("testList", testList);
		}
		return ResponseEntity.ok(map);
	}
	

	@RequestMapping(value = "/addAccount.do" , method = { RequestMethod.GET, RequestMethod.POST}) // @RequestMapping 옵션값 공부.
	public ModelAndView addAccount(@RequestParam String year, @RequestParam String month
									, @RequestParam String gubun, @RequestParam (required = false) String seqArray) {
		
		ModelAndView mav = new ModelAndView();
		if (gubun == null) {
			// 적절한 예외 처리
		    throw new IllegalArgumentException("gubun, year, month or AccountService cannot be null");
		}
		
		// C, 인서트 작업.
		if (gubun.equalsIgnoreCase("C")) {
			mav.setViewName("addAccount");
			mav.addObject("year", year);
			mav.addObject("month", month);
			
			mav.addObject("gubun", gubun);
		}
		return mav;
	}
	
	@PostMapping("insertAccout.do")
	public ResponseEntity<Map<String, Object>> postMethodName(@RequestBody Test entity, @ModelAttribute("test") Test test) {
		
		// 응답을 JSON 형태로 반환
        Map<String, Object> response = new HashMap<>();
		
		if (entity == null || test == null || accoutService == null) {
		    // 적절한 예외 처리
		    throw new IllegalArgumentException("Entity, Test, or AccountService cannot be null");
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
        return ResponseEntity.ok(response);
	}
	
	// 공부하기 좋은. 프론트에서 [{}] json형태로 통신하면 서버에서는 List<Map<>>으로 받을 수 있구나
	@PostMapping("updateAccout.do")
	public ResponseEntity<Map<String, Object>> updateAccout(@RequestBody List<Map<String, Object>> entity) {
		Map<String, Object> response = new HashMap<String, Object>();
		
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
	public ResponseEntity<Map<String, Object>> deleteAccount(@RequestBody String entity) {
		Map<String, Object> response = new HashMap<String, Object>();
		
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
		
		return ResponseEntity.ok(response);
	}
	
	//엑셀로 내려받기, 공부하기 좋은
	@GetMapping("/excelDown.do")
	public ResponseEntity<byte[]> excelDown (HttpServletResponse response
			, @RequestParam String year
			, @RequestParam String month) throws IOException{

		Test test = new Test();
		Workbook workBook = new XSSFWorkbook();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] bytes = outputStream.toByteArray();
		HttpHeaders headers = new HttpHeaders();
		
		try {
			//response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			//response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
			
			if (year == null || month == null) {
				// 적절한 예외 처리
				throw new IllegalArgumentException("year, month or AccountService cannot be null");
			}
			
			test.setYear(year);
			test.setMonth(month);
			List<Test> getList = accoutService.getFilteredList(test, "");
			
			System.out.println("다운 받을 액셀 리스트는??" + getList);
			// 다운로드 액셀 구성
			outputStream = accoutService.exportToExcel(getList);
			bytes = outputStream.toByteArray(); 				// 바이트 배열 가져오기
			headers.add("Content-Disposition", "attachment; filename=test.xlsx");
			
		} catch (IOException e) {
			e.getStackTrace();
			e.getMessage();
		} catch (Exception e) {
			e.getStackTrace();
			e.getMessage();
		} 
		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
	}
	
	//TODO 액셀 업로드 구현.
}
