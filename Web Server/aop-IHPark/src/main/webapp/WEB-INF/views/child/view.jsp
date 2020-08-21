<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이 상세 보기</title>

<script>
	$(document).ready(function() {
		$("#btnOk").click(function() {
			location.href = "http://localhost:8080/ihpweb/child/list";
		});
	});
	$(document)
			.ready(
					function() {
						$("#btnLocation")
								.click(
										function() {
											location.href = "http://localhost:8080/ihpweb/location/listChdLoc?device_name=${dto.device_name}";
										});
					});
	$(document)
			.ready(
					function() {
						$("#btnRawLocation")
								.click(
										function() {
											location.href = "http://localhost:8080/ihpweb/location/listChdLocJson?device_name=${dto.device_name}";
										});
					});
	$(document)
	.ready(
			function() {
				$("#btnRawGyro")
						.click(
								function() {
									location.href = "http://localhost:8080/ihpweb/gyro/readJson?device_name=${dto.device_name}";
								});
			});
	$(document)
			.ready(
					function() {
						$("#btnGyro")
								.click(
										function() {
											location.href = "http://localhost:8080/ihpweb/gyro/read?device_name=${dto.device_name}";
										});
					});
	$(document).ready(function() {
		$("#btnDelete").click(function() {
			if (confirm("삭제하시겠습니까?")) {
				document.form1.action = "../board/delete.do";
				document.form1.submit();
			}
		});

		$("#btnUpdate").click(function() {
			var title = $("#b_title").val();
			var content = $("#b_main").val();
			// var writer = $("#writer_id").val();
			if (title == "") {
				alert("제목을 입력하세요");
				document.form1.b_title.focus();
				return;
			}
			if (content == "") {
				alert("내용을 입력하세요");
				document.form1.b_main.focus();
				return;
			}
			/* 			if (writer == "") {
			 alert("이름을 입력하세요");
			 document.form1.writer.focus();
			 return;
			 } */
			/* 	document.form1.action = "../board/update.do";
				document.form1.submit(); */
		});
	});
	$(document).ready(function() {
		// $('#characterLeft').text('0/45');
		$('#b_title').keyup(function() {
			var max = 45;
			var len = $(this).val().length;
			if (len >= max) {
				$('#characterLeft').text('You have reached the limit');
				$('#characterLeft').addClass('red');
				$('#btnSubmit').addClass('disabled');
			} else {
				var ch = len;
				$('#characterLeft').text(ch + '/45');
				$('#btnSubmit').removeClass('disabled');
				$('#characterLeft').removeClass('red');
			}
		});
	});
	$(document).ready(function() {
		// $('#characterLeft2').text('0/1000');
		$('#b_main').keyup(function() {
			var max = 1000;
			var len = $(this).val().length;
			if (len >= max) {
				$('#characterLeft2').text('You have reached the limit');
				$('#characterLeft2').addClass('red');
				$('#btnSubmit').addClass('disabled');
			} else {
				var ch = len;
				$('#characterLeft2').text(ch + '/1000');
				$('#btnSubmit').removeClass('disabled');
				$('#characterLeft2').removeClass('red');
			}
		});
	});
</script>
</head>
<body>
	<div class="container">
		<br> <br>
		<div class="row">
			<div class="col">
				<img src="../resources/img/boardwriteupdatedelete.jpg" width="550"
					height="710" class="Avatar">
			</div>
			<div class="col">
				<div>
					<form name="form1" method="post" action="../child/view">
						<div>
							<br style="clear: both">
							<div class="form-group col-md-12 ">
								<label id="messageLabel">기기 번호 : ${dto.device_name} </label>
							</div>
							<div class="form-group col-md-12 ">
								<label id="messageLabel">이름 : ${dto.username }</label>
							</div>
							<div class="form-group col-md-12 ">
								<label id="messageLabel" for="message">성별 : ${dto.sex}</label>
							</div>
							<div class="form-group col-md-12 ">
								<label id="messageLabel" for="message2">나이 : ${dto.age}</label>
							</div>
							<div class="form-group col-md-12 ">
								<label id="messageLabel" for="message3">주소 :
									${dto.address}</label>
							</div>
							<div class="form-group col-md-12 ">
								<label id="messageLabel" for="message3">유치원 :
									${dto.kindergarden}</label>
							</div>
							<div class="form-group col-md-12 ">
								<label id="messageLabel" for="message3">반 :
									${dto.classname}</label>
							</div>
							<div class="form-group col-md-12 ">
								<label id="messageLabel" for="message3">부모 아이디 :
									${dto.parentid}</label>
							</div>






						</div>
					</form>

					<div class="form-group col-md-12">
						<button class="btn btn-success btn-lg btn-block" id="btnOk"
							type="button">확인</button>
					</div>
					<div class="form-group col-md-12">
						<button class="btn btn-success btn-lg btn-block" id="btnLocation"
							type="submit">아이 위치</button>
					</div>
					<div class="form-group col-md-12">
						<button class="btn btn-success btn-lg btn-block" id="btnGyro"
							type="submit">아이 걸음 수</button>
					</div>
					<div class="form-group col-md-12">
						<button class="btn btn-success btn-lg btn-block"
							id="btnRawLocation" type="submit">Raw 데이터(GPS)</button>
					</div>
					<div class="form-group col-md-12">
						<button class="btn btn-success btn-lg btn-block" id="btnRawGyro"
							type="submit">Raw 데이터(Gyro)</button>
					</div>
				</div>
			</div>
		</div>

		<br>
	</div>
</body>
</html>