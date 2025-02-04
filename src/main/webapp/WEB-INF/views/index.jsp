<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>나만의 가계부를 생성하거나 가져와 보세요!!</h1>
	
	<h2>Numbers from ${showYearList[0]} to ${showYearList[1]}</h1>
	<!-- 공통 헤더 포함 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp" />						<%-- page지정 방식은 뷰리졸버설정을 고려하지 않는다. 공부하기 좋은.--%>
	
	<%-- 시작/끝 값을 이용해 for문 돌리기.  --%>
	<li><a href="./getDateMonth.do?year=2024">2024</a></li> 임시 2024데이터
     <c:forEach begin="${showYearList[0]}" end="${showYearList[1]}" var="i">
         <li><a href="./getDateMonth.do?year=${i}">${i}</a></li>
     </c:forEach>
	 
</body>
<script>
    // 쿠키를 가져오는 함수
    function getCookie(name) {
    	 const value = `; ${document.cookie}`;
    	    const parts = value.split(`; ${name}=`);
    	    if (parts.length === 2) return parts.pop().split(';').shift();
    }

    // JWT 쿠키 확인
    const jwt = getCookie('jwt');
    if (jwt) {
        console.log('JWT:', jwt);
        // JWT를 사용하여 추가적인 작업 수행
    } else {
        console.log('JWT가 존재하지 않습니다.');
    }
</script>
</html>