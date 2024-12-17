<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>월을 선택해주세요!!</h1>

	<c:forEach begin="${showMonthList[0]}" end="${showMonthList[1]}" var="i">
		<li><a href="./getMyAccountList.do?year=${test.year}&month=${i}">${i}</a></li>
	</c:forEach>
	
</body>
</html>