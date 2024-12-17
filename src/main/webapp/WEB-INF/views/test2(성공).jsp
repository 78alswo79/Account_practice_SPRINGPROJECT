<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>재무 현황</title>
    <style>
         body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .form-row {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }
        .form-row label {
            margin-right: 10px;
            white-space: nowrap;
        }
        .form-row input {
            margin-right: 20px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .button {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

	<h2>재무 현황</h2>
	파람1 : ${param1}
	파람2 : ${param2}
	<!-- 데이터 입력을 위한 폼 -->
	<%-- <c:choose>
		<c:when test="${gubun eq 'C'}">
			<form id="accountForm" action="insertAccout.do" method="post">
			    <button type="submit" id="submit"		class="button" >입력</button>
			    <button type="button" id="addButton" 	class="button">추가하기</button>
			    <div class="form-row">
			        <label for="year">년도:</label>
			        <input type="text" id="year" name="year" value="<c:out value='${year}'/>" readonly>
			        <label for="month">월:</label>
			        <input type="text" id="month" name="month" value="<c:out value='${month}'/>" readonly>
			        <label for="days">일:</label>
			        <input type="text" id="days" name="days" required>
			        <label for="content">내용:</label>
			        <input type="text" id="content" name="content" required>
			        <label for="income">수입:</label>
			        <input type="text" id="income" name="income" required value="0">
			        <label for="spending">지출:</label>
			        <input type="text" id="spending" name="spending" required value="0">
			        <label for="balance">잔액:</label>
			        <input type="text" id="balance" name="balance" required value="0">
			    </div>
			</form>
		</c:when>
		<c:when test="${gubun eq 'U'}">
			<form id="accountForm" action="updateAccout.do" method="post">
			    <button type="submit" id="submit"		class="button" >수정하기</button>
			   	<!--  <button type="button" id="addButton" 	class="button">추가하기</button> -->
			   	<c:forEach var="updateList" items="${updateList}">
				    <div class="form-row">
				        <label for="year">년도:</label>
				        <input type="text" id="year" name="year" value="<c:out value='${updateList.year}'/>" readonly>
				        <label for="month">월:</label>
				        <input type="text" id="month" name="month" value="<c:out value='${updateList.month}'/>" readonly>
				        <label for="days">일:</label>
				        <input type="text" id="days" name="days" required value="<c:out value='${updateList.days}'/>">
				        <label for="content">내용:</label>
				        <input type="text" id="content" name="content" required value="<c:out value='${updateList.content}'/>">
				        <label for="income">수입:</label>
				        <input type="text" id="income" name="income" required value="<c:out value='${updateList.income}'/>" >
				        <label for="spending">지출:</label>
				        <input type="text" id="spending" name="spending" required value="<c:out value='${updateList.spending}'/>">
				        <label for="balance">잔액:</label>
				        <input type="text" id="balance" name="balance" required value="<c:out value='${updateList.balance}'/>">
				    </div>
			   	</c:forEach>
			</form>
		</c:when>
	</c:choose> --%>

	<script type="text/javascript">
	
	</script>
</body>
</html>