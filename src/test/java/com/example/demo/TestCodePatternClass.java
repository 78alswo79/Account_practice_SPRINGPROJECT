package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest					// 테스트용 애플리케이션 컨텍스트 생성
@AutoConfigureMockMvc			// MockMvc 생성 및 자동 구성
public class TestCodePatternClass {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;			// 자신의 스프링부트의 컨트롤러, 빈, 핸들러 매핑, 뷰리졸버 등 정보가 담김.
	
	@BeforeEach
	public void mockMvcSetup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();		// MockMvc 객체를 만들어주는 구문임.
	}
	
//	@Test
//	void contextLoads() {
//	}
	
	@DisplayName("quiz() : GET /quiz.do?code=1이면 응답 코드는 201, 응답 본문은 Created!를 리턴한다.")
	@Test
	public void getQuiz1() throws Exception {
		// given
		String url = "/quiz.do";
		// when
		final ResultActions result = mockMvc.perform(get(url).param("code", "1"));
		// then
		result
		.andExpect(status().isCreated())
		.andExpect(content().string("Created!!"));
//			.andExpect(status().isCreated())
//			.andExpect(content().string("Created!!"));
//			.andExpect(model().attributeExists("showYearList"));
	}
	
	
	@DisplayName("quiz() : GET /quiz.do?code=2이면 응답 코드는 400, 응답 본문은 Bad Request!!를 리턴한다.")
	@Test
	public void getQuiz2() throws Exception {
		// given
		String url = "/quiz.do";
		// when
		final ResultActions result = mockMvc.perform(get(url).param("code", "2"));
		// then
		result
		.andExpect(status().isBadRequest())
		.andExpect(content().string("Bad Request!!"));
//			.andExpect(status().isCreated())
//			.andExpect(content().string("Created!!"));
//			.andExpect(model().attributeExists("showYearList"));
	}
	
	@DisplayName("quiz() : POST /quiz.do?code=1이면 응답 코드는 403, 응답 본문은 Forbidden!!를 리턴한다.")
	@Test
	public void postQuiz1() throws Exception {
		// given
		String url = "/quiz.do";
		// when
		final ResultActions result = mockMvc.perform(post(url)
												.contentType(MediaType.APPLICATION_JSON)
												.content(objectMapper.writeValueAsString(new Code(1))));
		// then
		result
		.andExpect(status().isForbidden())
		.andExpect(content().string("Forbidden!!"));
//			.andExpect(status().isCreated())
//			.andExpect(content().string("Created!!"));
//			.andExpect(model().attributeExists("showYearList"));
	}
	
	@DisplayName("quiz() : POST /quiz.do?code=13이면 응답 코드는 200, 응답 본문은 Ok를 리턴한다.")
	@Test
	public void postQuiz2() throws Exception {
		// given
		String url = "/quiz.do";
		// when
		final ResultActions result = mockMvc.perform(post(url)
												.contentType(MediaType.APPLICATION_JSON)
												.content(objectMapper.writeValueAsString(new Code(13))));
		// then
		result
		.andExpect(status().isOk())
		.andExpect(content().string("Ok"));
//			.andExpect(status().isCreated())
//			.andExpect(content().string("Created!!"));
//			.andExpect(model().attributeExists("showYearList"));
	}
	
	
	
	
	
	
	public class Code {
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
