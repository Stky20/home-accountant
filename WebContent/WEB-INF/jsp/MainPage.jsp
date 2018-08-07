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

	<nav class="navbar navbar-default navbar-fixed-top">                                         														<!--наша панель/ менюшка, всегда на месте -->
      <div class="container">																															<!-- это ширина нашего проиложения -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">         <!--кнопка - появл при уменьшении экрана и прячет все меню -->
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/home-accountant-version-00/Controller?command=go_to_main_page" title="Main Page">
          	<h4 style="margin:0; padding:0;"><font color="red">H</font>A <small>v0.0</small></h4>																					<!--наше лого переход на главную страницу -->
          </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">																								<!--и есть все меню, которое прячется в кнопку -->
          <ul class="nav navbar-nav">																													<!--спец список для менюшки -->
            <li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_main_page">${nav_link_home}</a></li>
            <c:choose>
				<c:when test="${not empty sessionScope.user}">																							<!--если юзер в системе, то видна ссылка на свои расходы, если нет, то нет-->	
					<li><a href="/home-accountant-version-00/Controller?command=go_to_user_account_page">${nav_link_users}</a></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><a href="#" title="${nav_link_users_title}">${nav_link_users}</a></li>	
				</c:otherwise>
			</c:choose>             
            <li><a href="/home-accountant-version-00/Controller?command=go_to_about_page">${nav_link_about}</a></li>
            <li><a href="/home-accountant-version-00/Controller?command=go_to_contacts_page">${nav_link_contacts}</a></li>
            <li class="dropdown">																														<!--пункт менюшки, содержащий выпадающий список -->
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${nav_link_staff} 				<!--на ссылку нужно нажать для выпаданиявлож списка -->
              	<span class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li><a href="/home-accountant-version-00/Controller?command=go_to_slogon_page">${nav_link_slogan}</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">${nav_link_header}</li>
                <li><a href="https://www.google.com">Google</a></li>
                <li><a href="https://www.youtube.com/">YouTube</a></li>
                <li><a href="https://www.it-academy.by/">IT academy</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">           																							<!--часть менюшки, список располож в правой части экрана -->
            <c:choose>																							
				<c:when test="${not empty sessionScope.user}">																							<!--если юзер зарегистрирован,-->
					<li class="dropdown">																												<!--то виден этот выпадающий список -->
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							<c:choose>
								<c:when test="${not empty sessionScope.user.name}">																		<!--если юзер указывал имя, то в менюшке будет имя, если нет, то никнэйм-->
									<c:out value="${sessionScope.user.name} "/>							
								</c:when>
								<c:otherwise>
									<c:out value="${sessionScope.user.nickName} "/>	
								</c:otherwise>
							</c:choose> 
              				<span class="caret"></span>
              			</a>
              			<ul class="dropdown-menu">
                			<li><a href="#">${nav_link_edit}</a></li>
                			<li role="separator" class="divider"></li>
                			<li><a href="#">${nav_link_exit}</a></li>
              			</ul>						
					</li>
				</c:when>
				<c:otherwise>																																<!--если юзер не зарег-н то видна кнопка перехода на страницу логинации -->
					<li>
						<form action="Controller" method="post">
							<input type="hidden" name="command" value="go_to_login_page" /> 
							<input type="submit" name="login_page" value="${login_button}" class="btn btn-link" style="padding:0; margin:14px 20px 0 14px;"/>
						</form>
					</li>				
				</c:otherwise>
			</c:choose>
			
            <li class="dropdown">																															<!--дропдаун для смены языка -->
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${nav_link_language}<span class="caret"></span></a>
              <ul class="dropdown-menu">
              	<li><form action="Controller" method="get">
						<input type="hidden" name="command" value="localization" /> 
						<input type="hidden" name="localization" value="ru" /> 
						<input type="submit" class="btn btn-link" name="login" value="${ru_button}" />
					</form>
				</li>
                <li role="separator" class="divider"></li>
                <li><form action="Controller" method="get">
						<input type="hidden" name="command" value="localization" /> 
						<input type="hidden" name="localization" value="en" /> 
						<input type="submit" class="btn btn-link" name="login" value="${en_button}" />
					</form>
				</li>                
              </ul>
            </li>            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <div class="row" style="margin-top:150px;">
	    
	    <c:if test="${not empty requestScope.user}" >																								<!--панель ниже видня только когда юзер в системе -->											
		    <div class="col-xs-6 col-md-3 col-lg-2 sidebar-offcanvas" id="sidebar" style="width:190px; margin:0 0 0 15px; padding:0;">				<!--доп панель, нужно сделать только на странице просмотра счетов -->
		          <div class="list-group">
		            <a href="#" class="list-group-item active">Период времени</a>
		            <a href="#" class="list-group-item">День</a>
		            <a href="#" class="list-group-item">Месяц</a>
		            <a href="#" class="list-group-item">3 месяца</a>
		            <a href="#" class="list-group-item">6 месяцев</a>
		            <a href="#" class="list-group-item">Год</a>
		            <a href="#" class="list-group-item">за все время</a>
		          </div>
		    </div><!--/.sidebar-offcanvas-->
	    </c:if>
		
		<div class="container" >																														<!-- основная часть экрана, все будет в ней -->
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
	</div>
	
	
	
	<footer style="margin:100px 0 0 0; padding:0;">
      <div class="container">
        <p class="text-muted text-center">© 2018 Economy Group Corporation Inc.</p>
      </div>
    </footer>


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