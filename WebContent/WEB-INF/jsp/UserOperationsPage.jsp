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
<title>Operations</title>

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
<div class="container" style="margin-top:100px; text-align: center;">

	<div>
		<a href="/home-accountant-version-00/Controller?command=go_to_user_account_page" class="btn btn-default btn-lg"><<= Назад</a>
	</div>
		
	<div class="row">
		<div class="col-md-2">
			<div class="list-group">
				<button type="submit" class="list-group-item">${requestScope.operationType} - за текущий день</button>
				<button type="submit" class="list-group-item">${requestScope.operationType} - за текущую неделю</button>
				<button type="submit" class="list-group-item">${requestScope.operationType} -за текущий месяц</button>
				<button type="button" class="list-group-item">${requestScope.operationType} - за текущий год</button>				
				<button type="button" class="list-group-item" data-toggle="modal" data-target="#dateChooseModal">Указать период для операции - ${requestScope.operationType}</button>					
				<button type="submit" class="list-group-item" style="background: #FA8072;">В диаграммах</button>				
			</div>	
		</div>
		<div class="col-md-10" style="text-align: center;">
				<c:if test="${not empty requestScope.operationsList}">
						<c:set var="countOperations" value="1"/>
						<table class="table table-hover table-bordered">
	  						<h3>
	  							Операции по статье ${requestScope.operationType}
	  							<h4>
	  								За 
				   					<strong>
				   						<c:if  test="${not empty requestScope.firstDate}">
					   							<c:out value=" период с ${requestScope.firstDate} по "/>
				   						</c:if>
				   						<c:if  test="${not empty requestScope.lastDate}">
					   						<c:out value="${requestScope.lastDate}"/>
				   						</c:if>
				   					</strong>
				   			 		:				   				
				   				</h4>	  						
	  						</h3>
	  						<tr class="info">
	  							<th>№</th>
								<th>Cумма в BYR</th>
								<th>Дата</th>
								<th>Пояснение</th>
								<th></th>
								<th></th>							
							</tr>				
							<c:forEach var="oneOperation" items="${requestScope.operationsList}">
								<tr class="active">
									<td>				        
						       			<c:out value="${countOperations}" />
						       			<c:set var="countSpendings" value="${countOperations+1}"/>
						    		</td>
									<td>${oneOperation.amount}</td>						
									<td>${oneOperation.date}</td>
									<td>${oneOperation.remark}</td>
									<td>
										<button type="button" class="btn btn-link btn-xs" onclick="setParamsInTypeEditModal('${oneSpending.id}','${oneSpending.operationType}','${oneSpending.role}')">										
											Редактировать
										</button>
									</td>	
									<th>Удалить</th>								
								</tr>
							</c:forEach>		
						</table>
					</c:if>
					<c:if test="${empty requestScope.operationsList}">
						<div class="alert alert-success" role="alert">За указанный период операций не было!!!</div>
					</c:if>
	
	
	
	
	
	<!-- Pagination -->
	<div class="container" style="text-align:center">
		<c:if test="${requestScope.amountOfPages > 1}" >
			<nav aria-label="Page navigation">
				<ul class="pagination">
					<c:choose>																						
						<c:when test="${requestScope.amountOfPages == 2}">
							<c:if test="${requestScope.pageNumber == 1}">
								<li class="disabled active">
									<a href="#" aria-label="Previous">
										<span aria-hidden="true">&laquo;</span>
									</a>
								</li>
								<li>
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber + 1}" aria-label="Next">
										command
										pageNumber
										typeId
										firstDate
										lastDate
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
								<li class="disabled active">
									<a href="#" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:if>
						</c:when>
						<c:when test ="${requestScope.amountOfPages > 2 && requestScope.amountOfPages <= 5}">
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
														
							<c:forEach var="i" begin="${1}" end="${requestScope.amountOfPages}">
								<c:if test="${requestScope.pageNumber == i}">
									<li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:if>
								<c:if test="${requestScope.pageNumber != i}">
									<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
								</c:if>
							</c:forEach>
							
							<c:if test="${requestScope.pageNumber == requestScope.amountOfPages}">
								<li class="disabled">
									<a href="#" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:if>
							
							<c:if test="${requestScope.pageNumber < requestScope.amountOfPages}">
								<li>
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber+1}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:if>
							
						</c:when>
						
						<c:when test ="${requestScope.amountOfPages > 5}">
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
						
							<c:if test="${requestScope.pageNumber==1}">
								<c:forEach var="i" begin="${requestScope.pageNumber}" end="${requestScope.pageNumber+4}">
									<c:if test="${requestScope.pageNumber == i}">
										<li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>
									<c:if test="${requestScope.pageNumber != i}">
										<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
							</c:if>
							
							<c:if test="${requestScope.pageNumber==2}">
								<c:forEach var="i" begin="${requestScope.pageNumber-1}" end="${requestScope.pageNumber+3}">
									<c:if test="${requestScope.pageNumber == i}">
										<li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>
									<c:if test="${requestScope.pageNumber != i}">
										<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>								</c:forEach>
							</c:if>							
							
							<c:if test="${requestScope.pageNumber>2 && (requestScope.amountOfPages - requestScope.pageNumber)>=2}">
								<c:forEach var="i" begin="${requestScope.pageNumber-2}" end="${requestScope.pageNumber+2}">
									<c:if test="${requestScope.pageNumber == i}">
										<li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>
									<c:if test="${requestScope.pageNumber != i}">
										<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>								</c:forEach>
							</c:if>													
							
							<c:if test="${requestScope.pageNumber>2 && (requestScope.amountOfPages - requestScope.pageNumber)==1}">
								<c:forEach var="i" begin="${requestScope.pageNumber-3}" end="${requestScope.amountOfPages}">
									<c:if test="${requestScope.pageNumber == i}">
										<li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>
									<c:if test="${requestScope.pageNumber != i}">
										<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>								</c:forEach>
							</c:if>
																												
							<c:if test="${requestScope.pageNumber>2 && requestScope.pageNumber == requestScope.amountOfPages}">
								<c:forEach var="i" begin="${requestScope.pageNumber-4}" end="${requestScope.amountOfPages}">
									<c:if test="${requestScope.pageNumber == i}">
										<li class="active"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>
									<c:if test="${requestScope.pageNumber != i}">
										<li class="${i}"><a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${i}">${i}</a></li>
									</c:if>								</c:forEach>
							</c:if>						
							
							<c:if test="${requestScope.pageNumber == requestScope.amountOfPages}">
								<li class="disabled">
									<a href="#" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</li>
							</c:if>
							
							<c:if test="${requestScope.pageNumber < requestScope.amountOfPages}">
								<li>
									<a href="/home-accountant-version-00/Controller?command=go_to_user_administration_page&role=${requestScope.usersList[0].role}&pageNumber=${requestScope.pageNumber+1}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
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
</body>
</html>