<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>책정보</h2>
	<form action="updateBook" method="post">
		<input type="hidden" name="bookno" value="${book.bookno }">
		<table>
			
			<tr><th>책 번호(PK)</th><td>${book.bookno }</td></tr>
			<tr><th>책 이름</th><td>
					<input type="text" name="bookname"	required="required" value="${book.bookname }"></td></tr>
			<tr><th>작가</th><td>
					<input type="text" name="writer"	required="required" value="${book.writer}"></td></tr>
			<tr><th>장르</th><td>		
					<input type="text" name="genre" required="required" value="${book.genre }"></td></tr>
			<tr><th>가격</th><td>
					<input type="number" name="price"	required="required" value="${book.price }"></td></tr>
			<tr><th>아트번호</th><td>
					<input type="number" name="artno"	required="required" value="${book.artno }"></td></tr>	
							
		
			<tr>
				<td colspan="2">
					<input type="submit" value="확인"> 
				</td>
			</tr>	
		</table>
	</form>
</body>
</html>