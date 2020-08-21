<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<html>
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>부모 목록</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnWrite").click(function() {
			location.href = "http://localhost:8080/ihpweb/board/write.do";
		});
	});
	$(document).ready(function() {
		$("#btnHome").click(function() {
			location.href = "http://localhost:8080/ihpweb/";
		});
	});
</script>
</head>
<body>
	<div class="container">
		<div align="center" class="row">
			<br>
			<br>
			<br>
			<h2>부모 목록</h2>
			<div class="col">
				<br> <br> <br>
				<table class="table table-list-search">
					<thead>
						<tr>
							<th>아이디</th>
							<th>이름</th>
							<th>번호</th>
							<th>이메일</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="row" items="${list}">
							<tr>
								<td>${row.id}</td>
								<td>${row.username}</td>
								<td>${row.mobile}</td>
								<td>${row.email}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="col-md-2">
			<button class="btn btn-success btn-lg btn-block" type="button"
				id="btnHome">홈으로</button>
		</div>

		<br> <br>
	</div>

</body>
</html>