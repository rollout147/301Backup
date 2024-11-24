<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function chk() {
		if (!frm.bookno.value) {
			alert("책 번호를 입력해주세요")
			frm.bookno.focus();
			return false;
		} else location.href="confirm?bookno="+frm.bookno.value;
	}
</script>
</head>
<body>
<h2>책 정보 입력</h2>
	    <c:if test="${msg!=null}">${msg}</c:if>
		<form action="writeBook" method="post" name="frm">
			<table>
				
				<tr><th>책 번호(PK)</th><td><input type="number"	name="bookno" required="required"
															maxlength="4"	value="${bookno }">
									<input type="button" value="중복확인" onclick="chk()">
								</td>
				</tr>
				<tr><th>책 이름</th><td><input type="text"	name="bookname"		required="required"></td></tr>
				<tr><th>작가</th><td><input type="text"		name="writer"		required="required"></td></tr>
				<!-- <tr><th>장르</th><td><input type="text"		name="genre"		required="required"></td></tr> -->
				
				<tr><th>장르</th><td>
					<select name="genre">
						<c:forEach  var="book" items="${bookMngList}">
							<option value="${book.genre}">${book.genre}</option>
						</c:forEach>
					</select>			
					</td>
				</tr>
				<tr><th>가격</th><td><input type="number"	name="price"		required="required"></td></tr>
				<tr><th>아트번호</th><td>
					<select name="artno">
						<c:forEach var="art" items="${artMngList}">
							<option value="${art.artno}">${art.artname}</option>
						</c:forEach>
					</select>			
					</td>
				</tr>
				
				<tr><td colspan="2"><input type="submit" value="확인"></td>
				</tr> 
						
			</table>
		 </form>
</body>
</html>