<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <!-- 공부하기 좋은. 스프링부트 정적파일의 기본 경로는 src/main/resources/static이다.  -->
   	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
        <form class="login-form" action="/api/loginProcess.do" method="post">
            <h2>로그인</h2>
            <div class="input-group">
                <label for="username">사용자 이름</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="input-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">로그인</button>
            <p class="message">계정이 없으신가요? <a href="#">회원가입</a></p>
        </form>
    </div>
</body>
<% String errorMessage = request.getParameter("error"); %>
<% if (errorMessage != null) { %>
    <div class="error-message"><%= errorMessage %></div>
<% } %>
</html>