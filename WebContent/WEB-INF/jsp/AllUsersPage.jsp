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

<title>All Users</title>
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

<div class="row"></div>
	
	<div>
		<div class="list-group">
			<button type="button" class="list-group-item">Cras justo odio</button>
			<button type="button" class="list-group-item">Dapibus ac facilisis in</button>
			<button type="button" class="list-group-item">Morbi leo risus</button>
			<button type="button" class="list-group-item">Porta ac consectetur ac</button>
			<button type="button" class="list-group-item">Vestibulum at eros</button>
		</div>
	</div>
	
</div>
	
	
	<c:set var="pages" scope="page" value="10" />
	<c:set var="rest" scope="page" value="0" />
	<div class="container">
		
            <input type="text" id="datepicker">
            <input type="date" >
            <nav aria-label="Page navigation">
  				<ul class="pagination">
		            <c:forEach var="i" begin="0" end="${pages }" step="1">
		   				 <li><a href="#">${i}</a></li>
					</c:forEach>
				</ul>
			</nav>
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