<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Image : ${savedName}<p>
	UpLoad Image : <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${savedName}"><p>

	<a href="uploadFileDelete?delFile=${savedName}">upLoad 삭제</a>
</body>
</html>