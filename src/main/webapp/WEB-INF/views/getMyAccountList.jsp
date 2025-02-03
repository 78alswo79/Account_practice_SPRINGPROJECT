<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="EUC-KR">
<title>재무 현황</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .button {
            padding: 10px 15px;
            background-color: #4CAF50; /* Green */
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            font-size: 16px;
            margin-bottom: 20px;
            display: inline-block;
        }
        .button:hover {
            background-color: #45a049; /* Darker green */
        }
        .button-container {
            text-align: right; /* 버튼을 오른쪽 정렬 */
            margin-bottom: 20px; /* 버튼과 테이블 간격 */
        }
        .custom-select {
            position: relative;
            display: inline-block;
            width: 200px;
        }
        .custom-select select {
            display: none; /* 기본 셀렉트 숨기기 */
        }
        .select-selected {
            background-color: #f2f2f2;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            cursor: pointer;
        }
        .select-items {
            position: absolute;
            background-color: #f2f2f2;
            border: 1px solid #ddd;
            border-radius: 5px;
            z-index: 99;
            display: none;
        }
        .select-items div {
            padding: 10px;
            cursor: pointer;
        }
        .select-items div:hover {
            background-color: #ddd;
        }
        
        /* 셀렉트 박스 스타일 */
        /* .custom-select {
            display: inline-block;
            margin-bottom: 20px;
        } */
        select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            width: 200px; /* 원하는 너비 설정 */
        }
    </style>
</head>
<body>
    <h2>재무 현황</h2>
    
    <!-- 가계부 작성하기 버튼 -->
    <div class="button-container">
    	<a href="#" class="button exceldown">액셀 내려받기</a>
        <a href="addAccount.do?gubun=C&year=${year}&month=${month}" class="button add">가계부 작성하기</a>
        <a href="#"<%-- href="addAccount.do?gubun=U&year=${year}&month=${month}" --%> class="button update">가계부 수정하기</a>
        <a href="deleteAccount.do?year=${year}&month=${month}" class="button delete">가계부 삭제하기</a>
    </div>

    <!-- id가 selectId인 셀렉트 박스 추가 -->
        <label for="selectId">필터 선택:</label>
        <select id="selectId">
        	<c:if test="${not empty getContentList}">
        		<c:forEach items="${getContentList}" var="getContentList">
        			<c:choose>
	        			<c:when test="${getContentList == '전체'}">
	        				<option value="">${getContentList}</option>
	        			</c:when>
	        			<c:otherwise>
		        			<option value="${getContentList}">${getContentList}</option>
	        			</c:otherwise>
        			
        			</c:choose>
        		</c:forEach>
        	</c:if>
        </select>
    <%-- 파일 업로드 나중에 구현하기 --%>
    <!-- <form enctype="multipart/form-data" id = "upload" method = "post" action="upload">
    	<input type="file" name="file"/>
    	<button type="submit">업로드</button>
    </form> -->
	<div class="button-container">
        <a href="#" class="button perPage fifteen">15개 보기</a>
        <a href="#" class="button perPage twentyfive">25개 보기</a>
        <a href="#" class="button perPage fifty">50개 보기</a>
    </div>

    <table>
        <thead>
            <tr>
                <th><input type="checkbox" id="selectAll" onclick="allCheckBox(this)"></th> <!-- 전체 선택 체크박스 -->
                <th>년도</th>
                <th>월</th>
                <th>일</th>
                <th>내용</th>
                <th>수입(단위 : 원)</th>
                <th>지출(단위 : 원)</th>
                <th>잔액(단위 : 원)</th>
            </tr>
        </thead>
        <tbody id = "responseAppend">
            <c:choose>
                <c:when test="${not empty testList}">	    		
                    <c:forEach items="${testList}" var="testList">
                        <tr>
                            <td><input type="checkbox" name="selectedItems" value="${testList.seq}"></td>
                            <td class="year">${testList.year}</td>
                            <td class="month">${testList.month}</td>
                            <td><input type="text" name="days" value="${testList.days}"></td>
                            <td><input type="text" name="content" value="${testList.content}"></td>
                            <td><fmt:formatNumber value="${testList.income}" type="number" pattern="#,##0" var="income"/><input type="text" name="income" value="${income}"></td>
                            <td><fmt:formatNumber value="${testList.spending}" type="number" pattern="#,##0" var="spending"/><input type="text" name="spending" value="${spending}"></td>
                            <td><fmt:formatNumber value="${testList.balance}" type="number" pattern="#,##0" var="balance"/><input type="text" name="balance" value="${balance}"></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:when test="${empty testList}">	    		
                    <tr>
                        <td colspan="8"><h2>작성한 가계부가 없습니다!!</h2></td>
                    </tr>
                </c:when>
            </c:choose>
        </tbody>
    </table><br>
    
    <!-- 페이징 -->
    <div class="pagination">
    	<!-- 리스트 갯수가 존재하는지.  -->
        <c:if test="${pageVO.totalPages > 1}">
        	<!-- 현재페이지가 1보다 클 때, '이전'을 표시한다. -->
            <c:if test="${pageVO.currentPage > 1}">
                <a href="getMyAccountList.do?year=${year}&month=${month}&currentPage=${pageVO.currentPage - 1}">이전</a>
            </c:if>
            <c:forEach begin="1" end="${pageVO.totalPages}" var="i">
                <c:choose>
                    <c:when test="${i == pageVO.currentPage}">
                        <strong>${i}</strong>
                    </c:when>
                    <c:otherwise>
                        <a href="getMyAccountList.do?year=${year}&month=${month}&currentPage=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <!-- 다음 표시하는 방법. -->
            <c:if test="${pageVO.currentPage < pageVO.totalPages}">
                <a href="getMyAccountList.do?year=${year}&month=${month}&currentPage=${pageVO.currentPage + 1}">다음</a>
            </c:if>
        </c:if>
    </div>
    
    
    <div class="button-container">
        <a href="addAccount.do?gubun=C&year=${year}&month=${month}" class="button add">가계부 작성하기</a>
        <a href="#"<%-- href="addAccount.do?gubun=U&year=${year}&month=${month}" --%> class="button update">가계부 수정하기</a>
        <a href="deleteAccount.do?year=${year}&month=${month}" class="button delete">가계부 삭제하기</a>
    </div>


