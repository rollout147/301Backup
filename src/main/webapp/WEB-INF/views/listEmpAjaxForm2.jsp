<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function getListDept() {
		alert("getListDept Run...");
			var str		= "";
			var str2	= "";
			
			$.ajax(
					{
						url:"/sendVO3",
						dataType:'json',
						success:function(deptList) {
							var jsonStr = JSON.stringify(deptList);
							alert("jsonStr->"+jsonStr);
							$('#dept_list_str').append(jsonStr);
							str += "<select name='dept'>";
							$(deptList).each(
									//each: 향상형 for문처럼 작동하여 multirow에 각각 영향을 미치도록 하는 것
								function() {
										str2 = "<option value='"+this.deptno + "'> "+this.dname+"</option>";
										str += str2;
										}
							)
							str += "</select><p>"
							alert("combobox str->"+str);
								// 맨 아래 combobox와 연결됨
							$('#dept_list_combobox').append(str);
							
						}
						
					}		
				
				);
	}
	
	function getEmpnoDelete(pIndex) {
		// (0820)현장 HW 1-1
		// url    -> empnoDelete
		// parm   -> empno : selEmpno 
		// 성공하면  ->  Delete Tag
		var selEmpno = $("#empno"+pIndex).val();
		var selEname = $("#ename"+pIndex).val();
		// 결과를 text로 받음 
/* 			$.ajax(
				{
					url: "/empnoDelete",
					data: {empno : selEmpno , ename : selEname},
						// stringify가 아닌 객체로 보냈기 때문에,
						// Controller에서 @RequestBody가 아닌 객체 그대로 받아서 사용할 수 있음
					dataType: 'text',
					success: function(data) {
						alert(".ajax getDeptDelete data->"+data);
						if (data == '1') {
							// 성공하면 아래 라인 수행하기 --> Delete Tag 
							$('#emp'+ pIndex).remove();		 
						}
					}
				
				}		
			);		
	} 	
	
*/
    // 결과를 객체로 받음 
		$.ajax(
			  {
					url:"/empnoDelete03",
					data:{  empno : selEmpno 
						  , ename : selEname
						 },
					dataType:'json',
					success:function(response){
						alert(".ajax getDeptDelete data->"+response.delStatus); 
						if (response.delStatus == '1') {
							// 성공하면 아래라인 수행 
							$('#emp'+pIndex).remove();     /*  Delete Tag */
						}
					}
			 }
		);		



	}
	
</script>
</head>
<body>
<h2>회원정보</h2>
	<table>
		<tr>
			<th>번호</th><th>사번</th><th>이름</th>
			<th>업무</th><th>부서</th>
		</tr>
		<c:forEach var="emp" items="${listEmp}" varStatus="status">
			<tr id="emp${status.index}"> <td>emp${status.index}</td>
				<td>
					<input type="hidden" id="deptno${status.index}" value="${emp.deptno}">
					<input type="text" id="empno${status.index}" value="${emp.empno}">${emp.empno}<p>
				</td>
				<td>
					<input type="text" id="ename${status.index}" value="${emp.ename}">${emp.ename}<p>
				</td>
				<td>
					${emp.job}
				</td>
				<td>
					${emp.deptno}
					<input type="button" id="btn_idCheck2" value="사원 Row Delete"
										onclick="getEmpnoDelete(${status.index})">
				</td>
			</tr>
		</c:forEach>
	</table>
	
	RestController LISTVO3: <input type="button" 
								   id="btn_Dept3"
                                   value="부서명 LIST"  
                                   onclick="getListDept()"><p>
                                   
	dept_list_str:	<div id="dept_list_str"></div>

	dept_list_combobox:
	<div id="dept_list_combobox"></div>
			<!-- combobox안에 값이 통째로 들어감 -->
	
	<h1>The End </h1>
</body>
</html>