package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.util.CustomException;

@Service
public class ExcellService {
	private final String UPLOAD_DIR = "C:\\uploadTest\\";
	
	public void saveFile(MultipartFile file) throws IllegalStateException, IOException {
		// 파일을 서버에 저장
		File destinationFile = new File(UPLOAD_DIR + file.getOriginalFilename());
		
		destinationFile.getParentFile().mkdirs();
			file.transferTo(destinationFile);
		// 여기까지 서버에 업로드가 진행이 된다.
			
		// FileInputStream과 Workbook 객체는 사용 후 반드시 닫아야 합니다. try-with-resources 구문을 사용하면 자동으로 닫습니다.
		Workbook workbook = null;
		try (FileInputStream fis = new FileInputStream(destinationFile);) {
				// XSSFWorkbook은 .xlsx 형식의 파일만 지원한다.
				if (file.getOriginalFilename().endsWith(".xlsx")) {
					workbook = new XSSFWorkbook(fis);
				} else if (file.getOriginalFilename().endsWith(".xls")) {
					workbook = new HSSFWorkbook(fis);
				}
				// .csv는 ,로 이어붙인 텍스트형식의 파일이다. csv는 일단 제외하자
				//else if (file.getOriginalFilename().endsWith("csv")) {
				//}
				
				Sheet sheet = workbook.getSheetAt(0);
				
				for (Row row : sheet) {
					// 각 행의 데이터를 읽어오기
	                Cell accountDateCell = row.getCell(0); 	// 날짜 열
	                Cell contentCell = row.getCell(1); 		// 내용 열
	                Cell incomeCell = row.getCell(2); 		// 수입 열
	                Cell spendingCell = row.getCell(3); 	// 지출 열
	                Cell balanceCell = row.getCell(4); 		// 잔액 열
	
	                // 셀의 값을 가져오기
	                // 참조하는 값이 null경우 NullPointerEx발생!!
	                String accountDate = accountDateCell == null ? "9999-99-99" : accountDateCell.getStringCellValue();
	                String content = contentCell == null ? "N/A" : contentCell.getStringCellValue();					
	                String income = incomeCell == null ? "0" : incomeCell.getStringCellValue();
	                String spending = spendingCell == null ? "0" : spendingCell.getStringCellValue();
	                String balance = balanceCell == null ? "0" : balanceCell.getStringCellValue();
	          
	                // 데이터베이스에 저장하는 로직 추가
	                System.out.println("Value 1: " + accountDate + ", Value 2: " + content + "Value 3: " + income + "Value 4: " + spending + "Value 5: " + balance);
				}
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new CustomException("uploaded file is parsing NullPointer Error");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException("uploaded file is parsing excute Error!!");
		} finally {
			// workbook객체 닫아주기
			workbook.close();			
		}
		
	}
}
