<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Exception ERROR</title>
</head>
<body>
	예외 클래스 :	${exception.getClass().name} <br>
  	메시지 : 	${exception.message}
</body>
</html>