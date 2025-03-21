package com.example.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
		try {
			FileInputStream fis = new FileInputStream(destinationFile);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			// TODO 이어서 진행하기.
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new CustomException("uploaded file is parsing excute Error!!");
		}
	}
}
