<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>월을 선택해 주세요.</title>
</head>
<body>
	<h1>월을 입력해 주세요!</h1>
	<!-- 공통 헤더 포함 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />

	<c:forEach begin="${showMonthList[0]}" end="${showMonthList[1]}" var="i">
		<li><a href="./getMyAccountList.do?year=${test.year}&month=${i}">${i}</a></li>
	</c:forEach>
	
</body>
</html>