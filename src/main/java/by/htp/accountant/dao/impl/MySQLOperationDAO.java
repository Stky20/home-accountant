package by.htp.accountant.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.bean.DefaultOperationType;
import by.htp.accountant.bean.Operation;
import by.htp.accountant.bean.OperationType;
import by.htp.accountant.bean.User;
import by.htp.accountant.dao.OperationDAO;
import by.htp.accountant.dao.connectionpool.ConnectionPool;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.SQLUserDAOException;


public class MySQLOperationDAO implements OperationDAO{
	
	private ConnectionPool connectionPool;
	
	private static final Logger logger = LoggerFactory.getLogger(MySQLOperationDAO.class);	
	
	private final static int ID_FIELD_IN_OPERATION_TABLE = 1;
	private final static int OPERATION_TYPE_ID_FIELD_IN_OPERATION_TABLE = 2;
	private final static int AMOUNT_FIELD_IN_OPERATION_TABLE = 3;
	private final static int REMARK_FIELD_IN_OPERATION_TABLE = 4;
	private final static int OPERATION_DATE_FIELD_IN_OPERATION_TABLE = 5;
	private final static int USER_ID_FIELD_IN_OPERATION_TABLE = 6;
	
	private final static String CREATE_OPERATION_QUERY = "INSERT INTO operations (operationTypeId, amount, remark, operationDate, user_id) VALUES (?, ?, ?, ?, ?);";
	private final static String GET_ALL_OPERATION_DURING_PERIOD_QUERY = "SELECT * FROM operations WHERE user_id=? AND operationDate >= ? AND operationDate <= ?;";
	private final static String GET_ALL_OPERATION_AT_DATE_QUERY = "SELECT * FROM operations WHERE user_id=? AND operationDate=? ;";

	
	
	private final static String EDIT_OPERATION_QUERY = "UPDATE operations SET operationTypeId=?, amount=?, remark=?, operationDate=?, user_id=? WHERE id=?;";
	private final static String REMOVE_OPERATION_QUERY = "DELETE FROM operations WHERE id = ?;";
	private final static String SHOW_ALL_OPERATION_QUERY = "SELECT * FROM operations WHERE role=? AND operationDate>? AND operationDate<? AND user_id=? LIMIT ?,?;";
	private final static String SHOW_ALL_OPERATION_ONE_DAY_QUERY = "SELECT * FROM operations WHERE role=? AND operationDate=? AND user_id=? LIMIT ?,?;";
	private final static String SHOW_ONE_TYPE_OPERATION_QUERY = "SELECT * FROM operations WHERE role=? AND user_id=? AND operationTypeId=?  AND operationDate>? AND operationDate<? LIMIT ?,?;";
	private final static String SHOW_ONE_TYPE_OPERATION_ONE_DAY_QUERY = "SELECT * FROM operations WHERE role=? AND user_id=? AND operationTypeId=? AND operationDate=? LIMIT ?,?;";
	private final static String COUNT_ALL_TYPES_PAGES_QUERY = "SELECT count(*) FROM operations WHERE role=? AND user_id=? AND operationTypeId=? AND operationDate>? AND operationDate<?;";
	private final static String COUNT_ALL_TYPES_PAGES_ONE_DAY_QUERY = "SELECT count(*) FROM operations WHERE role=? AND user_id=? AND operationDate=?;";
	private final static String COUNT_ONE_TYPE_PAGES_QUERY = "SELECT count(*) FROM operations WHERE role=? AND user_id=? AND operationTypeId=? AND operationDate>? AND operationDate<?;";
	private final static String COUNT_ONE_TYPE_PAGES_ONE_DAY_QUERY = "SELECT count(*) FROM operations WHERE role=? AND user_id=? AND operationTypeId=? AND operationDate=?;";
	private final static String USE_DEFAULT_OPERATION_QUERY = "INSERT INTO operation_types (operationType, user_Id, role) VALUES (?, ?, ?);";
	private final static String GET_ALL_USERS_OPERATION_TYPES_QUERY = "SELECT * FROM operation_types WHERE user_Id=?;";


	
	public MySQLOperationDAO() {
		connectionPool = ConnectionPool.getInstance();
	}	
	

