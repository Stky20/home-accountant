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

import by.htp.accountant.bean.Operation;
import by.htp.accountant.bean.OperationType;
import by.htp.accountant.dao.OperationDAO;
import by.htp.accountant.dao.connectionpool.ConnectionPool;
import by.htp.accountant.exception.DAOException;


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
	private final static String GET_ONE_TYPE_NUMBER_OF_PAGES_AT_DATE_QUERY = "SELECT count(*) FROM operations WHERE operationTypeId=? AND operationDate=?;";
	private final static String GET_ONE_TYPE_NUMBER_OF_PAGES_BETWEEN_DATES_QUERY = "SELECT count(*) FROM operations WHERE operationTypeId=? AND operationDate BETWEEN ? AND ?;";
	private final static String GET_ONE_TYPE_OPERATIONS_AT_DATE_QUERY = "SELECT * FROM operations WHERE operationTypeId=? AND operationDate=? LIMIT ?,?";
	private final static String GET_ONE_TYPE_OPERATIONS_BETWEEN_DATES_QUERY = "SELECT * FROM operations WHERE operationTypeId=? AND operationDate BETWEEN ? AND ? LIMIT ?,?";
	private final static String DELETE_OPERATION_QUERY = "DELETE FROM operations WHERE id = ?;";
	private final static String EDIT_OPERATION_QUERY = "UPDATE operations SET amount=?, remark=?, operationDate=? WHERE id=?;";
	
	
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
	public int getOneTypeOperationsNumberOfPagesAtDate(int typeID, LocalDate date) throws DAOException {
		int numberOfPages = 0;
		
		try(Connection connection = connectionPool.takeConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_TYPE_NUMBER_OF_PAGES_AT_DATE_QUERY)){
			
			preparedStatement.setInt(1, typeID);
			preparedStatement.setDate(2, Date.valueOf(date));	
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){				
				if(resultSet.next()){
					numberOfPages =resultSet.getInt(1);					
				}
			}		
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or create resultSet or execute query in " + 
					"MySQLOperationDAO getOneTypeOperationsNumberOfPagesAtDate() method", e);
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in MySQLOperationDAO to getOneTypeOperationsNumberOfPagesAtDate()", e);
		}
		return numberOfPages;
	}


	@Override
	public int getOneTypeOperationsNumberOfPagesBetweenDates(int typeID, LocalDate firstDate, LocalDate lastDate) throws DAOException {
		int numberOfPages = 0;
		
		try(Connection connection = connectionPool.takeConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_TYPE_NUMBER_OF_PAGES_BETWEEN_DATES_QUERY)){
			
			preparedStatement.setInt(1, typeID);
			preparedStatement.setDate(2, Date.valueOf(firstDate));
			preparedStatement.setDate(3, Date.valueOf(lastDate));
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){				
				if(resultSet.next()){
					numberOfPages =resultSet.getInt(1);					
				}
			}		
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or create resultSet or execute query in " + 
					"MySQLOperationDAO getOneTypeOperationsNumberOfPagesBetweenDates() method", e);
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in MySQLOperationDAO to getOneTypeOperationsNumberOfPagesBetweenDates()", e);
		}
		return numberOfPages;
	}


	@Override
	public List<Operation> getOneTypeOperationsAtDate(int typeId, LocalDate date, int startingFrom, int operationsAmount) throws DAOException {
		List<Operation> operationList = new ArrayList<Operation>();
		
		try(Connection connection = connectionPool.takeConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_TYPE_OPERATIONS_AT_DATE_QUERY)){
			
			preparedStatement.setInt(1, typeId);
			preparedStatement.setDate(2, Date.valueOf(date));
			preparedStatement.setInt(3, startingFrom);
			preparedStatement.setInt(4, operationsAmount);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){				
				while(resultSet.next()){
					Operation operation = new Operation();
					operation.setId(resultSet.getInt(ID_FIELD_IN_OPERATION_TABLE));  		
					operation.setOperationTypeId(resultSet.getInt(OPERATION_TYPE_ID_FIELD_IN_OPERATION_TABLE)); 
					operation.setAmount(resultSet.getDouble(AMOUNT_FIELD_IN_OPERATION_TABLE));
					operation.setDate(resultSet.getDate(OPERATION_DATE_FIELD_IN_OPERATION_TABLE).toLocalDate());
					operation.setRemark(resultSet.getString(REMARK_FIELD_IN_OPERATION_TABLE));
					operation.setUserId(resultSet.getInt(USER_ID_FIELD_IN_OPERATION_TABLE));
					
					operationList.add(operation);				
				}
			}		
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or create resultSet or execute query in " + 
					"MySQLOperationDAO getOneTypeOperationsAtDate() method", e);
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in MySQLOperationDAO to getOneTypeOperationsAtDate()", e);
		}
		return operationList;
	}


	@Override
	public List<Operation> getOneTypeOperationsBetweenDates(int typeId, LocalDate firstDate, LocalDate lastDate, 
															int startingFrom, int operationsAmount) throws DAOException {
		
		List<Operation> operationList = new ArrayList<Operation>();
		
		try(Connection connection = connectionPool.takeConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ONE_TYPE_OPERATIONS_BETWEEN_DATES_QUERY)){
			
			preparedStatement.setInt(1, typeId);
			preparedStatement.setDate(2, Date.valueOf(firstDate));
			preparedStatement.setDate(3, Date.valueOf(lastDate));
			preparedStatement.setInt(4, startingFrom);
			preparedStatement.setInt(5, operationsAmount);
			
			try(ResultSet resultSet = preparedStatement.executeQuery()){				
				while(resultSet.next()){
					Operation operation = new Operation();
					operation.setId(resultSet.getInt(ID_FIELD_IN_OPERATION_TABLE));  		
					operation.setOperationTypeId(resultSet.getInt(OPERATION_TYPE_ID_FIELD_IN_OPERATION_TABLE)); 
					operation.setAmount(resultSet.getDouble(AMOUNT_FIELD_IN_OPERATION_TABLE));
					operation.setDate(resultSet.getDate(OPERATION_DATE_FIELD_IN_OPERATION_TABLE).toLocalDate());
					operation.setRemark(resultSet.getString(REMARK_FIELD_IN_OPERATION_TABLE));
					operation.setUserId(resultSet.getInt(USER_ID_FIELD_IN_OPERATION_TABLE));
					
					operationList.add(operation);				
				}
			}		
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or create resultSet or execute query in " + 
					"MySQLOperationDAO getOneTypeOperationsBetweenDates() method", e);
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in MySQLOperationDAO to getOneTypeOperationsBetweenDates()", e);
		}
		return operationList;
	}
	
	
	@Override
	public boolean deleteOperationById(int operationId) throws DAOException {
		
		int changedRowsInBase = 0;
		
		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_OPERATION_QUERY)){
			
				preparedStatement.setInt(1, operationId);								
				changedRowsInBase = preparedStatement.executeUpdate();			
				
				if(changedRowsInBase == 1) return true;
				else {
					logger.info("Somehow during deletion operation executeUpdate() method returner " + changedRowsInBase);		
					return false;
				}
			
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in UserDAO to delete user", e);
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or execute query in UserDAO deleteUser() method", e);
		}		
	}
	

	@Override
	public boolean editOperation(int operationId, double amout, String remark, LocalDate date) throws DAOException {
		
		int changedRowsInBase = 0;
		
		try (Connection connection = connectionPool.takeConnection()){
			
			try(PreparedStatement preparedStatement = connection.prepareStatement(EDIT_OPERATION_QUERY)){	
								
				preparedStatement.setDouble(1, amout);
				preparedStatement.setString(2, remark);
				preparedStatement.setDate(3, Date.valueOf(date));
				preparedStatement.setInt(4, operationId);
								
				changedRowsInBase = preparedStatement.executeUpdate();			
				
				if(changedRowsInBase == 1) return true;
				
			}	
		} catch (InterruptedException e) {
			throw new DAOException("Can`t take connection from ConnectionPool in UserDAO to edit user", e);
		} catch (SQLException e) {
			throw new DAOException("Can`t create statement or execute query in UserDAO editUser() method", e);
		}	
		
		return false;
	}	

	
	
	
}
