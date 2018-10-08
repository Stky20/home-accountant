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
			
			function openModal(operationCreateModal){
				if(operationCreateModal != null)$('#operationCreateModal').modal();
			}
</script>

</head>
<body onload="openModal(${requestScope.operationCreateModal})">
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
				<button type="submit" class="list-group-item">Один день</button>
				<button type="submit" class="list-group-item">Текущая неделя</button>
				<button type="submit" class="list-group-item">Текущий месяц</button>
				<button type="submit" class="list-group-item">Текущий год</button>
				<button type="submit" class="list-group-item">В диаграммах</button>	<button type="submit" class="list-group-item">В диаграммах</button>						
				<form action="Controller" method="get">
					<input type="hidden" name="command" value="go_to_edit_operationtype_form">
					<button type="submit" class="list-group-item list-group-item-info">Указать период</button>
				</form>
				<button type="submit" class="list-group-item">В диаграммах</button>
				<button type="submit" class="list-group-item">Долговые обязателства</button>	
			</div>	
		</div>
		<div class="col-md-10">
			
			
		</div>
	</div>

</div>


<!-- operationCreateModal -->
<div class="modal fade" id="operationCreateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
				<c:if test="${not empty requestScope.wrongOperationDate}">
				   	<font color="red"><c:out value="wrongOperationDate" /></font>							     	
				</c:if>
				
			<p>Вы можете ввести пояснения:</p>
			<textarea class="form-control" rows="3" placeholder="Пояснения" name="remark"></textarea>
				<c:if test="${not empty requestScope.wrongOperationRemark}">
				   	<font color="red"><c:out value="wrongOperationRemark" /></font>							     	
				</c:if>
				
			<div style="margin:10px 0 10px 0;">
				Выберите дату операции: <input type="date" name="date" max="${sessionScope.date}" placeholder="dd.mm.yyyy">
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