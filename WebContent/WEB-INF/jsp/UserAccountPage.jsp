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
<title>User Account</title>

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

<script type="text/javascript">	
			
			function hideSpendingsShowIncome(){
				$("#spending").hide();				
				$("#income").show();
			}
								
			function hideIncomeShowSpending(){
				$("#income").hide();				
				$("#spending").show();
			}
			
			function openModal(modal, role, typeId){
				
				if(modal == "typeCreateModal"){
					$('#createRole').attr('value', role);
					$('#typeCreateModal').modal();
				}
				
				if(modal == "typeEditModal"){
					$('#editTypeId').attr('value', typeId);
					$('#typeEditModal').modal();
				}
			}
			
			function setParamsInTypeCreateModal(role){		
								
				if(role != "") $("#createRole").attr('value', role);				
				openTypeCreateModal();
			}
			
			function openTypeCreateModal(){		
				$('#typeCreateModal').modal();				
			}
			
			function setParamsInTypeEditModal(id, name, role){		
				
				if(id != "")$("#editTypeId").attr('value', id);
				if(id != "")$("#deleteTypeId").attr('value', id);
				if(role != "")$("#deleteTypeRole").attr('value', role);
				if(name != "")$('#editTypeName').attr('placeholder', name);
				
				openTypeEditModal();
			}
			
			function openTypeEditModal(){		
				$('#typeEditModal').modal();				
			}
			
</script>

</head>
<body onload="openModal('${requestScope.modal}', '${requestScope.role}', '${requestScope.typeId}')">
<%@ include file="inclusion/NavigationBar.jsp"%> 

<div class="container" style="margin-top:100px; text-align: center;">

	<div class="container" style="margin-bottom:20px;">
		<!-- Button trigger operationCreateModalmodal -->
		<button type="button" class="btn btn-success btn-lg" data-toggle="modal" data-target="#operationCreateModal">
			Добавить расходы/доходы
		</button>		
	</div>
	
	<div class="row">
		<div class="col-md-2">
			<div class="list-group">
				<button type="submit" class="list-group-item">За текущий день</button>
				<button type="submit" class="list-group-item">За текущую неделю</button>
				<button type="submit" class="list-group-item">За текущий месяц</button>
				<button type="button" class="list-group-item">За текущий год</button>				
				<button type="button" class="list-group-item" data-toggle="modal" data-target="#dateChooseModal">Указать период</button>					
				<button type="submit" class="list-group-item">Все операции за период без типов</button>
				<button type="submit" class="list-group-item" style="background: #FA8072;">В диаграммах</button>
				<button type="submit" class="list-group-item" style="background: #FA8072;">Долговые обязателства</button>
				
			</div>	
		</div>
		
		<div class="col-md-10" style="text-align: center;">
			<!--DIV of Balance-->
			<div>
				<c:if test="${not empty requestScope.balance}">
				
					<c:if test="${requestScope.balance < 0}">
					   	<div class="alert alert-warning" role="alert">
				   			<h4>Баланс за период 
				   				<strong><c:if  test="${not empty requestScope.firstDate}">
					   				<c:out value="${requestScope.firstDate}"/>
				   				</c:if>
				   				<c:if  test="${not empty requestScope.lastDate}">
					   				<c:out value="по ${requestScope.lastDate}"/>
				   				</c:if></strong>
				   			 составляет:				   				
				   			</h4>
				   			<h3>${requestScope.balance} BYR</h3>
				   		</div>							     	
					</c:if>
					<c:if test="${requestScope.balance >= 0}">
					   	<div class="alert alert-success" role="alert">
				   			<h4>Баланс за период 
				   				<c:if  test="${not empty requestScope.firstDate}">
					   				<c:out value="${requestScope.firstDate}"/>
				   				</c:if>
				   				<c:if  test="${not empty requestScope.lastDate}">
					   				<c:out value="по ${requestScope.lastDate}"/>
				   				</c:if>
				   			 составляет:</h4>
				   			<h3>${requestScope.balance} BYR</h3>
				   		</div>							     	
					</c:if>
				</c:if>
			</div>
			
			<div class="row">
			
				<div class="col-md-6">
					<div>
					<c:if test="${not empty requestScope.spendingTypesList}">
						<c:set var="countSpendings" value="1"/>
						<table class="table table-hover table-bordered" style="background-color: #FAEBD7">
	  						<h3>Расходы</h3>
	  						<tr class="danger">
	  							<th>№</th>
								<th>Тип</th>
								<th>%</th>
								<th><button type="button" class="btn btn-default btn-xs" onclick="setParamsInTypeCreateModal('${requestScope.spendingTypesList[0].role}')">+</button></th>							
							</tr>				
							<c:forEach var="oneSpending" items="${requestScope.spendingTypesList}">
								<tr class="warning">
									<td>				        
						       			<c:out value="${countSpendings}" />
						       			<c:set var="countSpendings" value="${countSpendings+1}"/>
						    		</td>
									<td>${oneSpending.operationType}</td>						
									<td>${oneSpending.percentOfAllTypes}</td>
									<td>
										<button type="button" class="btn btn-link btn-xs" onclick="setParamsInTypeEditModal('${oneSpending.id}','${oneSpending.operationType}','${oneSpending.role}')">										
											Редактировать
										</button>
									</td>									
								</tr>
							</c:forEach>		
						</table>
					</c:if>
					<c:if test="${empty requestScope.spendingTypesList}">
						<div class="alert alert-success" role="alert">За указанный период не было расходов!</div>
					</c:if>
					</div>
				</div>
				
				<div class="col-md-6">
					<div>
					<c:if test="${not empty requestScope.incomeTypesList}">
						<c:set var="countIncome" value="1"/>
						<table class="table table-hover table-bordered" style="background-color: #E0FFFF" >
	  						<h3>Доходы</h3>
	  						<tr class="success">
	  							<th>№</th>
								<th>Тип</th>
								<th>%</th>
								<th><button type="button" class="btn btn-default btn-xs" onclick="setParamsInTypeCreateModal('${requestScope.incomeTypesList[0].role}')">+</button></th>							
							</tr>				
							<c:forEach var="oneIncome" items="${requestScope.incomeTypesList}">
								<tr class="active">
									<td>				        
						       			<c:out value="${countIncome}" />
						       			<c:set var="countIncome" value="${countIncome+1}"/>
						    		</td>
									<td>${oneIncome.operationType}</td>						
									<td>${oneIncome.percentOfAllTypes}</td>
									<td>
										<button type="button" class="btn btn-link btn-xs" onclick="setParamsInTypeEditModal('${oneIncome.id}','${oneIncome.operationType}','${oneIncome.role}')">
											Редактировать
										</button>
									</td>									
								</tr>
							</c:forEach>		
						</table>
					</c:if>
					<c:if test="${empty requestScope.spendingTypesList}">
						<div class="alert alert-info" role="alert">За указанный период не было Доходов!</div>
					</c:if>
					</div>
				</div>
				
			</div>
			
		</div>
	</div>

