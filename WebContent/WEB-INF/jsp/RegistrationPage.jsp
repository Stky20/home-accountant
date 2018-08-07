<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ include file="inclusion/LocaleTags.jsp"%>

<title>${loginpage_link_registration} HA</title>

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

<body>

	<div class="container" style="margin-top:200px;">
		<form class="form-horizontal" action="Controller" method="post">
			<input type="hidden" name="command" value="registration" />
			<div class="form-group">
				<label for="login" class="col-sm-2 control-label">${login_placeholder}:*</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="login" placeholder="${login_placeholder}">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-sm-2 control-label">${password_placeholder}:*</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password" placeholder="${password_placeholder}">
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">${name}:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name" placeholder="${name}">				
				</div>
			</div>
			<div class="form-group">
				<label for="surname" class="col-sm-2 control-label">${surname}:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="surname" placeholder="${surname}">
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-2 control-label">Email:</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" id="email" placeholder="Email">
				</div>
			</div>
			<div class="form-group">
				<div class="row">
					<div class="col-sm-offset-2 col-sm-10 col-md-1" style="margin-right:80px;">
						<button type="submit" class="btn btn-success" style="margin:0;">${registration_button}</button>
					</div>					
				</div>
				<p class="text-danger" style="margin:20px 0 0 200px;">*- ${registration_error}</p>
			</div>
		</form>
		<div class="row">
			<div class="col-sm-offset-2 col-sm-10 col-md-3"></div>
			<div class="col-sm-offset-2 col-sm-10 col-md-2" style="margin:0 8px 0 0; padding:0;">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="go_to_main_page" /> 
					<input type="submit" name="login_page" value="<-- ${nav_link_home}" class="btn btn-link" style="margin:0;"/>
				</form>
			</div>
			<div class="col-sm-offset-2 col-sm-10 col-md-3" style="margin:0; padding:0;"">
				<form action="Controller" method="post">
					<input type="hidden" name="command" value="go_to_login_page" /> 
					<input type="submit" name="login_page" value=" ${login_button}-->" class="btn btn-link" style="margin:0;"/>
				</form>
			</div>
		</div>
	</div>

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
</body>
</html>