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
	<h1>������ ����θ� �����ϰų� ������ ������!!</h1>
	
	<h2>Numbers from ${showYearList[0]} to ${showYearList[1]}</h1>
	<%-- ����/�� ���� �̿��� for�� ������.  --%>
     <c:forEach begin="${showYearList[0]}" end="${showYearList[1]}" var="i">
         <li><a href="./getDateMonth.do?year=${i}">${i}</a></li>
     </c:forEach>
</body>
</html>