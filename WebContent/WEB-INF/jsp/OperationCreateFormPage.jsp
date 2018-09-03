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
<title>Operation Form</title>

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
	<form action="Controller" method="post">
		<input type="hidden" name="command" value="create_operation">
		<div class="radio">
			<label>
			<input type="radio" name="type" id="optionsRadios1" value="spending" checked>
				Данная операция относится к расходам
			</label>
		</div>
		<div class="radio">
			<label>
			<input type="radio" name="type" id="optionsRadios2" value="income">
				Данная операция относится к доходам
			</label>
		</div>
		
		<select class="form-control" name="spending">
			<c:forEach var="oneSpending" items="${sessionScope.spendingTypesList}">>
				<option>oneSpending.operationType</option>				
			</c:forEach>
		</select>
		
		<select class="form-control" name="income">
			<c:forEach var="oneIncome" items="${sessionScope.incomeTypesList}">>
				<option>oneIncome.operationType</option>				
			</c:forEach>
		</select>
				
		<select class="form-control" name="count">
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>4</option>
			<option>5</option>
		</select>
		
		<div class="form-group">
   			 <label for="exampleInputEmail1">Введите сумму:</label>
			 <input type="text" class="form-control" id="exampleInputAmount" name="amount" placeholder="Сумма">
		</div>
		
		<textarea class="form-control" rows="3" placeholder="Пояснения" name="remark"></textarea>
		<div>
		<input type="date" name="date">
		</div>
		<div>
		<input type="submit" class="btn btn-default">	
		</div>
	
	</form>

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