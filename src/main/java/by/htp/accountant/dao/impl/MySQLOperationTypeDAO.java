package by.htp.accountant.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.bean.OperationType;
import by.htp.accountant.dao.OperationTypeDAO;
import by.htp.accountant.dao.connectionpool.ConnectionPool;
import by.htp.accountant.exception.DAOException;


public class MySQLOperationTypeDAO implements OperationTypeDAO {
	
	private ConnectionPool connectionPool;	
	
	private static final Logger logger = LoggerFactory.getLogger(MySQLOperationTypeDAO.class);
	
	private final static String GET_USER_OPERATION_TYPES_ON_ROLE_QUERY = "SELECT * FROM operation_types WHERE (user_Id = ?) AND (role = ?);"; 
	private final static String TYPE_CREATE_QUERY = "INSERT INTO operation_types (operationType, user_Id, role) VALUES (?, ?, ?);";
	private final static String TYPE_EDIT_QUERY = "UPDATE operation_types SET operationType=? WHERE id=?;";
	private final static String TYPE_DELETE_QUERY = "DELETE FROM operation_types WHERE id=?;";
	private final static String OPERATION_DELETE_QUERY = "DELETE FROM operations WHERE operationTypeId=?;";
	private final static String GETTING_TYPR_ID_QUERY = "SELECT id FROM operation_types WHERE user_Id=? AND operationType=?;";

	
	
	
	public MySQLOperationTypeDAO() {
		connectionPool = ConnectionPool.getInstance();
	}
	
	@Override
	public List<OperationType> getUserOperationTypesDependingOnTypeRole(int userId, int typeRole) throws DAOException {
		List<OperationType> spendingTypeList = new ArrayList<OperationType>();
		
		try (Connection connection = connectionPool.takeConnection(); 
			PreparedStatement prepareStatement = connection.prepareStatement(GET_USER_OPERATION_TYPES_ON_ROLE_QUERY)){
				
				prepareStatement.setInt(1, userId);
				prepareStatement.setInt(2, typeRole);
				
				try(ResultSet resultSet = prepareStatement.executeQuery()){				
					while(resultSet.next())	{
						OperationType type = new OperationType();
						type.setId(resultSet.getInt(1));  		
						type.setOperationType(resultSet.getString(2)); 
						type.setUserId(resultSet.getInt(3)); 
						type.setRole(resultSet.getInt(4)); 
					
						spendingTypeList.add(type);
					}
					if(typeRole == OperationType.SPENDING_TYPE_ROLE) {
						spendingTypeList.add(getUserUndeletableOperationTypesDependingOnTypeRole(userId, OperationType.SPENDING_TYPE_UNDELETEBLE_ROLE));
					} else {
						spendingTypeList.add(getUserUndeletableOperationTypesDependingOnTypeRole(userId, OperationType.INCOME_TYPE_UNDELETEBLE_ROLE));
					}
				}
		} catch (SQLException e) {
			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationTypeDAO getUserOperationTypesDependingOnTypeRole() method", e);
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationTypeDAO getUserOperationTypesDependingOnTypeRole() method", e);
		}
				
		return spendingTypeList;		
	}

	@Override
	public OperationType getUserUndeletableOperationTypesDependingOnTypeRole(int userId,
			int undeletableOperationTypeRole) throws DAOException {

		OperationType operationType = new OperationType();
		
		try (Connection connection = connectionPool.takeConnection(); 
				PreparedStatement prepareStatement = connection.prepareStatement(GET_USER_OPERATION_TYPES_ON_ROLE_QUERY)){
					
					prepareStatement.setInt(1, userId);
					prepareStatement.setInt(2, undeletableOperationTypeRole);
					
					try(ResultSet resultSet = prepareStatement.executeQuery()){				
						if(resultSet.next())	{							
							operationType.setId(resultSet.getInt(1));  		
							operationType.setOperationType(resultSet.getString(2)); 
							operationType.setUserId(resultSet.getInt(3)); 
							operationType.setRole(resultSet.getInt(4));					
						}
					}
			} catch (SQLException e) {
				throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationTypeDAO getUserUndeletableOperationTypesDependingOnTypeRole() method", e);
			} catch (InterruptedException e) {
				throw new DAOException ("Can`t take connection from ConnectionPool in OperationTypeDAO getUserUndeletableOperationTypesDependingOnTypeRole() method", e);
			}
		return operationType;
	}

