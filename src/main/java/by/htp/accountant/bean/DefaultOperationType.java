package by.htp.accountant.bean;

public enum DefaultOperationType {
	
	SPENDING_HOME("spending.home", 1),
	SPENDING_TRANSPORT("spending.transport", 1),
	SPENDING_FOOD("spending.food", 1),
	SPENDING_CLOTHES("spending.clothes", 1),
	SPENDING_OTHER("spending.other", 1),
	
	INCOME_WAGE("income.wage", 2),
	INCOME_DEPOSIT("income.deposit", 2),
	INCOME_OTHER("income.other", 2);		
	
	
	private final String operationTypeKey;
	private final int role;
	
	private DefaultOperationType(String operationTypeKey, int role) {
		this.operationTypeKey = operationTypeKey;
		this.role = role;
	}

	public String getOperationTypeKey() {
		return operationTypeKey;
	}

	public int getRole() {
		return role;
	}

}
