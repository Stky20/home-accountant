<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Contacts</title>
<%@ include file="inclusion/LocaleTags.jsp"%>
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
		integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
		crossorigin="anonymous">
	
	<!-- Optional theme -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
		integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp"
		crossorigin="anonymous">	
	
	<!-- <link rel="stylesheet" href="css/bootstrap.css"> -->
	<link rel="stylesheet" href="css/main.css">
</head>
<body style="background: url(img/Contacts.png) no-repeat center top; background-attachment:fixed;">
<%@ include file="inclusion/NavigationBar.jsp"%> 

	<div class="container" style="margin-top: 440px; text-align: center; background-color:white; padding:0;">
	
		<div class="panel panel-primary">
		<div class="panel-heading">Для коммерческих предложений обращаться:</div>
		<div class="panel-body">
			<a href="#" >homeaccountant@gmail.com</a>
		</div>
	</div>
	
	<h1 class="bg-danger">Контакты в соцсетях:</h1>
	
	<div class="panel panel-success">
		<div class="panel-heading">Вконтакте</div>
		<div class="panel-body">
			<a href="#" >https://vk.com/homeaccountant2018</a>
		</div>
	</div>
	<div class="panel panel-success">
		<div class="panel-heading">Facebook</div>
		<div class="panel-body">
			<a href="#" >https://www.facebook.com/homeaccountant2018</a>
		</div>
	</div>
	<div class="panel panel-success">
		<div class="panel-heading">Одноклассники</div>
		<div class="panel-body">
			<a href="#" >https://ok.ru/homeaccountant2018</a>
		</div>
	</div>
	<div class="panel panel-success">
		<div class="panel-heading">Viber</div>
		<div class="panel-body">
			+ 1 - 801 555 8796
		</div>
	</div>
	<div class="panel panel-success">
		<div class="panel-heading">Telegramm</div>
		<div class="panel-body">
			+ 1 - 801 555 8796
		</div>
	</div>
		

	  
	</div>


<!-- Footer -->
	<%@ include file="inclusion/Footer.jsp"%> 

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	
	<!-- Latest compiled and minified JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>

<!-- 	<script	src="js/bootstrap.min.js"></script> -->

</body>
</html>