	@Override
	public boolean createOperation(Operation operation) throws DAOException {
		int addedRowsInBase = 0;
		
		try (Connection connection = connectionPool.takeConnection()){
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE_OPERATION_QUERY)){
							
				preparedStatement.setInt(1, operation.getOperationTypeId());
				preparedStatement.setDouble(2, operation.getAmount());
				preparedStatement.setString(3, operation.getRemark());
				preparedStatement.setDate(4, Date.valueOf(operation.getDate()));
				preparedStatement.setInt(5, operation.getUserId());
				addedRowsInBase = preparedStatement.executeUpdate();
			}
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO to create operation", e);
		} catch (SQLException e) {
			throw new DAOException ("Can`t create statement or execute query in OperationDAO createOperation() method", e);
		}	
		
		if(addedRowsInBase == 1) {
			return true;
		} else {
			logger.info("Somehow during creation operation executeUpdate() method returner " + addedRowsInBase);
			return false;
		}
	}
	
	
	@Override
	public List<Operation> getAllOperationsDuringPeriod(int userId, LocalDate from, LocalDate till) throws DAOException {
		
		List<Operation> operationsDuringPeriod = new ArrayList<Operation>();
		
		try (Connection connection = connectionPool.takeConnection(); 
			PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_OPERATION_DURING_PERIOD_QUERY)){	
			
			preparedStatement.setInt(1, userId);
			preparedStatement.setDate(2, Date.valueOf(from));
			preparedStatement.setDate(3, Date.valueOf(till));
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){				
				while(resultSet.next())	{
					Operation operation = new Operation();
					operation.setId(resultSet.getInt(ID_FIELD_IN_OPERATION_TABLE));  		
					operation.setOperationTypeId(resultSet.getInt(OPERATION_TYPE_ID_FIELD_IN_OPERATION_TABLE)); 
					operation.setAmount(resultSet.getDouble(AMOUNT_FIELD_IN_OPERATION_TABLE));
					operation.setDate(resultSet.getDate(OPERATION_DATE_FIELD_IN_OPERATION_TABLE).toLocalDate());
					operation.setRemark(resultSet.getString(REMARK_FIELD_IN_OPERATION_TABLE));
					operation.setUserId(resultSet.getInt(USER_ID_FIELD_IN_OPERATION_TABLE));
					
					operationsDuringPeriod.add(operation);
				}
			}			
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in MySQLOperationDAO to getAllOperationsDuringPeriod()", e);
		} catch (SQLException e) {
			throw new DAOException ("Can`t create statement or create resultSet or execute query in "
					+ "MySQLOperationDAO getAllOperationsDuringPeriod() method", e);
		}			
		
		return operationsDuringPeriod;
	}


	@Override
	public List<Operation> getAllOperationsAtDate(int userId, LocalDate date) throws DAOException {

		List<Operation> operationsAtDate = new ArrayList<Operation>();
		
		try (Connection connection = connectionPool.takeConnection(); 
			PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_OPERATION_AT_DATE_QUERY)){	
			
			preparedStatement.setInt(1, userId);
			preparedStatement.setDate(2, Date.valueOf(date));			
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){				
				while(resultSet.next())	{
					Operation operation = new Operation();
					operation.setId(resultSet.getInt(ID_FIELD_IN_OPERATION_TABLE));  		
					operation.setOperationTypeId(resultSet.getInt(OPERATION_TYPE_ID_FIELD_IN_OPERATION_TABLE)); 
					operation.setAmount(resultSet.getDouble(AMOUNT_FIELD_IN_OPERATION_TABLE));
					operation.setDate(resultSet.getDate(OPERATION_DATE_FIELD_IN_OPERATION_TABLE).toLocalDate());
					operation.setRemark(resultSet.getString(REMARK_FIELD_IN_OPERATION_TABLE));
					operation.setUserId(resultSet.getInt(USER_ID_FIELD_IN_OPERATION_TABLE));
					
					operationsAtDate.add(operation);
				}
			}			
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in MySQLOperationDAO to getAllOperationsAtDate()", e);
		} catch (SQLException e) {
			throw new DAOException ("Can`t create statement or create resultSet or execute query in "
					+ "MySQLOperationDAO getAllOperationsAtDate() method", e);
		}			
		
		return operationsAtDate;		
	}	
	
	
	
	
	
	
	
	
	
	

	@Override
	public boolean editOperation(int oldOperationId, Operation newOperation) throws DAOException {
		
		int changedRowsInBase;
		
		try (Connection connection = connectionPool.takeConnection()){
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(EDIT_OPERATION_QUERY)){	
								
				preparedStatement.setInt(1, newOperation.getOperationTypeId());
				preparedStatement.setDouble(2, newOperation.getAmount());
				preparedStatement.setString(3, newOperation.getRemark());
				preparedStatement.setDate(4, Date.valueOf(newOperation.getDate()));
				preparedStatement.setInt(5, newOperation.getUserId());
				preparedStatement.setInt(6, oldOperationId);
				
				changedRowsInBase = preparedStatement.executeUpdate();			
				
				if(preparedStatement.executeUpdate() == 1) return true;
				
			}	
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in UserDAO to edit user", e);
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or execute query in UserDAO editUser() method", e);
		}	
		logger.info("Somehow during edition operation executeUpdate() method returner " + changedRowsInBase);
		return false;
	}

	
	
	@Override
	public boolean removeOperation(int operationId) throws DAOException {
		
		int changedRowsInBase;
		
		try (Connection connection = connectionPool.takeConnection()){
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_OPERATION_QUERY)){	
				
				preparedStatement.setInt(1, operationId);
								
				changedRowsInBase = preparedStatement.executeUpdate();			
				
				if(preparedStatement.executeUpdate() == 1) return true;
				
			}	
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in UserDAO to delete user", e);
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or execute query in UserDAO deleteUser() method", e);
		}	
		logger.info("Somehow during deleting operation executeUpdate() method returner " + changedRowsInBase);
		return false;
		
	}

	
	
	@Override
	public List<Operation> showAllOperations(int role, int operationsNumber, int startingFrom, LocalDate fromDate,
			LocalDate tilDate, int userId) throws DAOException {
		
//		List<Operation> operationsList = new ArrayList<Operation>();
//		
//		try (Connection connection = connectionPool.takeConnection()){				
//			
//				try(PreparedStatement prepareStatement = connection.prepareStatement(SHOW_ALL_OPERATION_QUERY)){
//					prepareStatement.setInt(1, role);
//					prepareStatement.setDate(2, Date.valueOf(fromDate));
//					prepareStatement.setDate(3, Date.valueOf(tilDate));
//					prepareStatement.setInt(4, userId);
//					prepareStatement.setInt(5, startingFrom);
//					prepareStatement.setInt(6, operationsNumber);
//					
//					try(ResultSet resultSet = prepareStatement.executeQuery()){
//						while(resultSet.next()) {
//							Operation operation = new Operation();
//							operation.setId(resultSet.getInt(1));
//							operation.setRole(resultSet.getInt(2));
//							operation.setOperationTypeId(resultSet.getInt(3));
//							operation.setAmount(resultSet.getDouble(4));
//							operation.setRemark(resultSet.getString(5));
//							operation.setDate(resultSet.getDate(6).toLocalDate());
//							operation.setUserId(resultSet.getInt(7));
//						}
//					}
//				}							
//				
//		} catch (SQLException e) {
//			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO showAllOperations() method", e);
//		} catch (InterruptedException e) {
//			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO showAllOperations() method", e);			
//		}
//				
//		return operationsList;	
		return null;
	}
	
	@Override
	public List<Operation> showAllOperationsAtOneDay(int role, int operationsNumber, int startingFrom, LocalDate date,
			int userId) throws DAOException {
		
//		List<Operation> operationsList = new ArrayList<Operation>();
//		
//		try (Connection connection = connectionPool.takeConnection()){			
//				
//				try(PreparedStatement prepareStatement = connection.prepareStatement(SHOW_ALL_OPERATION_ONE_DAY_QUERY)){
//					prepareStatement.setInt(1, role);
//					prepareStatement.setDate(2, Date.valueOf(date));
//					prepareStatement.setInt(3, userId);
//					prepareStatement.setInt(4, startingFrom);
//					prepareStatement.setInt(5, operationsNumber);
//					
//					try(ResultSet resultSet = prepareStatement.executeQuery()){
//						while(resultSet.next()) {
//							Operation operation = new Operation();
//							
//							operation.setId(resultSet.getInt(1));
//							operation.setRole(resultSet.getInt(2));
//							operation.setOperationTypeId(resultSet.getInt(3));
//							operation.setAmount(resultSet.getDouble(4));
//							operation.setRemark(resultSet.getString(5));
//							operation.setDate(resultSet.getDate(6).toLocalDate());
//							operation.setUserId(resultSet.getInt(7));
//							
//							operationsList.add(operation);
//						}
//					}
//				}							
//				
//		} catch (SQLException e) {
//			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO showAllOperations() method", e);
//		} catch (InterruptedException e) {
//			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO showAllOperations() method", e);			
//		}
//				
//		return operationsList;	
		return null;
	}
	

	@Override
	public int countAllPages(int role, int userId, int numberOfOperationsOnPage, LocalDate fromDate, LocalDate tilDate) throws DAOException {
		
		int amountOfOperationsInPeriod;
		
		try (Connection connection = connectionPool.takeConnection()){			
			
			try(PreparedStatement prepareStatement = connection.prepareStatement(COUNT_ALL_TYPES_PAGES_QUERY)){
				prepareStatement.setInt(1, role);
				prepareStatement.setInt(2, userId);
				prepareStatement.setDate(3, Date.valueOf(fromDate));
				prepareStatement.setDate(4, Date.valueOf(tilDate));
								
				try(ResultSet resultSet = prepareStatement.executeQuery()){
					resultSet.next();
					
					amountOfOperationsInPeriod = resultSet.getInt(1);
				}
			}							
			
		} catch (SQLException e) {
			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO countAllPages() method", e);
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO countAllPages() method", e);			
		}
		
		int amountOfPages = amountOfOperationsInPeriod/numberOfOperationsOnPage;
		
		if((amountOfOperationsInPeriod % numberOfOperationsOnPage) != 0) amountOfPages++;
		
		return amountOfPages;
	
	}
	
	@Override
	public int countAllPagesAtOneDay(int role, int userId, int numberOfOperationsOnPage, LocalDate date) throws DAOException {
		
		int amountOfOperationsInDate;
		
		try (Connection connection = connectionPool.takeConnection()){			
			
			try(PreparedStatement prepareStatement = connection.prepareStatement(COUNT_ALL_TYPES_PAGES_ONE_DAY_QUERY)){
				prepareStatement.setInt(1, role);
				prepareStatement.setInt(2, userId);
				prepareStatement.setDate(3, Date.valueOf(date));				
								
				try(ResultSet resultSet = prepareStatement.executeQuery()){
					resultSet.next();
					
					amountOfOperationsInDate = resultSet.getInt(1);
				}
			}							
			
		} catch (SQLException e) {
			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO countAllPages() method", e);
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO countAllPages() method", e);			
		}
		
		int amountOfPages = amountOfOperationsInDate/numberOfOperationsOnPage;
		
		if((amountOfOperationsInDate % numberOfOperationsOnPage) != 0) amountOfPages++;
		
		return amountOfPages;
	}



	@Override
	public List<Operation> showOneTypeOperations(int role, int userId, int operationType, int operationsNumber,
			int startingFrom, LocalDate fromDate, LocalDate tilDate) throws DAOException {
		
//		List<Operation> operationsList = new ArrayList<Operation>();
//		
//		try (Connection connection = connectionPool.takeConnection()){				
//			
//				try(PreparedStatement prepareStatement = connection.prepareStatement(SHOW_ONE_TYPE_OPERATION_QUERY)){
//					prepareStatement.setInt(1, role);
//					prepareStatement.setInt(2, userId);
//					prepareStatement.setInt(3, operationType);
//					prepareStatement.setDate(4, Date.valueOf(fromDate));
//					prepareStatement.setDate(5, Date.valueOf(tilDate));
//					prepareStatement.setInt(6, startingFrom);
//					prepareStatement.setInt(7, operationsNumber);					
//					
//					try(ResultSet resultSet = prepareStatement.executeQuery()){
//						while(resultSet.next()) {
//							Operation operation = new Operation();
//							
//							operation.setId(resultSet.getInt(1));
//							operation.setRole(resultSet.getInt(2));
//							operation.setOperationTypeId(resultSet.getInt(3));
//							operation.setAmount(resultSet.getDouble(4));
//							operation.setRemark(resultSet.getString(5));
//							operation.setDate(resultSet.getDate(6).toLocalDate());
//							operation.setUserId(resultSet.getInt(7));
//							
//							operationsList.add(operation);
//						}
//					}
//				}							
//				
//		} catch (SQLException e) {
//			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO showOneTypeOperations() method", e);
//		} catch (InterruptedException e) {
//			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO showOneTypeOperations() method", e);			
//		}
//				
//		return operationsList;	
		return null;
	}



	@Override
	public List<Operation> showOneTypeOperationsAtOneDay(int role, int userId, int operationTypeId, int operationsNumber,
			int startingFrom, LocalDate date) throws DAOException {
		
//		List<Operation> operationsList = new ArrayList<Operation>();
//		
//		try (Connection connection = connectionPool.takeConnection()){			
//				
//				try(PreparedStatement prepareStatement = connection.prepareStatement(SHOW_ONE_TYPE_OPERATION_ONE_DAY_QUERY)){
//					prepareStatement.setInt(1, role);
//					prepareStatement.setInt(2, userId);
//					prepareStatement.setInt(3, operationTypeId);
//					prepareStatement.setDate(4, Date.valueOf(date));
//					prepareStatement.setInt(5, startingFrom);
//					prepareStatement.setInt(6, operationsNumber);
//					
//					try(ResultSet resultSet = prepareStatement.executeQuery()){
//						while(resultSet.next()) {
//							Operation operation = new Operation();
//							
//							operation.setId(resultSet.getInt(1));
//							operation.setRole(resultSet.getInt(2));
//							operation.setOperationTypeId(resultSet.getInt(3));
//							operation.setAmount(resultSet.getDouble(4));
//							operation.setRemark(resultSet.getString(5));
//							operation.setDate(resultSet.getDate(6).toLocalDate());
//							operation.setUserId(resultSet.getInt(7));
//							
//							operationsList.add(operation);
//						}
//					}
//				}							
//				
//		} catch (SQLException e) {
//			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO showOneTypeOperationsAtOneDay() method", e);
//		} catch (InterruptedException e) {
//			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO showOneTypeOperationsAtOneDay() method", e);			
//		}
//				
//		return operationsList;	
		return null;
	}



	@Override
	public int countOneTypePages(int role, int userId, int operationsTypeId, int numberOfOperationsOnPage,
			LocalDate fromDate, LocalDate tilDate) throws DAOException {

		int amountOfOperationsInPeriod;
		
		try (Connection connection = connectionPool.takeConnection()){			
			
			try(PreparedStatement prepareStatement = connection.prepareStatement(COUNT_ONE_TYPE_PAGES_QUERY)){
				prepareStatement.setInt(1, role);
				prepareStatement.setInt(2, userId);
				prepareStatement.setInt(3, operationsTypeId);
				prepareStatement.setDate(3, Date.valueOf(fromDate));
				prepareStatement.setDate(4, Date.valueOf(tilDate));
								
				try(ResultSet resultSet = prepareStatement.executeQuery()){
					resultSet.next();
					
					amountOfOperationsInPeriod = resultSet.getInt(1);
				}
			}							
			
		} catch (SQLException e) {
			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO countOneTypePages() method", e);
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO countOneTypePages() method", e);			
		}
		
		int amountOfPages = amountOfOperationsInPeriod/numberOfOperationsOnPage;
		
		if((amountOfOperationsInPeriod % numberOfOperationsOnPage) != 0) amountOfPages++;
		
		return amountOfPages;
		
	}



	@Override
	public int countOneTypePagesAtOneDay(int role, int userId, int operationsTypeId, int numberOfOperationsOnPage,
			LocalDate date) throws DAOException {

		int amountOfOperationsInDate;
		
		try (Connection connection = connectionPool.takeConnection()){			
			
			try(PreparedStatement prepareStatement = connection.prepareStatement(COUNT_ONE_TYPE_PAGES_ONE_DAY_QUERY)){
				prepareStatement.setInt(1, role);
				prepareStatement.setInt(2, userId);
				prepareStatement.setInt(3, operationsTypeId);
				prepareStatement.setDate(4, Date.valueOf(date));				
								
				try(ResultSet resultSet = prepareStatement.executeQuery()){
					resultSet.next();
					
					amountOfOperationsInDate = resultSet.getInt(1);
				}
			}							
			
		} catch (SQLException e) {
			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO countOneTypePagesAtOneDay() method", e);
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO countOneTypePagesAtOneDay() method", e);			
		}
		
		int amountOfPages = amountOfOperationsInDate/numberOfOperationsOnPage;
		
		if((amountOfOperationsInDate % numberOfOperationsOnPage) != 0) amountOfPages++;
		
		return amountOfPages;		
	}



	@Override
	public boolean useDefaultOperationTypes(int userId) throws DAOException {
		
		int addedRowsInBase = 0;
		DefaultOperationType[] defaultOperationTypeList = DefaultOperationType.values();
		
		try (Connection connection = connectionPool.takeConnection()){
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(USE_DEFAULT_OPERATION_QUERY)){
			
				for(int i = 0; i < defaultOperationTypeList.length; i++) {
					preparedStatement.setString(1, defaultOperationTypeList[i].getOperationTypeKey());
					preparedStatement.setInt(2, userId);
					preparedStatement.setInt(3, defaultOperationTypeList[i].getRole());
					
					addedRowsInBase = addedRowsInBase + preparedStatement.executeUpdate();
				}
			}
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO to create operation", e);
		} catch (SQLException e) {
			throw new DAOException ("Can`t create statement or execute query in OperationDAO createOperation() method", e);
		}	
		
		if(addedRowsInBase == defaultOperationTypeList.length) {
			return true;
		} else {
			logger.info("Somehow during initialising DefaultOperationTypes useDefaultOperationTypes() method returner " + addedRowsInBase + " instead of " + defaultOperationTypeList.length);
			return false;
		}		
	}



	@Override
	public List<OperationType> getAllOperationTypes(int userId) throws DAOException {
		return null;
//		List<OperationType> operationsList = new ArrayList<OperationType>();
//		
//		try (Connection connection = connectionPool.takeConnection()){				
//			
//				try(PreparedStatement prepareStatement = connection.prepareStatement(GET_ALL_USERS_OPERATION_TYPES_QUERY)){
//					prepareStatement.setInt(1, userId);					
//					
//					try(ResultSet resultSet = prepareStatement.executeQuery()){
//						while(resultSet.next()) {
//							OperationType operationType = new OperationType();
//							operationType.setId(resultSet.getInt(1));
//							operationType.setOperationType(resultSet.getString(2));
//							operationType.setRole(resultSet.getInt(3));
//							operationType.setUserId(resultSet.getInt(4));
//							
//							operationsList.add(operationType);
//						}
//					}
//				}							
//				
//		} catch (SQLException e) {
//			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationDAO getAllOperationTypes() method", e);
//		} catch (InterruptedException e) {
//			throw new DAOException ("Can`t take connection from ConnectionPool in OperationDAO getAllOperationTypes() method", e);			
//		}
//				
//		return operationsList;	
//		return null;
	}


	

}
