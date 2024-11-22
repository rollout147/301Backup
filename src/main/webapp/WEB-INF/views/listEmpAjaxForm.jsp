<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	function getDeptName(pDeptno)	{
		console.log(pDeptno)
		// alert("pDeptno->"+pDeptno);
		// 행동강령 : Ajax로 부서번호 보내고 부서명 받음
		$.ajax(
				{
						// 루트경로정보를 가져옴
					url:"<%=request.getContextPath()%>/getDeptName",
						// 컬럼명 // 파라미터값
					data:{deptno : pDeptno},
					// data:{deptno : pDeptno, dname : pDname},					
					dataType: 'text',
					success: function(deptName){
						// alert("success ajax Data->"+deptName);
						$('#deptName').val(deptName);	/* Input Tag에는 val */
						$('#msg').html(deptName);		/* span id Tag등 다른 것은 html*/
					}
				}
			);	
	}
	
	// listEmpAjaxForm(ajax JSP 텍스트 연동)
	function getDept(pDeptno) {
		alert("pDeptno->"+pDeptno);
        // (0819) 현장HW 2 
        // sample/sendVO2" 
        // id : RestDept 결과 값 넣어주기
        console.log(pDeptno)
        $.ajax(
        		{
        			url:"sample/sendVO2",
        				// EmpRestController의 "/sample/sendVO2"와 연결됨
        			data:{deptno : pDeptno},
        					// 위 두 줄은 서버의 Parameter
        			dataType: 'json',
        			success: function(sampleVo){ 	// json객체로 들어옴
        				resultStr = sampleVo.firstName + " " + sampleVo.lastName + " " + sampleVo.mno;
        					// json객체에 .을 찍으면 value값으로 들어옴
        				alert("ajax getDept resultStr->"+resultStr);
        				$('#RestDept').val(resultStr);	// Input Tag
        					// 아래의 id="RestDept"와 연결됨
        			}
        		}
        	);
	}
	

	function empWriteBtn() {
		var empno = $('#empno').val();
		// var sendData = JSON.stringify($('#empTrans').serialize());
		// alert('sendData->'+sendData);
		
		var sendData = $('#empTrans').serializeArray();
		// 		Json Data Conversion;
		sendData3 = jsonParse(sendData);
		alert('sendData3->'+sendData3);
		console.log('sendData3->'+sendData3);
		
		$.ajax({
			url: 'empSerializeWrite',
			type: 'POST',
			contentType: 'application/json;charset=UTF-8',
			data: JSON.stringify(sendData3),
			//jsp에서 stringify로 보내면 Controller에서 @RequestBody를 입력하여 풀어서 받아줘야 함
			dataType: 'json',
			success: function(response) {
				if(response.writeResult > 0) {
					alert("성공");
				}
			}
			
		});
		
	}

	
	function jsonParse(sendData2) {
		obj = {};
		if (sendData2) {
			
			jQuery.each(sendData2, function() {
				obj[this.name] = this.value;
				alert('this.name->'+this.name + ' this.value->'+this.value);
			});
		}
	    return obj;
	}


</script>
</head>
<body>
<h2>회원 정보</h2>
	<table >
		<tr><th>사번</th><th>이름</th><th>업무</th><th>부서</th><th>근무지</th></tr>
		<c:forEach var="emp" items="${listEmp}">
			<tr><td>${emp.empno }</td><td>${emp.ename }</td>
				<td>${emp.job }</td>
				<td>${emp.deptno} 
				    <input type="button" id="btn_idCheck" value="부서명" onmouseover="getDeptName(${emp.deptno })">
				</td>
				<td>${empDept.loc }</td>
			</tr>
		</c:forEach>
	</table>

	deptName:  <input type="text" id="deptName"  readonly="readonly"><p>
    Message :  <span id="msg"></span><p>
    
    RestController sendVO2: <input type="text" id="RestDept"    readonly="readonly"><p>
	RestController sendVO2: sendVO2<input type="button" id="btn_Dept"  value="부서명"  
	                                      onclick="getDept(10)"><p>
	                             
	                                      
<h2>Serialize Test</h2>
    <form  name="empTrans"   id="empTrans">
    		<input type="hidden"  id="empno"  name="empno"    value="1234">
    		<input type="hidden"  id="ename"  name="ename"    value="시리얼">
    		<input type="hidden"  id="sal"    name="sal"      value="1000">
        <input type="button"  value="Ajax Serialize 확인" onclick="empWriteBtn()"> 
     </form>
</body>
</html>