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

<title>User Administration</title>

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
<body onload="setPageNumber(${requestScope.pageNumber}); activatePageNumber();">

<%@ include file="inclusion/NavigationBar.jsp"%> 

<div class="container" style="margin-top:100px;">

	<div class="row">
	
		<div class="col-md-2">
			<div class="list-group">
				<form action="Controller" method="get">
					<input type="hidden" name="command" value="go_to_user_administration_page">
					<input type="hidden" name="role" value="2">
					<button type="submit" class="list-group-item">Пользователи</button>
				</form>	
				<form action="Controller" method="get">
					<input type="hidden" name="command" value="go_to_user_administration_page">
					<input type="hidden" name="role" value="1">
					<button type="submit" class="list-group-item">Администраторы</button>
				</form>	
				<form action="Controller" method="get">
					<input type="hidden" name="command" value="go_to_user_administration_page">
					<input type="hidden" name="role" value="0">
					<button type="submit" class="list-group-item">Неактивные пользователи</button>	
				</form>			
			</div>			
		</div>
		
		<div class="col-md-10">
									
			<c:set var="count" value="${(requestScope.pageNumber - 1) * 10}"/>		
			
			<table class="table table-hover table-bordered">
  				<tr>
  					<th>№</th>
					<th>Id</th>
					<th>Login</th>
					<th>Информация</th>
					<th>Повысить</th>
				</tr>
				<c:forEach var="oneUser" items="${requestScope.usersList}">
					<tr>
						<td>								
					        <c:out value="${count + 1 }"/>
					    </td>
						<td>${oneUser.id}</td>						
						<td>${oneUser.nickName}</td>
						<td>
							<button type="button" class="btn btn-primary btn-md" data-toggle="modal" onclick="return openModal(${oneUser.name}, ${oneUser.surname}, ${oneUser.eMail})">
								О пользователе
							</button>
						</td>
						<td>
							<form action="Controller" method="get">
								<input type="hidden" name="command" value="make_admin"/>
								<input type="hidden" name="id" value="${oneUser.id}"/>
								<input type="submit" class="btn btn-md btn-primary" value="Админ"/>						
							</form>
						</td>
					</tr>
				</c:forEach>		
			</table>
			
		</div>
		
		

		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" onclick="return closeModal()">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Информация о пользователе</h4>
		      </div>
		      <div class="modal-body">
			        <div class="panel panel-default">
						<div class="panel-body">
							<p id="1">Значение не установлено</p>
						</div>
					</div>
					 <div class="panel panel-default">
						<div class="panel-body">
							<p id="2">Значение не установлено</p>
						</div>
					</div>
					 <div class="panel panel-default">
						<div class="panel-body">
							<p id = "3">Значение не установлено</p>
						</div>
					</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="return closeModal()">Close</button>		        
		      </div>
		    </div>
		  </div>
		</div>
	
	</div>
	
	<div class="container" style="text-align: center"></div>
		<c:if test="${requestScope.amountOfPages > 1}" >
			<nav aria-label="Page navigation">
				<ul class="pagination">
					<c:choose>																						
						<c:when test="${requestScope.amountOfPages == 2}">
							<c:if test="${requestScope.pageNumber == 1}">
								<li class="disabled">
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber}" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
								<li>
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber + 1}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:if>
							<c:if test="${requestScope.pageNumber == 2}">
								<li>
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber - 1}" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
								<li class="disabled">
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:if>
						</c:when>
						<c:when test ="${requestScope.amountOfPages > 2}">
							<c:if test="${requestScope.pageNumber == 1}">
								<li class="disabled">
									<a href="#" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>								
							</c:if>
							<c:if test="${requestScope.pageNumber > 1}">
								<li>
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber-1}" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
							</c:if>
							
							<c:if test="${requestScope.pageNumber==1 && requestScope.amountOfPages<=5}">
								<c:forEach var="i" begin="${requestScope.pageNumber}" end="${requestScope.amountOfPages}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:forEach>
							</c:if>
							
							<c:if test="${requestScope.pageNumber==2 && requestScope.amountOfPages<=5}">
								<c:forEach var="i" begin="${requestScope.pageNumber-1}" end="${requestScope.amountOfPages}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:forEach>
							</c:if>
							
							<c:if test="${requestScope.pageNumber==2 && requestScope.amountOfPages>5}">
								<c:forEach var="i" begin="${requestScope.pageNumber-1}" end="${requestScope.pageNumber+3}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:forEach>
							</c:if>
							
							<c:if test="${requestScope.pageNumber>2 && (requestScope.amountOfPages - requestScope.pageNumber)>2}">
								<c:forEach var="i" begin="${requestScope.pageNumber-2}" end="${requestScope.pageNumber+2}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:forEach>
							</c:if>
							
							<c:if test="${requestScope.pageNumber>2 && (requestScope.amountOfPages - requestScope.pageNumber)==2}">
								<c:forEach var="i" begin="${requestScope.pageNumber-2}" end="${requestScope.amountOfPages}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:forEach>
							</c:if>
							
							<c:if test="${requestScope.pageNumber>2 && (requestScope.amountOfPages - requestScope.pageNumber)==1}">
								<c:forEach var="i" begin="${requestScope.pageNumber-3}" end="${requestScope.amountOfPages}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:forEach>
							</c:if>
							
							<c:if test="${requestScope.pageNumber == requestScope.amountOfPages}">
								<c:forEach var="i" begin="${requestScope.pageNumber-4}" end="${requestScope.amountOfPages}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:forEach>
							</c:if>						
							
							<c:if test="${requestScope.pageNumber == requestScope.amountOfPages}">
								<liclass="disabled">
									<a href="#" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:if>
							
							<c:if test="${requestScope.pageNumber < requestScope.amountOfPages}">
								<li>
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber+1}" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
							</c:if>
						</c:when>						
					</c:choose>	
				</ul>
			</nav>
		</c:if>
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

<script type="text/javascript">		
			
			var defaultInfo = "Значение не установлено";	
			var pageNumber;
			
			function openModal(name, surname, email){		
				if(name != null) document.getElementById("1").value=name;
				if(surname != null) document.getElementById("2").value=surname;
				if(email != null) document.getElementById("3").value=email;		
			}
			
			function closeModal(){
				var modalName = document.getElementById("1").value=defaultInfo;
				var modalSurname = document.getElementById("2").value=defaultInfo;
				var modaleMail =document.getElementById("3").value=defaultInfo;
			}			
						
			function setPageNumber(number){
				pageNumber = number;
			}
			
			function activatePageNumber(){
				$(#"${pageNumber}").attr("class", "active");				
			}
			
		
		</script>
<!-- 	<script	src="js/bootstrap.min.js"></script> -->
</body>
</html>