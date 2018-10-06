package by.htp.accountant.dao;

import java.util.List;

import by.htp.accountant.bean.OperationType;
import by.htp.accountant.exception.DAOException;

public interface OperationTypeDAO {

	public List<OperationType> getUserOperationTypesDependingOnTypeRole(int userId, int operationTypeRole) throws DAOException;
	public OperationType getUserUndeletableOperationTypesDependingOnTypeRole(int userId, int undeletableOperationTypeRole) throws DAOException;
	
	
}
