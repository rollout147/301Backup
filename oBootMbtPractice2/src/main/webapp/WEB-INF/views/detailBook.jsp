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
<h2>책 정보</h2>
	<table>
		<tr><th>책 번호(PK)</th><td>${book.bookno }</td></tr>
		<tr><th>책 이름</th><td>${book.bookname }</td></tr>
		<tr><th>작가</th><td>${book.writer }</td></tr>
		<tr><th>장르</th><td>${book.genre }</td></tr>
		<tr><th>가격</th><td>${book.price }</td></tr>
		<tr><th>아트번호</th><td>${book.artno }</td></tr>
		
		
		<tr><td colspan="2">
			<input type="button" value="목록"
				onclick="location.href='listBook'">
			<input type="button" value="수정"
				onclick="location.href='updateFormBook?bookno=${book.bookno}'">
			<input type="button" value="삭제"
				onclick="location.href='deleteBook?bookno=${book.bookno}'">
			</td>
		</tr>
	</table>

</body>
</html>