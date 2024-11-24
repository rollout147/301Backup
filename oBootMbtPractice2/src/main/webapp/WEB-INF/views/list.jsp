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
<h1>책 관리 </h1>

	<a href="writeFormBook">writeFormBook 입력</a><p>
	<a href="writeFormEmp3">writeFormBook3 입력(Validation)</a>

<h3>사원수 : ${totalBook }</h3>
	<p>uptCnt	수정시 전달 Message : ${uptCnt} <p>
	<p>kk3		수정시 전달 Message : ${kk3}<p>
	
	<form action="listSearch3">
		<select name="search">
				<option value="s_genre">업무조회</option>
		</select>
		
		<input type="text" name="keyword" placeholder="keyword를 입력하세요">
		<button type="submit">keyword 검색</button><p>
	</form>
	

	<c:set var="num" value="${page.total-page.start+1 }"></c:set>		
	
	<table>
		<tr>
			<th>번호</th> <th>책 번호(PK)</th> <th>책 이름</th> 
			<th>작가</th> <th>장르</th> <th>가격</th>
		</tr>
		<c:forEach var="book" items="${listBook }">
			<tr>
				<td>${num }</td><td>${book.bookno }</td>
				<td><a href="detailBook?bookno=${book.bookno }">${book.bookname }</a></td>
				<td>${book.writer }</td><td>${book.genre }</td><td>${book.price }
			</tr>
			<c:set var="num" value="${num - 1 }"></c:set>
		</c:forEach>	
	</table>
	
	<c:if test="${page.startPage > page.pageBlock }">
		<a href="listBook?currentPage=${page.startPage-page.pageBlock }">[이전]</a>	
	</c:if>
	<c:forEach var="i" begin="${page.startPage }" end="${page.endPage }">
		<a href="listBook?currentPage=${i}">[${i}]</a>
	</c:forEach>
	
	<c:if test="${page.endPage < page.totalPage }">
		<a href="listBook?currentPage=${page.startPage+page.pageBlock }">[다음]</a>
	</c:if>
</body>
</html>