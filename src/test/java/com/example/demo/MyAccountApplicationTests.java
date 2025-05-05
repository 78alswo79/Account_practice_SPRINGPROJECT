package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

@SpringBootTest					// 테스트용 애플리케이션 컨텍스트 생성
@AutoConfigureMockMvc			// MockMvc 생성 및 자동 구성
class MyAccountApplicationTests {

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;			// 자신의 스프링부트의 컨트롤러, 빈, 핸들러 매핑, 뷰리졸버 등 정보가 담김.
	
	@BeforeEach
	public void mockMvcSetup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();		// MockMvc 객체를 만들어주는 구문임.
	}
	
//	@Test
//	void contextLoads() {
//	}
	
	@DisplayName("BaseController의 /index.do에 요청한다.")
	@Test
	public void httpIndex() throws Exception {
		// given
		String url = "/index.do";
		// when
		final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));
		// then
		result
			.andExpect(status().isOk())
			.andExpect(view().name("/loginForm"));
//			.andExpect(model().attributeExists("showYearList"));
	}

}
