package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PageVO;
import com.example.demo.dto.Test;
import com.example.demo.mapper.TestMapper;

@Service
public class AccountService {
	
	@Autowired
	private TestMapper testMapper;
	
	public List<Integer> getDateYear() {
		List<Integer> showYearList = new ArrayList<>();
		
		LocalDate localDate = LocalDate.now();
		int startYear = localDate.getYear();
		int endYear   = localDate.getYear() + 9;
		
		showYearList.add(startYear);
		showYearList.add(endYear);
		
		return showYearList;
	}
	
	public List<Integer> getDateMonth() {
		List<Integer> showMonthList = new ArrayList<>();
		showMonthList.add(1);
		showMonthList.add(12);
		return showMonthList;
	}
	
	public Test setDateYearMonth(String year, String month) {
		Test test = new Test();
		
		String parseMonth = setParseMonth(month);

		test.setYear(year);
		test.setMonth(parseMonth);
		
		return test;
	}
	
	public List<Test> getMyAccountList(Test test, PageVO pageVO, String filterOption) {
		List<Test> resultList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("test", test);
		paramMap.put("pageVO", pageVO);
		paramMap.put("filterOption", filterOption);
		
		resultList = testMapper.getMyAccountList(paramMap);
		
		// 수입, 지출, 총액에 ","이슈
		if (resultList != null && resultList.size() > 0) {
			for (Test itemTest : resultList) {
				itemTest.setIncome(itemTest.getIncome().replace(",", ""));
				itemTest.setSpending(itemTest.getSpending().replace(",", ""));
				itemTest.setBalance(itemTest.getBalance().replace(",", ""));
			}
			return resultList;
		} else {
			return null;
		}

	}

	public List<Test> getTestList(Test test) {
		List<Test> getList = new ArrayList<Test>();
		// 공부하기 좋은. 반복 횟수는 모두 똑같다. 어떤 필드를 참조하든
		int seq = test.getSeq();

		// seq의  중복 발생 방지.
		if (seq > 0) {
			seq++;
		}
		
		for (int i = 0; i < test.getYear().split(",").length; i++) {
			String year = test.getYear().split(",")[i];
			String month = test.getMonth().split(",")[i];
			String days = test.getDays().split(",")[i];
			String content = test.getContent().split(",")[i];
			String income = test.getIncome().split(",")[i];
			String spending = test.getSpending().split(",")[i];
			String balance = test.getBalance().split(",")[i];
			
			// month String 가공.
			String reMonth = setParseMonth(month);
			getList.add(new Test(seq, year, reMonth, days, content, income, spending, balance));
			seq++;
		}
		return getList;
	}

	public Map<String, Object> getAccountList(List<Test> getList) {
		Map<String, Object> resMap = new HashMap<>();
		
		int insertCnt = 0;
		
		try {
			insertCnt = testMapper.insertAccountList(getList);
			// 인서트 성공 시, 메세지와 insert횟수를 반환.
			// 인서트 에러 시, 메세지와 insert횟수를 반환.
			resMap.put("insertCnt", insertCnt);
			if (insertCnt > 0) {
				resMap.put("returnMessage", "가계부 입력을 정상적으로 완료했습니다.");
				resMap.put("resCode", 200);
			} else {
				resMap.put("returnMessage", "가계부 입력이 실패했습니다.");
				resMap.put("resCode", 400);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		return resMap;
	}

	public int getSeq(Test entity) {
		int resSeq = 0;
		Integer seq = testMapper.getSeq(entity);
		if (seq == null) {
			seq = 0;
		} else {
			seq = seq;
		}
		resSeq = seq;
		
		return resSeq;
	}
	
	private String setParseMonth(String month) {
		String reStr = "";
		if (Integer.parseInt(month) < 10) {
			reStr = "0" + month;
		} else {
			reStr = month;
		}
		
		return reStr;
	}

	public List<Test> getUpdateMyAccountList(String[] getSeqArray, String year, String month) {
		List<Test> resultList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		String parseMonth = "";
		
		paramMap.put("getSeqArray", getSeqArray);
		paramMap.put("year", year);
		parseMonth = setParseMonth(month);
		paramMap.put("month", parseMonth);
		resultList = testMapper.getUpdateMyAccountList(paramMap);

		return resultList;
	}

	public List<Map<String, Object>> testList(Test test) {
		List<Map<String, Object>> resultMap;
		resultMap = testMapper.testList(test);

		return resultMap;
	}

	public int delete(String[] seqArr) {
		int deleteCnt = 0;
		deleteCnt = testMapper.delete(seqArr);
		return deleteCnt;
	}

	public int updateMyAccountList(List<Map<String, Object>> entity) {
		int updateCnt = 0;
		
		// 수입, 지출, 총액에 ","이슈.
		for (Map<String, Object> item : entity) {
			item.put("income", item.get("income").toString().replace(",", ""));
			item.put("spending", item.get("spending").toString().replace(",", ""));
			item.put("balance", item.get("balance").toString().replace(",", ""));
			updateCnt = updateCnt + testMapper.update(item);
		}

		return updateCnt;
	}

	public List<String> getFilterList(Test test) {
		List<String> resList = new ArrayList<String>();
		resList = testMapper.getFilterList(test);
		resList.add("전체");
		
		return resList;
	}

	public int getTotalListCnt(Test test) {
		int resCnt = 0;
		resCnt = testMapper.getTotalListCnt(test);
		return resCnt;
	}

	public List<Test> getFilteredList(Test test, String filterOption) {
		List<Test> list = new ArrayList<Test>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("test", test);
		map.put("filterOption", filterOption);
		
		list = testMapper.getFilteredList(map);
		
		return list;
	}
	
}
