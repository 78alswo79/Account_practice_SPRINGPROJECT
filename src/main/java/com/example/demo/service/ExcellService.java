package com.example.demo.service;

import java.io.ByteArrayOutputStream;
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
		
		// 디렉토리 존재 여부 확인 및 생성
	    if (!destinationFile.getParentFile().exists()) {
	        destinationFile.getParentFile().mkdirs();
	    }
	    
	    // 파일이 이미 존재하는지 확인
	    if (destinationFile.exists()) {
	        redirectAttributes.addFlashAttribute("resultMessage", "파일이 이미 존재합니다. 다시 확인 해주세요.");
	        return;
	    } else {
	        // 파일을 서버에 저장
	        file.transferTo(destinationFile);
	    }
			
			
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
					redirectAttributes.addFlashAttribute("resultMessage", "지정된 형식의 액셀 파일이 아닙니다.");
					return;
				}
			
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
	                
//					System.out.println(
//							"seq" + getSeq + "year: " + year + ", month: " + month + "days: " + days +/*"accountDateValue2" + accountDateValue2 +*/ "content: " + content + ", income: " + income + "spending: " + spending + "balance: " + balance);
	                
					test.setSeq(getSeq++);
					test.setYear(year);
	                test.setMonth(month);
	                test.setDays(days);
	                test.setContent(content);
	                test.setIncome(income);
	                test.setSpending(spending);
	                test.setBalance(balance);

	                resList.add(test);                
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
	
	public ByteArrayOutputStream exportToExcel(List<Test> getList) throws IOException {
		// Workbook 객체 생성
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(getList.get(0).getYear() + "년 " + getList.get(0).getMonth() + "월");
		
		// 헤더 생성
		Row rowHeader = sheet.createRow(0);
		for (int i = 0; i < getHeaderNameList.values().length; i++) {
			rowHeader.createCell(i).setCellValue(getHeaderNameList.values()[i].name());
		} 
		
		// 데이터 추가
        int rowNum = 1;
        for (Test test : getList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(test.getYear());    // 첫 번째 셀
            row.createCell(1).setCellValue(test.getMonth());   // 두 번째 셀
            row.createCell(2).setCellValue(test.getDays());    // 세 번째 셀
            row.createCell(3).setCellValue(test.getContent());  // 네 번째 셀
            row.createCell(4).setCellValue(test.getIncome());   // 다섯 번째 셀
            row.createCell(5).setCellValue(test.getSpending()); // 여섯 번째 셀
            row.createCell(6).setCellValue(test.getBalance());   // 일곱 번째 셀
        }
        
        // 엑셀 파일을 ByteArrayOutputStream에 작성
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream); // workbook 객체를 사용
        wb.close(); // 리소스 해제

        return outputStream;
	}
	
	// 상수 Enum활용.
	private enum getHeaderNameList {
		년, 월, 일, 내용, 수입, 지출, 잔액
	}
}
