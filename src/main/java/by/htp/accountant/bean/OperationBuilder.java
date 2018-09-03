package by.htp.accountant.bean;

import java.time.LocalDate;

public class OperationBuilder {
	
	private int id;
	private int role;
	private int operationTypeId;
	private double amount;
	private String remark;
	private LocalDate date;
	private int userId;
	
	private OperationBuilder() {
		
	}
	
	public OperationBuilder buildId(int id) {
		this.id = id;
		return this;
	}

	public OperationBuilder buildRole(int role) {
		this.role = role;
		return this;
	}
	
	public OperationBuilder buildOperationType(int operationTypeId) {
		this.operationTypeId = operationTypeId;
		return this;
	}
	
	public OperationBuilder buildAmount(double amount) {
		this.amount = amount;
		return this;
	}
	
	public OperationBuilder buildRemark(String remark) {
		this.remark = remark;
		return this;
	}
	
	public OperationBuilder buildDate(LocalDate date) {
		this.date = date;
		return this;
	}
	
	public OperationBuilder buildUserId(int userId) {
		this.userId = userId;
		return this;
	}
	
	public Operation buildOperation() {
		Operation operation = new Operation();
		if (id != 0) operation.setId(id);
		if (role != 0) operation.setRole(role);
		if (operationTypeId != 0) operation.setOperationTypeId(operationTypeId);
		if (amount != 0) operation.setAmount(amount);
		if (remark != null) operation.setRemark(remark);
		if (date != null) operation.setDate(date);
		if (userId != 0) operation.setUserId(userId);
		return operation;		
		
	}	

}