</div>


<!-- operationCreateModal -->
<div class="modal fade" id="operationCreateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Создайте Новую Операцию!!!</h4>
      </div>
      <form action="Controller" method="post">
      	<div class="modal-body">
			<input type="hidden" name="command" value="create_operation">
				<p>Выберите тип операции:</p>
				<div class="radio">
					<label onclick="hideIncomeShowSpending()">
						<input type="radio" name="operationTypeRole" id="optionsRadios1" value="1" checked>Расход
					</label>
					<label onclick="hideSpendingsShowIncome()">
						<input type="radio" name="operationTypeRole" id="optionsRadios2" value="2">Доход
					</label>
				</div>			
			
			<select class="form-control" name="spending" id="spending">
				<c:forEach var="oneSpending" items="${sessionScope.spendingTypesList}">
					<option>${oneSpending.operationType}</option>				
				</c:forEach>
			</select>
		
			<select class="form-control" name="income" id="income" style="display:none;">
				<c:forEach var="oneIncome" items="${sessionScope.incomeTypesList}">
					<option>${oneIncome.operationType}</option>				
				</c:forEach>
			</select>			
				<c:if test="${not empty requestScope.wrongOperationTypeOrRole}">
				   	<font color="red"><c:out value="wrongOperationTypeOrRole" /></font>							     	
				</c:if>
		
			<div class="input-group" style="margin:20px 0 20px 0;; padding-left:100px;">
				<span class="input-group-addon" id="basic-addon1">Сумма Br:</span>
				<input type="text" class="form-control" name="amount" placeholder="123,25" aria-describedby="basic-addon1" pattern="\d+([,.]{1}\d{1,2})?" style="width: 200px;">
			</div>
				<c:if test="${not empty requestScope.emptyOperationAmount}">
				   	<font color="red"><c:out value="emptyOperationAmount" /></font>							     	
				</c:if>
				<c:if test="${not empty requestScope.wrongOperationAmount}">
				   	<font color="red"><c:out value="wrongOperationAmount" /></font>							     	
				</c:if>
				
			<p>Вы можете ввести пояснения:</p>
			<textarea class="form-control" rows="3" placeholder="Пояснения" name="remark"></textarea>
				<c:if test="${not empty requestScope.wrongOperationRemark}">
				   	<font color="red"><c:out value="wrongOperationRemark" /></font>							     	
				</c:if>
				
			<div style="margin:10px 0 10px 0;">
				Выберите дату операции: <input type="date" name="date" max="${sessionScope.date}">
			</div>	
				<c:if test="${not empty requestScope.wrongOperationDate}">
				   	<font color="red"><c:out value="wrongOperationDate" /></font>							     	
				</c:if>
				
      	</div>
      	<div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	<button type="submit" class="btn btn-primary">Сохранить</button>
      	</div>
      </form>
    </div>
  </div>
