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
	                Cell cell1 = row.getCell(0); // 첫 번째 열
	                Cell cell2 = row.getCell(1); // 두 번째 열
	
	                // 셀의 값을 가져오기
	                String value1 = cell1.getStringCellValue();
	                String value2 = cell2.getStringCellValue();	// 참조하는 값이 null경우 NullPointerEx발생!!
	
	                // 데이터베이스에 저장하는 로직 추가
	                System.out.println("Value 1: " + value1 + ", Value 2: " + value2);
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new CustomException("uploaded file is parsing excute Error!!");
		}
	}
}
