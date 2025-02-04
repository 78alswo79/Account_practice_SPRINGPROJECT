<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="EUC-KR">
	<title>오류 발생</title>
	<link rel="stylesheet" href="/css/errorStyle.css"> <!-- CSS 파일 링크 -->
</head>
<body>
	<div class="error-container">
        <h1>${statusCode}</h1>
        <h2>페이지를 찾을 수 없습니다.</h2>
        <h3>${exception}</h3>
		<h3>${exceptionMessage}</h3>
		<p>요청하신 페이지가 존재하지 않거나, 이동된 것 같습니다.</p>
        <a href="/" class="home-button">홈으로 돌아가기</a>
    </div>
</body>
</html>