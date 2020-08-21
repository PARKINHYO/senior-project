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
<title>아이의 걸음 수</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnWrite").click(function() {
			location.href = "http://localhost:8080/ihpweb/board/write.do";
		});
	});

	function goBack() {
		window.history.back();
	}
</script>
</head>
<body>
	<div class="container">
		<div class="row" align="center">
			<br> <br> <br>
			<h2>아이의 걸음수(실시간 업데이트) 목록</h2>
			<div class="col-md-12">
				<br> <br> <br>


				<h3>걸음수 : ${dto.count}</h3>

			</div>

		</div>
		<div class="col-md-2">
			<button class="btn btn-success btn-lg btn-block" type="button"
				onclick="goBack();" id="btnHome">뒤로가기</button>
		</div>

		<br> <br> <br>
	</div>

</body>
</html>