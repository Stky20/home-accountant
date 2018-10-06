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
	
	private final static String GET_USER_OPERATION_TYPES_ON_ROLE_QUERY = "SELECT * FROM operation_types WHERE (user_Id = ?) AND (role = ?);";  	
	
	
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
	
}
