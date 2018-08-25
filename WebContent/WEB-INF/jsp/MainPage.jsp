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

<title>Home accountant v 0.0</title>

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

<body>

<%@ include file="inclusion/NavigationBar.jsp"%>     
	    
	    <c:choose>
				<c:when test="${sessionScope.user.role == 0}">
						<div class="container" style="margin-top: 100px; text-align: center; font-size: 20px;">
						    	<p class="bg-warning">Вы удалили свой аккаунт, в течении 5 дней он будет безвозвратно удален администратором. 
						    	До истечения этого срока вы можете восстановить доступ к своему аккаунту. </p>
				    	</div>
				</c:when>
				<c:otherwise>
						<div class="container" style="margin-top: 100px;">																														<!-- основная часть экрана, все будет в ней -->
								<div class="jumbotron" style="text-align: center; padding: 10px;">																										<!-- джамботрон с текстом на клавной странице -->
						        	
							        	<h2>Добро пожаловать в Вашу личную бухгалтерию!</h2><br>
							        	<p><font size="4">Используйте Домашнего бухгалтера – программу для ведения и контроля финансов как личного, так и семейного бюджета. Доступно на любом устройстве при наличии доступа в интернет.</font></p>
										<p><font size="4">Кроме учета личных финансов и контроля семейного бюджета, Домашняя бухгалтерия поможет вести финансовый учет индивидуальным предпринимателям и небольшим компаниям.</font></p>
										<p><font size="4">Программа позволит создать эффективный финансовый план, рассчитать доходы и расходы.</font></p>
							        	<p><form action="Controller" method="get">
												<input type="hidden" name="command" value="localization" /> 
												<input type="hidden" name="localization" value="en" /> 
												<input type="submit" class="btn btn-success btn-lg" name="login" value="Приступить!" />
											</form></p>
								</div>
								
								<div class="container" style="text-align: center; margin-bottom: 50px;"><h3>Причины начать пользоваться Домашним бухгалтером уже сегодня:</h3></div>
								
								<div class="container">
									<div class="row">
										<div class="col-lg-3" >
											<h4>Простота и практичность</h4>
											<p class="text-danger">Не нужно обладать никакими специальными бухгалтерскими знаниями.</p>
											<p>Полный набор функций, необходимых для контроля семейного бюджета.</p>
											<p>
												<a class="btn btn-primary" href="#" role="button">Подробней
													&raquo;</a>
											</p>
										</div>
										<div class="col-lg-3">
											<h4>Польза</h4>
											<p>Ведение учета домашних финансов поможет в достижении целей.</p>
											<p>
												<a class="btn btn-primary" href="#" role="button">View details
													&raquo;</a>
											</p>
										</div>
										<div class="col-lg-3">
											<h4>Выгода</h4>
											<p>Анализируя свой бюджет, можно избежать излишних расходов.</p>
											<p>
												<a class="btn btn-primary" href="#" role="button">View details
													&raquo;</a>
											</p>
										</div>
										<div class="col-lg-3">
											<h4>Безопасность</h4>
											<p>Защита записей паролем и функция резервного копирования базы данных.</p>
											<p>
												<a class="btn btn-primary" href="#" role="button">View details
													&raquo;</a>
											</p>
										</div>
									</div>
								</div>
						</div>		
				</c:otherwise>
		</c:choose>
	    		

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