package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.Test;
import com.example.demo.mapper.TestMapper;
import com.example.demo.util.CustomException;

@Service
public class ExcellService {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private TestMapper testMapper;
	private final String UPLOAD_DIR = "C:\\uploadTest\\";
	
	public void saveFile(MultipartFile file, RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		List<Test> resList = new ArrayList<>();
		// 파일을 서버에 저장
		File destinationFile = new File(UPLOAD_DIR + file.getOriginalFilename());
		
		destinationFile.getParentFile().mkdirs();
			file.transferTo(destinationFile);
			
		// FileInputStream과 Workbook 객체는 사용 후 반드시 닫아야 합니다. try-with-resources 구문을 사용하면 자동으로 닫습니다.
		Workbook workbook = null;
		try (FileInputStream fis = new FileInputStream(destinationFile);) {

				// XSSFWorkbook은 .xlsx 형식의 파일만 지원한다.
				if (file.getOriginalFilename().endsWith(".xlsx")) {
					workbook = new XSSFWorkbook(fis);
				} else if (file.getOriginalFilename().endsWith(".xls")) {
					workbook = new HSSFWorkbook(fis);
				} else {
					// .csv는 ,로 이어붙인 텍스트형식의 파일이다. csv는 일단 제외하자.
					// .csv파일 및 이외의 데이터
					redirectAttributes.addAttribute("resultMessage", "지정된 형식의 액셀 파일이 아닙니다.");
					return;
				}
			
				System.out.println("이 아래는 진행이 디냐??");
				Sheet sheet = workbook.getSheetAt(0);
				DataFormatter dataFormatter = new DataFormatter(); // 데이터 포맷터 생성
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				
				int getSeq = accountService.getSeq();
				for(int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
					Test test = new Test();
					Row row = sheet.getRow(rowIndex);
					
					// 각 행의 데이터를 읽어오기
	                Cell accountDateCell = row.getCell(0); 	// 날짜 열
	                Cell contentCell = row.getCell(1); 		// 내용 열
	                Cell incomeCell = row.getCell(2); 		// 수입 열
	                Cell spendingCell = row.getCell(3); 	// 지출 열
	                Cell balanceCell = row.getCell(4); 		// 잔액 열
	                
	                Date date = accountDateCell.getDateCellValue();
	                String accountDateValue = format.format(date);
	                // 셀의 값을 가져오기
	                // 참조하는 값이 null경우 NullPointerEx발생!!
	                
	                String[] accountDate = accountDateValue.toString().split("-");
	                String days = accountDate[2];
	                String month = accountDate[1].substring(0);		//맨 뒤 "월"은 빼주기
	                String year = accountDate[0];
	                String content = contentCell.getStringCellValue();
	                
	                String income = (int)incomeCell.getNumericCellValue() + "";
	                String spending = (int)spendingCell.getNumericCellValue() + ""; 
	                String balance = (int)balanceCell.getNumericCellValue() + "";
	                
					System.out.println(
							"seq" + getSeq + "year: " + year + ", month: " + month + "days: " + days +/*"accountDateValue2" + accountDateValue2 +*/ "content: " + content + ", income: " + income + "spending: " + spending + "balance: " + balance);
	                
					test.setSeq(getSeq++);
					test.setYear(year);
	                test.setMonth(month);
	                test.setDays(days);
	                test.setContent(content);
	                test.setIncome(income);
	                test.setSpending(spending);
	                test.setBalance(balance);
//	                resList = accountService.getTestList(test);
	                resList.add(test);
	                
	                //TODO 이외에 액셀 다운로드 액셀 서비스분리하기
	                // 데이터베이스에 저장하는 로직 추가
//	                System.out.println("Value 1: " + accountDate + ", Value 2: " + content + "Value 3: " + income + "Value 4: " + spending + "Value 5: " + balance);
				}
				testMapper.insertExcelList(resList);
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new CustomException("uploaded file is parsing NullPointer Error");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("uploaded file is parsing excute Error!!");
		} finally {
			if (workbook != null) {				
				// workbook객체 닫아주기
				workbook.close();			
			}
		}
	}
	
	/**
	 * <p>액셀 CellType에 따라 값 세팅</p>
	 * */
	private void convertCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING: 
			
			break;
		case NUMERIC:
			break;
		case BOOLEAN:
			break;
		case FORMULA:
			break;
		case BLANK:
		 	break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + cell.getCellType());
		}
	}
	
//	/**
//	 * <p> **포멧의 year데이터를 ****자로 변환한다. </p>
//	 * */
//	private String getYearStrConvert(String rowYear) {
//		String result = "";
//		if (99 >= Integer.parseInt(rowYear)) {
//			result = "20".concat(rowYear);
//		} else {
//			result = "21".concat(rowYear);
//		}
//		return result;
//	}
}
