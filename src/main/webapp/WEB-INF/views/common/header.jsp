<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="text-align: right;">
    <c:choose>
        <c:when test="${not empty sessionScope.user}">				<!--HttpSession에 저장된 속성은 자동 el표현이 가능하다. 명시적으로 스콥을 지정해주는게 좋을듯하다. 공부하기좋은-->
            <a href="./logout.do">로그아웃하기</a>
        </c:when>
        <c:otherwise>
            <a href="/login/loginForm.do">로그인하기</a>
        </c:otherwise>
    </c:choose>
</div>