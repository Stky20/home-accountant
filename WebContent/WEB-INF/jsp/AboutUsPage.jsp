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

	<title>About Economy Group Corporation Inc.</title>

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

	<div class="container" style="margin-top: 70px;">
		<div>
			<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					  <!-- Indicators -->
					  <ol class="carousel-indicators">
						    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
						    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
						    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
					  </ol>
					
					  <!-- Wrapper for slides -->
					  <div class="carousel-inner" role="listbox">
						    <div class="item active">
						      <img src="img/Carusel1.jpg" alt="">
						      <div class="carousel-caption">
							    	<h3>Начни зарабатывать на разумной экономии</h3>								
						      </div>
						    </div>
						    <div class="item">
						      <img src="img/Carusel2.jpg" alt="">			      
						      <div class="carousel-caption">			        
								    <h3>Составь свою стратегию</h3>								    
						      </div>
						    </div>
						    <div class="item">
						      <img src="img/Carusel3.jpg" alt="">			      
						      <div class="carousel-caption">			        
								    <h3 style="color: green;">Трать на главное, а не на излишнее</h3>								    
						      </div>
						    </div>
					  </div>
					
					  <!-- Controls -->
					  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
						    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						    <span class="sr-only">Previous</span>
					  </a>
					  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
						    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						    <span class="sr-only">Next</span>
					  </a>
			</div>
		</div>
	
		<h1>О компании</small></h1>
		<p class="text-justify">Economy Group Corporation Inc. - это молодая компания. 
		В настоящее время группой разработчиков реализуется несколько проектов,  направленных на повышение экономической грамотности людей. 
		В Economy Group Corporation Inc. есть четкое видение цели и пути к ней. Недавно начав свою деятельность, уже охватили значительную 
		долю рынка в сфере разработки ПО. Продукцией компании пользуются в нескольких странах.</p>
		<h2>История компании</h2>
		<p class="text-justify">Основана в 2017 году в Республике Беларусь интузиастом в сфере JAVA разработки желающим сделать этот мир лучше. В течение года 
		численность работников увеличилась до 20 человек, в течение следующего года планируется увеличить численность работников в 3 раза. В 2018 году плаируется начало 3 новых проектов. </p>
				
		<h1>О проекте</small></h1>
		<p class="text-justify">HomeAccountant поможет Вам наладить учет личных средств. HomeAccountant прекрасно справится и 
		с ведением управленческого учета небольшого предприятия или предпринимателя. Небольшая и эффективная программа 
		обладает огромным потенциалом и позволит решить большинство Ваших задач по учету.</p>

		<p class="text-justify">HomeAccountant - сложная программа, но она будет понятна любому пользователю потому что по 
		умолчанию большинство возможностей программы выключено. Любая дополнительная возможность может быть включена в 
		два-три клика мышки.</p>
	
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