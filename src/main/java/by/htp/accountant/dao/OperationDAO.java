package by.htp.accountant.dao;

import java.time.LocalDate;
import java.util.List;

import by.htp.accountant.bean.Operation;
import by.htp.accountant.bean.OperationType;
import by.htp.accountant.exception.DAOException;

public interface OperationDAO {
	
	boolean createOperation(Operation operation) throws DAOException;
	
	List<Operation> getAllOperationsDuringPeriod(int userId, LocalDate from, LocalDate till) throws DAOException;
	
	List<Operation> getAllOperationsAtDate(int userId, LocalDate date) throws DAOException;
	
	int getOneTypeOperationsNumberOfPagesAtDate(int typeID, LocalDate date) throws DAOException;
	
	int getOneTypeOperationsNumberOfPagesBetweenDates(int typeID, LocalDate firstDate, LocalDate lastDate) throws DAOException;
	
	List<Operation> getOneTypeOperationsAtDate(int typeId, LocalDate date, int startingFrom, int operationsAmount) throws DAOException;
	
	List<Operation> getOneTypeOperationsBetweenDates(int typeId, LocalDate firstDate, LocalDate lastDate, int startingFrom, int operationsAmount) throws DAOException;
	
	boolean deleteOperationById(int operationId) throws DAOException;
	
	boolean editOperation(int operationId, double amout, String remark, LocalDate date) throws DAOException;	
	
	
	

}
