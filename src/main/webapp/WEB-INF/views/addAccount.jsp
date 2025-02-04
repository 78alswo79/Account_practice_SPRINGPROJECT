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
	<!-- 데이터 입력을 위한 폼 -->
	<c:if test="${gubun eq 'C'}">
		<form id="accountForm" action="/api/insertAccout.do" method="post">
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
	</c:if>
	<div id="testJSPAppend">
	</div>

	<script type="text/javascript">
	const modelYear = '<c:out value='${year}' />';
	const modelMonth = '<c:out value='${month}' />';
	const gubun = '<c:out value='${gubun}'/>';
    
    document.addEventListener('DOMContentLoaded', e => {
    	
    	if (gubun === 'C') {
	    	// 가계부 입력용 스크립트
	    	const addButton = document.getElementById('addButton');
	    	const accountForm = document.getElementById('accountForm');
	    	const submitBtn = document.getElementById('submit');
	    	const inputDays = document.getElementsByName('days');
	    	
	    	// javascript input추가하는 방법 깔끔하게,  공부하기 좋은.
	    	addButton.addEventListener('click', e => {
	    		const formRow = document.getElementsByClassName('form-row');
	    		const newDiv = document.createElement('div');
	    		newDiv.className = 'form-row';
	    		
	    		const divCnt = formRow.length + 1;
	    		
	    		const fields = [
	    			{label : '년도', 		type : 'text', 	name : 'year'}
	    			, {label : '월', 	type : 'text', 	name : 'month'}
	    			, {label : '일', 	type : 'text', 	name : 'days'}
	    			, {label : '내용', 	type : 'text', 	name : 'content'}
	    			, {label : '수입', 	type : 'text', 	name : 'income',	value : '0'}
	    			, {label : '지출', 	type : 'text', 	name : 'spending',	value : '0'}
	    			, {label : '잔액', 	type : 'text', 	name : 'balance',	value : '0'}
	    		];
	    		
	    		fields.forEach((field) => {		
	    			const label = document.createElement('label');
	    			label.setAttribute('for', field.name + divCnt);
	    			label.innerHTML = field.label + ':';
	    			
	    			const input = document.createElement('input');
	    			input.setAttribute('type', field.type);
	    			input.setAttribute('id', field.name + divCnt);
	    			input.setAttribute('name', field.name);
	    			
	    			if (field.name === 'year') {
	    				input.setAttribute('value', modelYear);
	    				input.setAttribute('readonly', true);
	    			} else if (field.name === 'month') {
	    				input.setAttribute('value', modelMonth);
	    				input.setAttribute('readonly', true);
	    			}
	    			
	    			input.setAttribute('required', true);
	    			
	    			if (field.name === 'days') {
	    				addEventDays(input);		
	    			}
	    			
	    			// '수입' 자동 계산입력 스크립트
	    			if (field.name === 'income') {
	    				input.setAttribute('value', field.value);
	    				autoCalculate(field.name, input);
	    			}
	    			
	    			// '지출' 자동 계산입력
	    			if (field.name === 'spending') {
	    				input.setAttribute('value', field.value);
	    				autoCalculate(field.name, input);
	    			}
	    			
	    			if (field.name === 'balance') {
	    				input.setAttribute('value', field.value);
	    			}
	    			newDiv.appendChild(label);
	    			newDiv.appendChild(input);
	    			
	    		});
	    		
	    		accountForm.appendChild(newDiv);
	    	});
	    	
	    	// 폼 제출
	    	accountForm.addEventListener('submit', e => {
	    		e.preventDefault();
	    		
	    		// checkValidity()는 HTML의 required를 만족했는지 비교하는 메소드다.
	    		if (accountForm.checkValidity()) {
		   			// FormData 객체 생성
		            const formData = new FormData(e.target)
		   	        // FormData를 일반 객체로 변환
		            const formObject = {};
		            formData.forEach((value, key) => {
		                // 중복된 name 속성 처리
		                if (formObject[key]) {
		                    // 이미 존재하는 경우 배열에 추가
		                    /* if (Array.isArray(formObject[key])) {
		                        formObject[key].push(value);
		                    } else {
		                        // 배열로 변환
		                        formObject[key] = [formObject[key], value];
		                    } */
		                    formObject[key] += "," + value;
		                } else {
		                    // 처음 추가하는 경우
		                    formObject[key] = value;
		                }
		            });
		
		   	        // JSON 문자열로 변환
		   	        const jsonString = JSON.stringify(formObject);
		   	        
		   	        console.log(jsonString); // JSON 문자열 출력
		   			
		   			
		   			fetch('/api/insertAccout.do', {
						method: 'POST'
						, headers: {
					        'Content-Type': 'application/json'
					    } 
		   				, body : jsonString
					  }).then(response => {
				  			if (!response.ok) throw new Error('Network response was not ok ' + response.statusText);
					  		// json으로 꼭 받아야 하는구나...
					  		return response.json();
					  }).then(data => {
				 	  		// 가계부 입력이 정상적으로 됨. 200코드
				 	  		if (data.resCode === 200) {
				 	  			alert(data.returnMessage);
				 	  			window.location.href = 'getMyAccountList.do?year=' + modelYear + '&month=' + modelMonth;
				 	  		} else {
				 	  			alert(data.returnMessage);
				 	  		}
					  }).catch(error => {
						  alert('There was a problem with the fetch operation: '+error);
					  });
		    			  
	    	        // form.submit 방식
	    			// accountForm.submit();
	    		} else {
	    			alert('입력을 완료하셔야 합니다');
	    		}
	    	});
	    	addEventDays(document.getElementById('days'));
    	}
    });
    
    
    function addEventDays(inputElement) {
    	// 'C' 가계부 입력 시, 단일 이벤트리스너 적용.
    	if (gubun === 'C') {
	    	inputElement.addEventListener('input', e => {
	   			// 숫자형식만 받겠다.
	   			e.target.value = e.target.value.replace(/[^0-9]/g, '');
	   			
	   			// 날짜 days 형식
	    		if (e.target.value.length > 2) {
	    			e.target.value = e.target.value.substring(0, 2);
	    			alert('2자리를 넘을 수 없습니다.');
	    			return false;
	    		}
	    		
	   			// 날짜 days 최대 일수
				if (modelMonth !== '2') {
		    		if (Number(e.target.value) > 31) {
		    			alert('31을 넘게 입력할 수 없습니다.');
		    			e.target.value = e.target.value.substring(0, 1);
		    			return false;
		    		}
				} else {
					if (Number(e.target.value) > 28) {
		    			alert('28을 넘게 입력할 수 없습니다.');
		    			e.target.value = e.target.value.substring(0, 1);
		    			return false;
		    		}
				}
	    	});
    	} else if (gubun === 'U') {
    		inputElement.forEach((item, idx) => {
    			item.addEventListener('input', e => {
    	   			// 숫자형식만 받겠다.
    	   			e.target.value = e.target.value.replace(/[^0-9]/g, '');
    	   			
    	   			// 날짜 days 형식
    	    		if (e.target.value.length > 2) {
    	    			e.target.value = e.target.value.substring(0, 2);
    	    			alert('2자리를 넘을 수 없습니다.');
    	    			return false;
    	    		}
    	    		
    	   			// 날짜 days 최대 일수
    	    		if (Number(e.target.value) > 31) {
    	    			alert('31을 넘게 입력할 수 없습니다.');
    	    			e.target.value = e.target.value.substring(0, 1);
    	    			return false;
    	    		}
    	    	});
    		});
    	}
    }
    
    function autoCalculate(fieldName, input) {
    	input.addEventListener('input', e => {
			const parentDiv = e.target.closest('div');
			// 노드 선택. 공부하기 좋은
			const parentBalance = parentDiv.previousElementSibling.querySelector('input[name="balance"]');
			
			let targetBalance = parentDiv.querySelector('input[name="balance"]');
			if (fieldName === 'income') {
				let tagetSpendig = parentDiv.querySelector('input[name="spending"]');
				// 현재 잔액 = 전 잔액 + 현 수입 - 현 지출
				targetBalance.value = Number(e.target.value) - Number(tagetSpendig.value) + Number(parentBalance.value);
			} else if (fieldName === 'spending') {
				let tagetIncome = parentDiv.querySelector('input[name="income"]');
				// 현재 잔액 = 전 잔액 + 현 수입 - 현 지출
				targetBalance.value = Number(tagetIncome.value) - Number(e.target.value) + Number(parentBalance.value);
			}
    	});
    }
	</script>
</body>
</html>