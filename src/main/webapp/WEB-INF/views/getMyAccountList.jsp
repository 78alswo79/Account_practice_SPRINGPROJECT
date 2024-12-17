<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="EUC-KR">
<title>�繫 ��Ȳ</title>
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
            text-align: right; /* ��ư�� ������ ���� */
            margin-bottom: 20px; /* ��ư�� ���̺� ���� */
        }
        .custom-select {
            position: relative;
            display: inline-block;
            width: 200px;
        }
        .custom-select select {
            display: none; /* �⺻ ����Ʈ ����� */
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
        
        /* ����Ʈ �ڽ� ��Ÿ�� */
        /* .custom-select {
            display: inline-block;
            margin-bottom: 20px;
        } */
        select {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            width: 200px; /* ���ϴ� �ʺ� ���� */
        }
    </style>
</head>
<body>
    <h2>�繫 ��Ȳ</h2>
    <!-- ����� �ۼ��ϱ� ��ư -->
    <div class="button-container">
        <a href="addAccount.do?gubun=C&year=${year}&month=${month}" class="button add">����� �ۼ��ϱ�</a>
        <a href="#"<%-- href="addAccount.do?gubun=U&year=${year}&month=${month}" --%> class="button update">����� �����ϱ�</a>
        <a href="deleteAccount.do?year=${year}&month=${month}" class="button delete">����� �����ϱ�</a>
    </div>

    <!-- id�� selectId�� ����Ʈ �ڽ� �߰� -->
        <label for="selectId">���� ����:</label>
        <select id="selectId">
        	<c:if test="${not empty getContentList}">
        		<c:forEach items="${getContentList}" var="getContentList">
        			<c:choose>
	        			<c:when test="${getContentList == '��ü'}">
	        				<option value="">${getContentList}</option>
	        			</c:when>
	        			<c:otherwise>
		        			<option value="${getContentList}">${getContentList}</option>
	        			</c:otherwise>
        			
        			</c:choose>
        		</c:forEach>
        	</c:if>
        </select>
	<div class="button-container">
        <a href="#" class="button perPage fifteen">15�� ����</a>
        <a href="#" class="button perPage twentyfive">25�� ����</a>
        <a href="#" class="button perPage fifty">50�� ����</a>
    </div>

    <table>
        <thead>
            <tr>
                <th><input type="checkbox" id="selectAll" onclick="allCheckBox(this)"></th> <!-- ��ü ���� üũ�ڽ� -->
                <th>�⵵</th>
                <th>��</th>
                <th>��</th>
                <th>����</th>
                <th>����(���� : ��)</th>
                <th>����(���� : ��)</th>
                <th>�ܾ�(���� : ��)</th>
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
                        <td colspan="8"><h2>�ۼ��� ����ΰ� �����ϴ�!!</h2></td>
                    </tr>
                </c:when>
            </c:choose>
        </tbody>
    </table><br>
    
    <!-- ����¡ -->
    <div class="pagination">
    	<!-- ����Ʈ ������ �����ϴ���.  -->
        <c:if test="${pageVO.totalPages > 1}">
        	<!-- ������������ 1���� Ŭ ��, '����'�� ǥ���Ѵ�. -->
            <c:if test="${pageVO.currentPage > 1}">
                <a href="getMyAccountList.do?year=${year}&month=${month}&currentPage=${pageVO.currentPage - 1}">����</a>
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
            <!-- ���� ǥ���ϴ� ���. -->
            <c:if test="${pageVO.currentPage < pageVO.totalPages}">
                <a href="getMyAccountList.do?year=${year}&month=${month}&currentPage=${pageVO.currentPage + 1}">����</a>
            </c:if>
        </c:if>
    </div>
    
    
    <div class="button-container">
        <a href="addAccount.do?gubun=C&year=${year}&month=${month}" class="button add">����� �ۼ��ϱ�</a>
        <a href="#"<%-- href="addAccount.do?gubun=U&year=${year}&month=${month}" --%> class="button update">����� �����ϱ�</a>
        <a href="deleteAccount.do?year=${year}&month=${month}" class="button delete">����� �����ϱ�</a>
    </div>


</body>

<script type="text/javascript">
	let seqArray = [];
	const resYear = ${year};
	const resMonth = ${month};
	
	document.addEventListener("DOMContentLoaded", () => {
		
	});
	
	
	// ��ü üũ�ڽ� ����/����.
	function allCheckBox(param) {
		const selectedItems = document.querySelectorAll('input[name="selectedItems"]');
    	// ��¥ ������ ���.
    	// �б�ó�� ����, ��ü���� üũ�ڽ� ���� �־��ش�!!. �����Ҹ��ϴ�.
    	selectedItems.forEach((item, idx) => {
    	    item.checked = param.checked;
    	});
	};

	
	// ������Ʈ ��Ʋ�ѷ� ��ũ��Ʈ
	const updateBtn = document.querySelectorAll('.button.update');
	//const selectedItems = document.querySelectorAll('input[name="selectedItems"]');
	updateBtn.forEach(item => {		
		item.addEventListener('click', e => {
			e.preventDefault();
			// üũ�ڽ� �� �ߺ�����
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
			
			
			// array��ü. ��ȿ�� üũ ���. �����ϱ� ����.
			if (param.length > 0 && Array.isArray(param)) {
				if (param.length > 10) {
					alert('������Ʈ�� 10������ �� �� �����ϴ�.!');
					return false;
				} else {
					//�����ϱ� ����, array.join(',') : �迭�� ��Ҹ� ��ǥ�� �����Ͽ� �ϳ��� ���ڿ��� �����.
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
						alert('��� ������ �������� ����� �����߽��ϴ�.' + error);
					}).finally(() => {
						// ������ ����Ǿ�� �� ���� ���ָ� ��.
						window.location.href = 'getMyAccountList.do?year=' + year + '&month=' + month;
					})
				}

			} else {
				alert('üũ�ڽ��� ������ �ּ���.!');
				return false;
			}
		});
	});
	
	// ���� ��Ʈ�ѷ�.
	const deleteBtn = document.querySelectorAll('.button.delete');
	deleteBtn.forEach(item => {		
		item.addEventListener('click', e => {
			// üũ�ڽ� �� �ߺ�����
			seqArray = [];
			e.preventDefault();
			
			selectedItems.forEach((item, idx) => {
	    	    if (item.checked) {
	    	    	// console.log(item.value);
	    	    	seqArray.push(item.value);
	    	    }
	    	});
			
			<%-- array��ü. ��ȿ�� üũ ���. �����ϱ� ����.--%>
			if (seqArray === [] || (seqArray.length > 0 && Array.isArray(seqArray)) ) {				
				<%-- �����ϱ� ����, array.join(',') : �迭�� ��Ҹ� ��ǥ�� �����Ͽ� �ϳ��� ���ڿ��� �����.--%>
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
				alert('üũ�ڽ��� ������ �ּ���.!');
				return false;
			}
		});
	});
	
	// ���Ϳɼ� ��ũ��Ʈ ¥��
	const selectId = document.querySelector('#selectId');
	selectId.addEventListener('change', e => {
		//console.log('��Ĭ!');
		//console.log('e', e.target.value);
		const baseURL = 'getFilteredList.do';
		const param = new URLSearchParams({
			year : resYear
			, month : resMonth
			, filterOption : e.target.value
			// �������� �߰������ ������?, perPage
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
					// �±װ� �����ؼ� �ٿ��ֱ�.
					// �ڽĿ�ҵ� �ѹ��� �����. �����ϱ� ����.
					responseAppend.innerHTML = '';
					//let resTrTag;
					// tr�� ������ �� testList.size()�� �ǹ�.
	                data.testList.forEach((item, idx) => {
						const trTag = document.createElement('tr');
	                	const resTrTag = makeFields({trTag, item});
	                	// �����ϱ� ����. ��ü.innerHTML�� ���ڿ��̱⶧���� �̻��ϰ� ǥ�ð� ��
	                	responseAppend.appendChild(resTrTag);
					});
                } else {
                	alert('�����͸� �ҷ����� ���߽��ϴ�.');
                }
			}).catch(error => {
				alert('error�� �����ϴ�.' + error);
			}).finally(()=> {
				const pagination = document.querySelectorAll('.pagination');
				// ���Ϳɼ��� '��ü'�� �ƴѰ��� �ϴ� ����¡ �� �����ֱ�
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

	// ���Ϳɼ�, viewPer �� ������������ �̿���(interSect?) ����¡ ��ũ�� ¥��
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
			
			const baseURL = 'getFilteredList.do';
			const params = new URLSearchParams({
				year : resYear
				, month : resMonth
				, perPage : viewNumber
				// �������� �߰������ ������?, filterOption��.
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
					// �±װ� �����ؼ� �ٿ��ֱ�.
					// �ڽĿ�ҵ� �ѹ��� �����. �����ϱ� ����.
					responseAppend.innerHTML = '';
					// tr�� ������ �� testList.size()�� �ǹ�.
	                data.testList.forEach((item, idx) => {
						const trTag = document.createElement('tr');
	                	const resTrTag = makeFields({trTag, item});
	                	// �����ϱ� ����. ��ü.innerHTML�� ���ڿ��̱⶧���� �̻��ϰ� ǥ�ð� ��
	                	responseAppend.appendChild(resTrTag);
					});
                } else {
                	alert('�����͸� �ҷ����� ���߽��ϴ�.');
                }
			})
			.catch(error => {
				console.log('error�� �߻��߽��ϴ�.', error);
			})
			.finally(()=> {
				const pagination = document.querySelectorAll('.pagination');
				pagination.forEach((item, idx) => {
					item.style.display = 'none';
				});
			});
		})
	});
	
	const responseAppend = document.querySelector('#responseAppend');
	let isLoading = false; // API ȣ�� �ߺ� ���� �÷���
	const callback = (entries, io) => {
		entries.forEach(item => {
			/* if (item.isIntersecting) {
				intersectOb.unobserve(item.target);
				console.log('������ ���̻� ���� ����.');
				getAPIList();
			} else {
				console.log('������????.');
			} */
			
			if (item.isIntersecting && !isLoading) {
	            isLoading = true; // API ȣ�� ����
	            intersectOb.unobserve(item.target); // ���� Ÿ���� ���������� ����
	            console.log('������ ���̻� ���� ����.');
	            getAPIList();
	        }
		})
	};

	const intersectOb = new IntersectionObserver(callback, {threshold : 0.7});
	intersectOb.observe(responseAppend);			// ������ ���� ȣ��.
	
	const getAPIList = () => {
		const baseURL = 'getFilteredList.do';
		const params = new URLSearchParams({
			year : resYear
			, month : resMonth
			, perPage : 5
			// �������� �߰������ ������?, filterOption��.
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
					// �±װ� �����ؼ� �ٿ��ֱ�.
					// �ڽĿ�ҵ� �ѹ��� �����. �����ϱ� ����.
					// responseAppend.innerHTML = '';
					// tr�� ������ �� testList.size()�� �ǹ�.
	                data.testList.forEach((item, idx) => {
						const trTag = document.createElement('tr');
	                	const resTrTag = makeFields({trTag, item});
	                	// �����ϱ� ����. ��ü.innerHTML�� ���ڿ��̱⶧���� �̻��ϰ� ǥ�ð� ��
	                	responseAppend.appendChild(resTrTag);
					});
	            } else {
	            	alert('�����͸� �ҷ����� ���߽��ϴ�.');
	            }
			})
			.catch(error => {
				console.log('error�� �߻��߽��ϴ�.', error);
			})
			.finally(()=> {
				isLoading = false; 					// API ȣ�� �Ϸ�
	            intersectOb.observe(responseAppend.children[responseAppend.children.length - 2]); // �ٽ� ������ ����
	            const pagination = document.querySelectorAll('.pagination');
	            pagination.forEach((item) => {
	                item.style.display = 'none';
	            });
			});
	};
	
</script>
</html>