	@Override
	public boolean createOperationType(OperationType type) throws DAOException   {
		
		int addedRowsInBase = 0;
		
		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(TYPE_CREATE_QUERY)){	
			
			preparedStatement.setString(1, type.getOperationType());
			preparedStatement.setInt(2, type.getUserId());
			preparedStatement.setInt(3, type.getRole());			
			
			addedRowsInBase = preparedStatement.executeUpdate();
			
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in MySQLOperationTypeDAO to create operation type", e);
		} catch (SQLException e) {			
			throw new DAOException ("SQLException in MySQLOperationTypeDAO to create operation type in createOperationType() method", e);
		}	
		
		if(addedRowsInBase == 1) return true;
		else return false;	
	}

	@Override
	public boolean editOperationType(int typeId, String operationType) throws DAOException {
		try (Connection connection = connectionPool.takeConnection();				
				PreparedStatement preparedStatement = connection.prepareStatement(TYPE_EDIT_QUERY)){						
				
			preparedStatement.setString(1, operationType);	
			preparedStatement.setInt(2, typeId);					
								
				if(preparedStatement.executeUpdate() != 1) return false;
				else return true;
				
			} catch (InterruptedException e) {
				throw new DAOException("Can`t take connection from ConnectionPool in OperationTypeDAO to edit type", e);
			} catch (SQLException e) {
				throw new DAOException("Can`t create statement or execute query in operationType editOperationType() method", e);
			}				
	}

	@Override
	public boolean deleteOperationType(int typeId) throws DAOException {

		int deletedrypeRows = 0;		
		
		try (Connection connection = connectionPool.takeConnection()) {			
			try {
				connection.setAutoCommit(false);
			
				try(PreparedStatement statement = connection.prepareStatement(OPERATION_DELETE_QUERY)){
					statement.setInt(1, typeId);
					statement.executeUpdate();			
				}		
				try(PreparedStatement statement = connection.prepareStatement(TYPE_DELETE_QUERY)){
					statement.setInt(1, typeId);
					deletedrypeRows = statement.executeUpdate();
				}				
			
				connection.commit();
				
			} catch (SQLException e) {
				logger.warn("SQLException while doing deleteOperationType() method in MySQLOperationTypeDAO", e);
				connection.rollback();	
				connection.setAutoCommit(true);
				throw new DAOException ("SQLException in OperationTypeDAO deleteOperationType() method", e);
			}
				
			connection.setAutoCommit(true);
			
			if(deletedrypeRows == 1) {
				return true;
			}			
			
		} catch (InterruptedException e) {
			logger.warn("InterruptedException while taking connection from connectionpool in MySQLUserDAO to delete user");
			throw new DAOException ("Can`t take connection from connectionpool in MySQLUserDAO to delete user", e);			
		} catch (SQLException e) {			
			logger.warn("SQLException while doing rollback() or setting AutoCommit true in userDelete() method of MySQLUserDAO", e);
			throw new DAOException ("SQLException in UserDAO deleteUser() method", e);
		}
		
		return false;
		
	}

	@Override
	public int getTypeIdOnUserIdAndOperationType(int userId, String operationType) throws DAOException {
		
		int typeId = 0;
		
		try (Connection connection = connectionPool.takeConnection(); 
				PreparedStatement prepareStatement = connection.prepareStatement(GETTING_TYPR_ID_QUERY)){
					
					prepareStatement.setInt(1, userId);
					prepareStatement.setString(2, operationType);
					
					try(ResultSet resultSet = prepareStatement.executeQuery()){				
						if(resultSet.next())	{
							typeId = resultSet.getInt(1);						
						}						
					}
		} catch (SQLException e) {
			throw new DAOException ("Can`t take Prepeared Statement or Result Set in OperationTypeDAO getTypeIdOnUserIdAndOperationType() method", e);
		} catch (InterruptedException e) {
			throw new DAOException ("Can`t take connection from ConnectionPool in OperationTypeDAO getTypeIdOnUserIdAndOperationType() method", e);
		}
					
		return typeId;		
	}	
}
