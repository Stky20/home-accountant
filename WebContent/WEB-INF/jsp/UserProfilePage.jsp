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
	
	<title>User profile</title>
	
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
		
		<script type="text/javascript">		
						
			function openModal(modal){
				if(modal != null)$('#myModal').modal();
			}			
								
			
		</script>
	
	<!-- <link rel="stylesheet" href="css/bootstrap.css"> -->
	<link rel="stylesheet" href="css/main.css">
</head>

<body onload="openModal(${requestScope.modal})">
<%@ include file="inclusion/NavigationBar.jsp"%> 
	
	<div class="container" style="margin-top: 100px;">
		
		<p class="bg-success" style="font-size:40px;">Профиль</p>
				<div classs="row">
						<div class="row col-md-8">
							<div class="col-md-5">
								<img src="img/user_img.png" alt="User" class="img-rounded">
							</div>
							
							<div class="col-md-7">
								<div class="panel panel-default">
										<div class="panel-heading">Логин</div>
										<div class="panel-body">
											<form class="form-inline" action="Controller" method="post">
												<input type="hidden" name="command" value="change_login" />												
											  	<div class="form-group">
											    	<label for="exampleInputName2">Логин</label>
											    	<input type="text" class="form-control" name="login" placeholder="${sessionScope.user.nickName}">
											  	</div>											  	
											  	<button type="submit" class="btn btn-default">Изменить</button>
											</form>											
										</div>
										<div class="panel-footer">
											<c:choose>
												<c:when test="${not empty requestScope.emptyLoginErrorMsg}">
													<div class="panel-footer">
														<font color="red"><c:out value="${profile_null_msg}" /></font>
													</div>
												</c:when>
												<c:when test="${not empty requestScope.wrongLoginErrorMsg}">
													<div class="panel-footer">
														<font color="red"><c:out value="${wrong_login_msg}" />	</font>	
													</div>					     	
												</c:when>
												<c:otherwise>												
												</c:otherwise>
											</c:choose>
										</div>
								</div>
							</div>
						</div>
						<div class="row col-md-4 ">
								<div class="panel panel-default">
										<div class="panel-heading">Изменение пароля / Удалить профиль</div>
									  	<div class="panel-body">
										    	<!-- Button trigger modal -->
												<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal1">
												  Изменить пароль
												</button>												
												
												<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal2">
												  	Удалить профиль
												</button>
												
												<!-- Modal1 -->
												<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
												  <div class="modal-dialog" role="document">
												    <div class="modal-content">
													      <div class="modal-header">
													       		 <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
													       		 <h4 class="modal-title" id="myModalLabel">Изменить пароль</h4>
													      </div>
												      
													      <form class="form-horizontal" action="Controller" method="post">
													      	  <input type="hidden" name="command" value="change_password" />
													      	  <input type="hidden" name="id" value="${sessionScope.user.id}"/>
														      <div class="modal-body">
														      		<div class="form-group">
																			<label for="login" class="col-sm-2 control-label">Старый пароль</label>
																			<div class="col-sm-10">
																					<input type="password" class="form-control" name="password" placeholder="Введите старый пароль">
																			</div>																																						
																	</div>	
																	<div class="form-group">
																			<label for="login" class="col-sm-2 control-label">Новый пароль</label>
																			<div class="col-sm-10">
																					<input type="password" class="form-control" name="new_password" placeholder="Введите новый пароль">
																			</div>																			
																	</div>	
																	<div class="form-group">
																			<label for="login" class="col-sm-2 control-label">Новый пароль</label>
																			<div class="col-sm-10">
																					<input type="password" class="form-control" name="new_password_again" placeholder="Повторите новый пароль">
																			</div>
																	</div>	
														      </div>
														      
														      <div class="modal-footer">
															  	  <div class="row">
															        <div class=" col-md-offset-6 col-md-3 ">
															        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
															        </div>
															        <div class="col-md-2">
																        <div class="form-group">																		
																			<button type="submit" class="btn btn-success" style="margin:0;">Сохранить пароль</button>																																				
																		</div>
																	</div>
																  </div>
														      </div>
														  </form>
												    </div>
												  </div>
												</div>
												
												<!-- Modal2 -->
												<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
												  <div class="modal-dialog" role="document">
												    <div class="modal-content">
												      <div class="modal-header">
												        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
												        <h4 class="modal-title" id="myModalLabel">Вы уверены, что хотите удалить профиль?</h4>
												      </div>
												      <div class="modal-body">
													      <div class="row">
														      <div class="col-md-2">
														      	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
														      </div>
														      <div class="col-md-3">
															      <form action="Controller" method="post">
															      		<input type="hidden" name="command" value="diactivate_user" />
															      		<input type="hidden" name="id" value="${sessionScope.user.id}" />
															      		<button type="submit" class="btn btn-danger">Удалить пользователя</button>
															      </form>
														      </div>
													      </div>
												      </div>												      
												    </div>
												  </div>
												</div>
									  	</div>
									  	<div class="panel-footer">
											<c:if test="${not empty requestScope.wrongPasswordErrorMsg}">
											     	<font color="red"><c:out value="${wrong_password}" /></font>							     	
											 </c:if>
											 <c:if test="${not empty requestScope.emptyPasswordErrorMsg}">
											     	<font color="red"><c:out value="${profile_password_null_msg}" /></font>
											 </c:if>
											 <c:if test="${not empty requestScope.differentNewPasswordErrorMsg}">
											     	<font color="red"><c:out value="${different_msg}" /></font>						     	
											 </c:if>
											 <c:if test="${not empty requestScope.passwordDoNotMatches}">	
											 		<font color="red"><c:out value="${wrong_password_msg}" /></font>																 		
											 </c:if>																														 
										</div>
								</div>								
						</div>
				</div>
			<div class="col-md-8 col-md-offset-3">
					<div class="panel panel-default">
						  <div class="panel-heading">Информация пользователя</div>
						  <div class="panel-body">				  		
						  		<form class="form-horizontal" action="Controller" method="post">
						  			<input type="hidden" name="command" value="change_user_info">
									  <div class="form-group">
									    <label for="name" class="col-sm-2 control-label">Имя</label>
									    <div class="col-sm-10">
									      <input type="text" class="form-control" name="name" placeholder="${sessionScope.user.name } ">
									      <c:choose>								 
												 <c:when test="${not empty requestScope.wrongNameErrorMsg}">
												     	<font color="red"><c:out value="${wrong_name_msg}" /></font>							     	
												 </c:when>
												 <c:otherwise>
												 		</br>
												 </c:otherwise>
											</c:choose>
									    </div>									    
									  </div>
									  
									  <div class="form-group">
									    <label for="surname" class="col-sm-2 control-label">Фамилия</label>
									    <div class="col-sm-10">
									      <input type="text" class="form-control" name="surname" placeholder="${sessionScope.user.surname } ">
									       <c:choose>								 
												 <c:when test="${not empty requestScope.wrongSurnameErrorMsg}">
												     	<font color="red"><c:out value="${wrong_surname_msg}" /></font>							     	
												 </c:when>
												 <c:otherwise>
												 		</br>
												 </c:otherwise>
											</c:choose>
									    </div>									   
									  </div>
									  
									  <div class="form-group">
									    <label for="eMail" class="col-sm-2 control-label">e-mail</label>
									    <div class="col-sm-10">
									      <input type="email" class="form-control" name="email" placeholder="${sessionScope.user.eMail } ">
									      <c:choose>								 
												 <c:when test="${not empty requestScope.wrongEmailErrorMsg}">
												     	<font color="red"><c:out value="${wrong_email_msg}" /></font>							     	
												 </c:when>											 
											</c:choose>
											<c:if test="${not empty requestScope.noChangesMsg}">
												<font color="green"><c:out value="${no_changes_msg}"/></font>
											</c:if>
									    </div>									    
									  <div class="form-group">
									  </div>
									  
									    <div class="col-sm-offset-2 col-sm-10">
									      <button type="submit" class="btn btn-default">Изменить</button>
									    </div>
									  </div>
								</form>
						  </div>
					</div>
			</div>
	</div>
	
	<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true" onclick="closeModal()">&times;</span></button>
		        <h4 class="modal-title" id="myModalLabel">Изменения внесены</h4>
		      </div>		      
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="closeModal()">Close</button>		        
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

<!-- 	<script	src="js/bootstrap.min.js"></script> -->

</body>
</html>