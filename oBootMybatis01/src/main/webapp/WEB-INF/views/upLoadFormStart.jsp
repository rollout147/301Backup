<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	UpLoad Image : <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${savedName}">
	
	<form id="form1" action="uploadForm" method="post" enctype="multipart/form-data">
							<!-- 이미지나 멀티미디어 파일은 꼭 post와 multipart/form-data로 해야함 -->
		<input type="file" name="file1"><p>
		<input type="text" name="title"><p>
		<input type="submit">	
	</form>
	
</body>
</html>