package by.htp.accountant.dao;

import java.time.LocalDate;
import java.util.List;

import by.htp.accountant.bean.Operation;
import by.htp.accountant.bean.OperationType;
import by.htp.accountant.exception.DAOException;

public interface OperationDAO {
	
	public boolean createOperation(Operation operation) throws DAOException;
	
	public List<Operation> getAllOperationsDuringPeriod(int userId, LocalDate from, LocalDate till) throws DAOException;
	
	public List<Operation> getAllOperationsAtDate(int userId, LocalDate date) throws DAOException;
	
	
	
	
	boolean editOperation(int oldOperationId, Operation newOperation) throws DAOException;
	
	boolean removeOperation(int operationId) throws DAOException;
	
	List<Operation> showAllOperations(int role, int operationsNumber, int startingFrom, LocalDate fromDate, LocalDate tilDate, int userId) throws DAOException;
	
	List<Operation> showAllOperationsAtOneDay(int role, int operationsNumber, int startingFrom, LocalDate date, int userId) throws DAOException;
	
	int countAllPages(int role, int userId, int numberOfOperationsOnPage, LocalDate firstDate, LocalDate tilDate) throws DAOException;
	
	int countAllPagesAtOneDay(int role, int userId, int numberOfOperationsOnPage, LocalDate date) throws DAOException;
	
	List<Operation> showOneTypeOperations(int role, int userId, int operationType, int operationsNumber, int startingFrom, LocalDate fromDate, LocalDate tilDate) throws DAOException;
	
	List<Operation> showOneTypeOperationsAtOneDay(int role, int userId, int operationTypeId, int operationsNumber, int startingFrom, LocalDate date) throws DAOException;
	
	int countOneTypePages(int role, int userId, int operationsTypeId, int numberOfOperationsOnPage, LocalDate fromDate, LocalDate tilDate) throws DAOException;
	
	int countOneTypePagesAtOneDay(int role, int userId, int operationsTypeId, int numberOfOperationsOnPage, LocalDate date) throws DAOException;
	
	boolean useDefaultOperationTypes(int userId) throws DAOException;
	
	List<OperationType> getAllOperationTypes(int userId) throws DAOException;
	

}
