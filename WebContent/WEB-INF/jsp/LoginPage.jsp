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

<title>${loginpage_title}</title>

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
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">${loginpage_panel_title}</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="Controller" method="post">
                        	<input type="hidden" name="command" value="logination"/>
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="${login_placeholder}" name="login" type="text" value ="" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="${password_placeholder}" name="password" type="password" value="">
                                </div>

								<i class="error"> 
									<c:if test="${not empty requestScope.nullErrorMsg}">
										<c:out value="${null_login_password}" />
									</c:if>
									
									<c:if test="${not empty requestScope.nullHashErrorMsg}">
										<c:out value="${hash_password_problem}" />
									</c:if>
									
									<c:if test="${not empty requestScope.emptyLoginPasswordErrorMsg}">
										<c:out value="${empty_login_password}" />
									</c:if>
									
									<c:if test="${not empty requestScope.wrongLoginPasswordErrorMsg}">
										<c:out value="${wrong_login_password}" />
									</c:if>
								</i>
								
								<div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">${loginpage_checkbox}
                                    </label>
                                </div>
                                
                                <input type="submit" class="btn btn-lg btn-success btn-block" value="${loginpage_button}">
                            </fieldset>
                        </form>
                        <div class="row">
							<div class="col-md-3" style="margin-right:130px;" >
								<form action="Controller" method = "get">
									<input type="hidden" name="command" value="go_to_main_page"/>
									<input type="submit" class="btn btn-link" value="${nav_link_home}" />
								</form>
							</div>
							<div class="col-md-3" >
								<form action="Controller" method = "get">
									<input type="hidden" name="command" value="go_to_registration_page"/>
									<input type="submit" class="btn btn-link" value="${loginpage_link_registration}"/>
								</form>
							</div>
						</div>
                    </div>
                </div>
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

<!-- 	<script	src="js/bootstrap.min.js"></script> -->
</body>
</html>