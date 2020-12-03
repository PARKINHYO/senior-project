<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<html>
<head>
<title>어린이집 유아 관리 시스템</title>

<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<!--Autor: Gabriel Ramirez | Diseñador Web
    Tema:Login Class="Gabriel".
    Fecha:11/01/2019.
    pagina Web: https://www.tonygabriel.ga/
    -->

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">

<!-- <meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1"> -->
<!--====================================================================================================================================-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<!--====================================================================================================================================-->
<!-- <link rel="stylesheet" href="css/style.css"> -->
<!--====================================================================================================================================-->

</head>

<script type="text/javascript">
	$(document).ready(function() {
		$("#logoutBtn").on("click", function() {
			location.href = "cafe/logout";
		})

	})

	$(document).ready(function() {
		$("#memberRegisterBtn").on("click", function() {
			location.href = "cafe/register";
		})

	})
	$(document).ready(function() {
		$("#memberUpdateBtn").on("click", function() {
			location.href = "cafe/memberUpdateView";
		})
	})
	$(document).ready(function() {
		$("#memberDeleteBtn").on("click", function() {
			location.href = "cafe/memberDeleteView";
		})
	})
	$(document).ready(function() {
		$("#boardBtn").on("click", function() {
			location.href = "board/list.do";
		})
	})
	$(document).ready(function() {
		$("#childManagerBtn").on("click", function() {
			location.href = "child/list";
		})
	})
	$(document).ready(function() {
		$("#parentManagerBtn").on("click", function() {
			location.href = "parent/list";
		})
	})
</script>

<body>
	<div align="center">
		<br> <br>
		<h2>유아 관리 시스템</h2>
	</div>

	<div class="container">
		<br>
		<div class="row">
			<div class="col">
				<img src="resources/img/loginform.jpg" width="550" height="408"
					class="Avatar">
			</div>
			<div class="col">
				<br> <br> <br> <br> <br> <br>
				<button id="childManagerBtn"
					class="btn btn-success btn-lg btn-block" type="button">
					<!--<i class="fas fa-share-square"></i>-->
					아이관리
				</button>
				<button id="parentManagerBtn"
					class="btn btn-success btn-lg btn-block" type="button">
					<!--<i class="fas fa-share-square"></i>-->
					부모관리
				</button>
			</div>
		</div>

		<br>
	</div>
	<br>
	<br>

	<!--====================================FOOOOOOOOTER==============================================-->
	<footer class="page-footer font-small green">
		<!-- <a href="https://www.tonygabriel.ga/" class="btn btn-info btn-sm"
			target="_blank">Soporte</a> -->
		<!-- Copyright -->
		<div class="footer-copyright text-center py-3">
			© 2020 Copyright: <a
				href="https://github.com/PARKINHYO/senior-project" target="_blank">박인효,
				양현용, 한승훈</a>
			<footer>
				Contact: <a href="mailto:inhyopark122@gmail.com">Inhyo Park</a>
			</footer>
		</div>
		<!-- Fin de Copyright -->
	</footer>
	<!--==============================FINDE FOOOTER==============================================-->


	<!--====================================================================================================================================-->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js"
		integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k"
		crossorigin="anonymous"></script>
	<!--====================================================================================================================================-->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<!--====================================================================================================================================-->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js"
		integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut"
		crossorigin="anonymous"></script>
</body>
</html>