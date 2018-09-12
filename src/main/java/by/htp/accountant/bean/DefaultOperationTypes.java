package by.htp.accountant.bean;

public enum DefaultOperationTypes {
	
	HOME_SPENDINGS("home_spendings", 1),
	TRANSPORT_SPENDINGS("transport_spendings", 1),
	FOOD_SPENDINGS("food_spending", 1),
	CLOTHES_SPENDINGS("clothes_spendings",1),
	
	WAGE_INCOME("wage_income",2),
	GIFT_INCOME("gift_income",2),
	OTHER_INCOME("other_income",2);
	
	
	private final String operationType;
	private final int role;
	
	private DefaultOperationTypes(String operationType, int role) {
		this.operationType = operationType;
		this.role = role;
	}

	public String getOperationType() {
		return operationType;
	}

	public int getRole() {
		return role;
	}
	
	

}
