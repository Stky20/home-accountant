package by.htp.accountant.bean;

import java.io.Serializable;

public class OperationType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String operationType;
	private int role;
	private int userId;
	
	public OperationType() {
		this.role = 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
		result = prime * result + role;
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
		OperationType other = (OperationType) obj;
		if (id != other.id)
			return false;
		if (operationType == null) {
			if (other.operationType != null)
				return false;
		} else if (!operationType.equals(other.operationType))
			return false;
		if (role != other.role)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OperationType [id=" + id + ", operationType=" + operationType + ", role=" + role + ", userId=" + userId
				+ "]";
	}
	
	
	
}