</div>

<!-- typeCreateModal -->
<div class="modal fade" id="typeCreateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Создайте новый тип!!!</h4>
      </div>
       	<div class="modal-body">
      		<form action="Controller" method="post">
				<input type="hidden" name="command" value="create_type">
				<input type="hidden" name="role" id="createRole" value="">
					<p>
						Введите название: <input type="text" name="operationType" placeholder="Название типа">
						<button type="submit" class="btn btn-primary">Создать</button>
					</p>
			</form>
				<c:if test="${not empty requestScope.wrongOperationType}">
				   	<font color="red"><c:out value="wrongOperationType" /></font>							     	
				</c:if>
				<c:if test="${not empty requestScope.emptyOperationType}">
				   	<font color="red"><c:out value="emptyOperationType" /></font>							     	
				</c:if>					
      	</div>     
    </div>
  </div>
</div>

<!-- typeEditModal -->
<div class="modal fade" id="typeEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static" data-keyboard="false">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Редактировать тип!!!</h4>
      </div>
      <form action="Controller" method="post">
      	<div class="modal-body" style="text-align: center;">
      		<form action="Controller" method="post">
				<input type="hidden" name="command" value="edit_type">
				<input type="hidden" name="typeId" id="editTypeId">
					<p>
						Введите название: <input type="text" name="operationType" id="editTypeName" placeholder="">
						<button type="submit" class="btn btn-primary">Переименовать</button>
					</p>
			</form>
			<c:if test="${not empty requestScope.wrongOperationType}">
			   	<font color="red"><c:out value="wrongOperationType" /></font>							     	
			</c:if>
			<c:if test="${not empty requestScope.emptyOperationType}">
			   	<font color="red"><c:out value="emptyOperationType" /></font>							     	
			</c:if><br/>
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#deleteTypeModal">Удалить тип</button>
      	</div>     
      </form>
    </div>
  </div>
</div>

<!-- dateChooseModal -->
<div class="modal fade bs-example-modal-sm" id="dateChooseModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-sm" role="document">
  	 <div class="modal-content">
  	 	<div style="text-align: center;">
  	 		<h2>Выберите даты!</h2>
    		<fotm action="Controller" method="post">
	    		<input type="hidden" name="command" value="go_to_user_account_page">
    			<p>C:<input type="date" name="fromDate" max="${sessionScope.date}"></p>
    			<p>По:<input type="date" name="tillDate" max="${sessionScope.date}"></p><br/>
    			<button type="button" class="btn btn-default btn-xs" data-dismiss="modal" style="margin-bottom: 10px;">Close</button>
    			<button type="submit" class="btn btn-primary btn-xs" style="margin-bottom: 10px;">Сохранить</button>
    		</fotm>
    	</div>
    </div>
  </div>
</div>

<!-- deleteTypeModal -->
<div class="modal fade bs-example-modal-sm" id="deleteTypeModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-sm" role="document">  	
    <div class="modal-content" style="text-align: center;">
    	<h4>Вы уверены?</h4>
    	<form action="Controller" method="post">
	    	<input type="hidden" name="command" value="delete_type">
	    	<input type="hidden" name="typeId" id="deleteTypeId">
	    	<input type="hidden" name="role" id="deleteTypeRole">
	    	<button type="button" class="btn btn-default btn-xs" data-dismiss="modal" style="margin-bottom: 10px;">Нет</button>
    		<button type="submit" class="btn btn-danger btn-xs" style="margin-bottom: 10px;">Да</button>
    	</form>
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