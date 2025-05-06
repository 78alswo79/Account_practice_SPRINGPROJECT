package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCodePatternController {

	@GetMapping("/quiz.do")
	public ResponseEntity<String> quiz(@RequestParam("code") int code) {
		switch (code) {
		case 1:
			return ResponseEntity.created(null).body("Created!!");
		case 2:
			return ResponseEntity.badRequest().body("Bad Request!!");	
		default:
			return ResponseEntity.ok().body("Ok");
		}
	}
	
	@PostMapping("/quiz.do")
	public ResponseEntity<String> quiz2(@RequestBody Code code) {
//		switch (code) {
//		case 1:
//			return ResponseEntity.status(403).body("Forbidden!!");
//		default:
//			return ResponseEntity.ok().body("Ok");
//		}
		
		// 자바 10/11에서는 이렇게 레코드의 필드에 직접 접근해야 해.
	    if (code.getValue() == 1) { // <-- 레코드의 value() 메소드로 값 접근
	        return ResponseEntity.status(403).body("Forbidden!!");
	    } else {
	        return ResponseEntity.ok().body("Ok");
	    }
	}
//	recode Code(int value) {}
	
	public static class Code {
	    private int value; // 필드 정의

	    // 기본 생성자 (Jackson 등에서 JSON to Object 변환 시 필요)
	    public Code() {
	    }

	    // 필드 값을 받는 생성자 (선택 사항이지만 데이터를 만들 때 편리)
	    public Code(int value) {
	        this.value = value;
	    }

	    // 필드 값을 가져오는 Getter 메소드 (필수)
	    public int getValue() {
	        return value;
	    }

	    // 필드 값을 설정하는 Setter 메소드 (필요하다면 추가. 불변 객체면 없어도 됨)
	    // public void setValue(int value) {
	    //     this.value = value;
	    // }

	    // equals, hashCode, toString 메소드는 필요에 따라 직접 구현하거나 IDE 자동 생성 기능 사용
	    // 레코드는 이걸 자동으로 해주지만, 일반 클래스는 직접 해야 함.
	    // @Override
	    // public boolean equals(Object o) { ... }
	    // @Override
	    // public int hashCode() { ... }
	    // @Override
	    // public String toString() { ... }
	}
}