</body>

<script type="text/javascript">
	let seqArray = [];						<%-- 선택된 체크박스 --%>
	const resYear = ${year};
	const resMonth = ${month};
	
	document.addEventListener("DOMContentLoaded", () => {
		
	});
	
	
	// 전체 체크박스 선택/해제.
	function allCheckBox(param) {
		const selectedItems = document.querySelectorAll('input[name="selectedItems"]');
    	// 진짜 간단한 방법.
    	// 분기처리 없이, 전체선택 체크박스 값을 넣어준다!!. 공부할만하다.
    	selectedItems.forEach((item, idx) => {
    	    item.checked = param.checked;
    	});
	};

	
	// 업데이트 컨틀롤러 스크립트
	const updateBtn = document.querySelectorAll('.button.update');
	//const selectedItems = document.querySelectorAll('input[name="selectedItems"]');
	updateBtn.forEach(item => {		
		item.addEventListener('click', e => {
			e.preventDefault();
			// 체크박스 값 중복방지
			// seqArray = [];
			
			let param = [];
			let seq, year, month, days, content, income, spending, balance;
			
			document.querySelectorAll('input[name="selectedItems"]').forEach((item, idx) => {
	    	    if (item.checked) {
	    	    	// console.log(item.value);
	    	    	seqArray.push(item.value);
	    	    	
	    	    	const rowTr = item.closest('tr');
	    	    	seq = rowTr.querySelector('input[name="selectedItems"]').value;
	    	    	year = rowTr.querySelector('.year').innerText;
	    	    	month = rowTr.querySelector('.month').innerText;
	    	    	days = rowTr.querySelector('input[name="days"]').value;
	    	    	content = rowTr.querySelector('input[name="content"]').value;
	    	    	income = rowTr.querySelector('input[name="income"]').value;
	    	    	spending = rowTr.querySelector('input[name="spending"]').value;
	    	    	balance = rowTr.querySelector('input[name="balance"]').value;
	    	    	param.push({
	    	    		seq : seq
	    	    		, year : year
	    	    		, month : month
	    	    		, days : days
	    	    		, content : content
	    	    		, income : income
	    	    		, spending : spending
	    	    		, balance : balance
	    	    	});
	    	    }
	    	});
			console.log(param);
			
			
			// array객체. 유효성 체크 방법. 공부하기 좋은.
			if (param.length > 0 && Array.isArray(param)) {
				if (param.length > 10) {
					alert('업데이트는 10개행을 할 수 없습니다.!');
					return false;
				} else {
					//공부하기 좋은, array.join(',') : 배열의 요소를 쉼표로 구분하여 하나이 문자열로 만든다.
					//window.location.href = 'addAccount.do?seqArray=' + seqArray.join(',') + '&gubun=U&year=' + ${year} + '&month=' + ${month};
					fetch('updateAccout.do', {
						method : "POST"
						, headers : {'Content-Type' : 'application/json' }
						, body : JSON.stringify(param)
					}).then(response => {
						if (!response.ok) throw new Error('Communication Response Error!!' + response.statusText)
	
						return response.json();
					}).then(data => {
						if (data.resCode === '200') {
							alert(data.resMessage);
						} else {
							alert(data.resMessage);
						}
					}).catch(error => {
						alert('어떠한 이유로 서버와의 통신이 실패했습니다.' + error);
					}).finally(() => {
						// 무조건 실행되어야 할 로직 써주면 됨.
						window.location.href = 'getMyAccountList.do?year=' + year + '&month=' + month;
					})
				}

			} else {
				alert('체크박스를 선택해 주세요.!');
				return false;
			}
		});
	});
	
	// 삭제 컨트롤러.
	const deleteBtn = document.querySelectorAll('.button.delete');
	deleteBtn.forEach(item => {		
		item.addEventListener('click', e => {
			// 체크박스 값 중복방지
			seqArray = [];
			e.preventDefault();
			
			const resCheckedLength = checkboxValidation();
			
			if (resCheckedLength > 0) {				
				<%-- 공부하기 좋은, array.join(',') : 배열의 요소를 쉼표로 구분하여 하나이 문자열로 만든다.--%>
				//window.location.href = 'addAccount.do?seqArray=' + seqArray.join(',') + '&gubun=U&year=' + ${year} + '&month=' + ${month};
				fetch('deleteAccount.do', {
					method : 'POST'
					, header : { 'Content-Type' : 'application/json' }
					, body : seqArray.join(',')
				}).then(response => {
					if (!response.ok) throw new Error ('Network response was not ok ' + response.statusText);
					return response.json();
				}).then(data => {
					if (data.resCode === '200') {
						alert(data.resMessage);
						window.location.href = '/getMyAccountList.do?year=' + ${year} + '&month=' + ${month};
					} else {
						alert(data.resMessage);
						return false;
					}
				}).catch(error => {
				  	alert('There was a problem with the fetch operation: '+error);
			  	});
			} else {
				alert('체크박스를 선택해 주세요.!');
				return false;
			}
		});
	});
	
	// 필터옵션 스크립트 짜기
	const selectId = document.querySelector('#selectId');
	selectId.addEventListener('change', e => {
		//console.log('딸칵!');
		//console.log('e', e.target.value);
		const baseURL = 'api/getFilteredList.do';
		const param = new URLSearchParams({
			year : resYear
			, month : resMonth
			, filterOption : e.target.value
			// 언젠가는 추가해줘야 할지도?, perPage
		});
		
		const apiURL = baseURL+'?'+param.toString();
		
		fetch(apiURL)
			.then(response => {
				//console.log('response', response);
				if (!response.ok) throw new Error('Network is failure!!' + response.status);
				return response.json();
			}).then(data => {
				console.log('data', data);
				const responseAppend = document.querySelector('#responseAppend');

                if (data.resCode === 200) {                	
					// 태그값 생성해서 붙여주기.
					// 자식요소들 한번에 지우기. 공부하기 좋은.
					responseAppend.innerHTML = '';
					//let resTrTag;
					// tr의 개수는 곧 testList.size()를 의미.
	                data.testList.forEach((item, idx) => {
						const trTag = document.createElement('tr');
	                	const resTrTag = makeFields({trTag, item});
	                	// 공부하기 좋은. 객체.innerHTML은 문자열이기때문에 이상하게 표시가 남
	                	responseAppend.appendChild(resTrTag);
					});
                } else {
                	alert('데이터를 불러오지 못했습니다.');
                }
			}).catch(error => {
				alert('error가 났습니다.' + error);
			}).finally(()=> {
				const pagination = document.querySelectorAll('.pagination');
				// 필터옵션이 '전체'가 아닌경우는 하당 페이징 뷰 보여주기
				if (e.target.value !== '') {
					pagination.forEach((item, idx) => {
						item.style.display = 'none';
					});					
				} else {
					pagination.forEach((item, idx) => {
						item.style.display = 'block';
					});
				}
			})
	});
	
	function makeFields(paramObj) {
		const fields = [
			{name : 'selectedItems', 	type : 'checkbox', 	value : paramObj.item.seq}
			, {name : 'year', 			type : '',			value : paramObj.item.year}
			, {name : 'month', 			type : '',			value : paramObj.item.month}
			, {name : 'days', 			type : 'text', 		value : paramObj.item.days}
			, {name : 'content', 		type : 'text', 		value : paramObj.item.content}
			, {name : 'income', 		type : 'text', 		value : paramObj.item.income} 
			, {name : 'spending', 		type : 'text', 		value : paramObj.item.spending}
			, {name : 'balance', 		type : 'text', 		value : paramObj.item.balance}
		];
		
		fields.forEach((item, idx) => {
			const tdTag = document.createElement('td');

			if (item.type !== '') {		
				const inputTag = document.createElement('input');
				inputTag.setAttribute('type', item.type);
				inputTag.setAttribute('name', item.name);
				inputTag.setAttribute('value', item.value);
				tdTag.appendChild(inputTag);
			} 
			else {
				tdTag.setAttribute('class', item.name);
				tdTag.textContent = item.value;
			}
			console.log(tdTag)
			paramObj.trTag.appendChild(tdTag);
		});
		return paramObj.trTag;
	}

	// 필터옵션, viewPer 시 옵저버패턴을 이용한(interSect?) 페이징 스크롤 짜기
	const perPageBtn = document.querySelectorAll('.button.perPage');
	let viewNumber;
	perPageBtn.forEach((item, idx) => {
		item.addEventListener('click', e=> {
			console.log('e.target', e.target);
			if (e.target.classList.contains('fifteen')) {			
				viewNumber = 15; 	
			} else if (e.target.classList.contains('twentyfive')) {
				viewNumber = 25;
			} else {
				viewNumber = 50;
			}
			
			const baseURL = 'api/getFilteredList.do';
			const params = new URLSearchParams({
				year : resYear
				, month : resMonth
				, perPage : viewNumber
				// 언젠가는 추가해줘야 할지도?, filterOption값.
			});
			const apiURL = baseURL + '?' + params.toString();
			
			fetch(apiURL)
			.then(response => {
				if (!response.ok) throw new Error ('Network communication Error!!!' + response.status);
				return response.json();
			})
			.then(data => {
				console.log('data', data);
				const responseAppend = document.querySelector('#responseAppend');
				
                if (data.resCode === 200) {                	
					// 태그값 생성해서 붙여주기.
					// 자식요소들 한번에 지우기. 공부하기 좋은.
					responseAppend.innerHTML = '';
					// tr의 개수는 곧 testList.size()를 의미.
	                data.testList.forEach((item, idx) => {
						const trTag = document.createElement('tr');
	                	const resTrTag = makeFields({trTag, item});
	                	// 공부하기 좋은. 객체.innerHTML은 문자열이기때문에 이상하게 표시가 남
	                	responseAppend.appendChild(resTrTag);
					});
                } else {
                	alert('데이터를 불러오지 못했습니다.');
                }
			})
			.catch(error => {
				console.log('error가 발생했습니다.', error);
			})
			.finally(()=> {
				const pagination = document.querySelectorAll('.pagination');
				pagination.forEach((item, idx) => {
					item.style.display = 'none';
				});
			});
		})
	});
	
	
	// 무한 스크롤 페이징. 공부하기 좋은.
	const responseAppend = document.querySelector('#responseAppend');
	let isLoading = false; // API 호출 중복 방지 플래그
	const callback = (entries, io) => {
		entries.forEach(item => {
			/* if (item.isIntersecting) {
				intersectOb.unobserve(item.target);
				console.log('옵저버 더이상 감지 안함.');
				getAPIList();
			} else {
				console.log('옵저버????.');
			} */
			
			if (item.isIntersecting && !isLoading) {
	            isLoading = true; // API 호출 시작
	            intersectOb.unobserve(item.target); // 현재 타겟을 옵저버에서 제거
	            console.log('옵저버 더이상 감지 안함.');
	            getAPIList();
	        }
		})
	};

	const intersectOb = new IntersectionObserver(callback, {threshold : 0.7});
	//intersectOb.observe(responseAppend);			// 옵저버 최초 호출.
	
	const getAPIList = () => {
		const baseURL = 'api/getFilteredList.do';
		const params = new URLSearchParams({
			year : resYear
			, month : resMonth
			, perPage : 5
			// 언젠가는 추가해줘야 할지도?, filterOption값.
		});
		const apiURL = baseURL + '?' + params.toString();
		
		fetch(apiURL)
			.then(response => {
				if (!response.ok) throw new Error ('Network communication Error!!!' + response.status);
				return response.json();
			})
			.then(data => {
				console.log('data', data);
				const responseAppend = document.querySelector('#responseAppend');
				
	            if (data.resCode === 200) {                	
					// 태그값 생성해서 붙여주기.
					// 자식요소들 한번에 지우기. 공부하기 좋은.
					// responseAppend.innerHTML = '';
					// tr의 개수는 곧 testList.size()를 의미.
	                data.testList.forEach((item, idx) => {
						const trTag = document.createElement('tr');
	                	const resTrTag = makeFields({trTag, item});
	                	// 공부하기 좋은. 객체.innerHTML은 문자열이기때문에 이상하게 표시가 남
	                	responseAppend.appendChild(resTrTag);
					});
	            } else {
	            	alert('데이터를 불러오지 못했습니다.');
	            }
			})
			.catch(error => {
				console.log('error가 발생했습니다.', error);
			})
			.finally(()=> {
				isLoading = false; 					// API 호출 완료
	            intersectOb.observe(responseAppend.children[responseAppend.children.length - 2]); // 다시 옵저버 설정
	            const pagination = document.querySelectorAll('.pagination');
	            pagination.forEach((item) => {
	                item.style.display = 'none';
	            });
			});
	};
	
	// 액셀 다운로드 기능. 공부하기 좋은.
	const excelDownBtn = document.querySelector('.exceldown');
	excelDownBtn.addEventListener('click', e => {
		// a태그의 화면고침 막기.
		e.preventDefault();
		
		if ('${testList}' != '') {
			const confirmBool = confirm('정말 다운을 받으시겠습니까??');
			if (confirmBool) {
				const baseURL = '/excelDown.do';
				const params = new URLSearchParams({
					year : resYear
					, month : resMonth
				})
				const apiURL = baseURL+"?"+params;
				
				fetch(apiURL, {
					method : 'GET'
					, headers: {
			            'Content-Type': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
			        }
				}).then(response => {
					if (!response.ok) throw new Error('Communication Response Error!!' + response.statusText);
					return response.blob();
				}).then(blobData => {
					const url = window.URL.createObjectURL(blobData); // Blob URL 생성
			        const a = document.createElement('a'); // 링크 요소 생성
			        a.style.display = 'none';
			        a.href = url;
			        
			        a.download = resYear + '_' + resMonth + '_accountList.xlsx'; // 다운로드할 파일 이름
			        document.body.appendChild(a); // 링크 요소를 DOM에 추가
			        a.click(); // 링크 클릭하여 다운로드 시작
			        window.URL.revokeObjectURL(url); // Blob URL 해제
				})
			}
		} else {
			alert('다운로드 받을 내역 리스트가 존재하지 않습니다.');
		}
	});
	
	// checkboxValidation
	const checkboxValidation = () => {
		document.querySelectorAll('input[name="selectedItems"]').forEach((item, idx) => {
		    if (item.checked) {
		    	//console.log('item', item)
		    	seqArray.push(item.value);
		    }
		});
		return seqArray.length;
	}
	
</script>
</html>