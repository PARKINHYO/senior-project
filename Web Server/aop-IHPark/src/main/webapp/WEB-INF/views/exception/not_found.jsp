<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DataNotFoundException</title>
</head>
<body>
	<h1>DataNotFoundException</h1>  
	<c:url value="/member/list" var="url"/>
	<a href="${url}">학생목록  화면가기</a>
</body>
</html>