<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="text-align: right;">
    <c:choose>
        <c:when test="${not empty user}">
            <a href="./logout.do">로그아웃하기</a>
        </c:when>
        <c:otherwise>
            <a href="/login/loginForm.do">로그인하기</a>
        </c:otherwise>
    </c:choose>
</div>