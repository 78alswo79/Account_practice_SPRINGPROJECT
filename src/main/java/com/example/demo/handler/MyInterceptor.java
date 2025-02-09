package com.example.demo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class MyInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 요청 처리전에 실행할 로직.
		// 세션의 "user"속성을 제외한 속성을 초기화한다.
		sessionUserExcludeDel(request);
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 요청 처리후 실행할 로직
		setSessionAttribute(request);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		// 요청 완료후 실행할 로직
	}

	/**
	 * <p>"/addAccount.do", "/getDateMonth.do", "/getMyAccountList.do" 3개 요청에 대해<br>
	 * session의 "user"속성을 제외한 속성을 초기화한다.</p>
	 * @author kephas_LEEMINJAE
	 * @param HttpServletRequest
	 * @return void
	 * */
	private void sessionUserExcludeDel(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			var allSessionNames = session.getAttributeNames();
			while (allSessionNames.hasMoreElements()) {
				String attrName = allSessionNames.nextElement();
				if (!attrName.equalsIgnoreCase("user")) {
					session.removeAttribute(attrName);
				}
			}
		}
	}
	
	/**
	 * <p>인터셉트에 등록한 Url 매핑에 필요한 정보 세션에 세팅.</p>
	 * @author kephas_LEEMINJAE
	 * @param HttpServletRequest
	 * @return void
	 * */
	private void setSessionAttribute(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int endIndexNo = request.getRequestURL().lastIndexOf("/");
		
		switch (request.getRequestURI()) {
		
			case "/getDateMonth.do":
					session.setAttribute("redirectUrl", request.getRequestURL());
					session.setAttribute("param", "year=".concat(request.getParameter("year")));
				break;
			default:
					session.setAttribute("redirectUrl", request.getRequestURL());
					session.setAttribute("param", "year=".concat(request.getParameter("year")).concat("&month=").concat(request.getParameter("month")));
				break;
		}
	}
}
