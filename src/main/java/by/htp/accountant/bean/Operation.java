package by.htp.accountant.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class Operation implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String pattern = "#.##";
	
	private int id;	
	private int operationTypeId;
	private double amount;
	private String remark;
	private LocalDate date;
	private int userId;
	
	public Operation() {		
		date = LocalDate.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getOperationTypeId() {
		return operationTypeId;
	}

	public void setOperationTypeId(int operationTypeId) {
		this.operationTypeId = operationTypeId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public static double roundDoubleCalculations(double culculations) {
		DecimalFormat df = new DecimalFormat(pattern);
		double result = 0;
		String resultInString = df.format(culculations).replace(",", ".");
		result = Double.parseDouble(resultInString);
		
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + operationTypeId;
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (operationTypeId != other.operationTypeId)
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Operation [id=" + id + ", operationTypeId=" + operationTypeId + ", amount=" + amount + ", remark="
				+ remark + ", date=" + date + ", userId=" + userId + "]";
	}
	
	
}
