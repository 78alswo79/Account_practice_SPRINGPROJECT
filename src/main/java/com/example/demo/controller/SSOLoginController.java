package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class SSOLoginController {

	@GetMapping("/loginForm.do")
	public String login() {
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("login");
		return "loginForm";
	}
}
