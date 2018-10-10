package by.htp.accountant.dao;

import java.util.List;

import by.htp.accountant.bean.OperationType;
import by.htp.accountant.exception.DAOException;

public interface OperationTypeDAO {

	List<OperationType> getUserOperationTypesDependingOnTypeRole(int userId, int operationTypeRole) throws DAOException;
	
	OperationType getUserUndeletableOperationTypesDependingOnTypeRole(int userId, int undeletableOperationTypeRole) throws DAOException;
	
	boolean createOperationType(OperationType type) throws DAOException;
	
	boolean editOperationType(int typeId, String operationType) throws DAOException;
	
	boolean deleteOperationType(int typeId) throws DAOException;
	
	int getTypeIdOnUserIdAndOperationType(int userId, String operationType) throws DAOException;
	
	
}
