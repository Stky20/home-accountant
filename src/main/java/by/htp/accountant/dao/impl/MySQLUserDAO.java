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
import by.htp.accountant.bean.User;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.dao.connectionpool.ConnectionPool;
import by.htp.accountant.exception.SQLUserDAOException;

public class MySQLUserDAO implements UserDAO{
	
	private ConnectionPool connectionPool;
	
	private static final Logger logger = LoggerFactory.getLogger(MySQLUserDAO.class);														 
	
	private final static String CHECK_LOGIN_QUERY = "SELECT nickName FROM users WHERE (nickName = ?);";                              
	private final static String CHECK_PASSWORD_QUERY = "SELECT hashPassword FROM users WHERE (nickName = ?);";                       
	private final static String LOGINATION_QUERY = "SELECT * FROM users WHERE (nickName = ?);";                                      
	private final static String USER_CREATION_QUERY = "INSERT INTO users (nickName, hashPassword, name, surname, e_mail, role) VALUES (?, ?, ?, ?, ?, ?);";
	private final static String DEFAULT_OPERATION_TYPES_INIT_QUERY = "INSERT INTO operation_types (operationType, user_Id, role) VALUES (?, ?, ?);";
	private final static String USER_CHANGING_QUERY = "UPDATE users SET name=?, surname=?, e_mail=? WHERE id=?;";	
	private final static String USER_DELETE_QUERY = "DELETE FROM users WHERE id = ?;";
	private final static String DELETE_USERS_OPERATIONS_QUERY = "DELETE FROM operations WHERE user_id = ?;";
	private final static String DELETE_USERS_OPERATIONS_TYPES_QUERY = "DELETE FROM dept_operations WHERE user_id = ?;";
	private final static String DELETE_USERS_DEPTS_OPERATIONS_QUERY = "DELETE FROM operation_types WHERE user_id = ?;";	
	private final static String USER_LOGIN_CHANGING_QUERY = "UPDATE users SET nickName=? WHERE id=?;";
	private final static String USER_PASSWORD_CHANGING_QUERY = "UPDATE users SET hashPassword=? WHERE id=?;";
	private final static String COUNT_ALL_USERS_QUERY = "SELECT count(*) FROM homeaccountant.users WHERE role=?;";
	private final static String SHOW_USERS_QUERY = "SELECT * FROM homeaccountant.users WHERE role=? LIMIT ?,?;";
	private final static String USER_CHANGE_ROLE_QUERY = "UPDATE users SET role=? WHERE id=?;";

	
	public MySQLUserDAO() {
		connectionPool = ConnectionPool.getInstance();
	}
	
	
	@Override
	public boolean createUser(User user, List<OperationType> defaultOperationTypeList) throws SQLUserDAOException {
		
		int addedRowsInBase = 0;		
		int userId = 0;		
		
		try (Connection connection = connectionPool.takeConnection()){	
			try {
				connection.setAutoCommit(false);
				try(PreparedStatement preparedStatement = connection.prepareStatement(USER_CREATION_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)){
					
					preparedStatement.setString(1, user.getNickName());
					preparedStatement.setString(2, user.getHashPassword());
					preparedStatement.setString(3, user.getName());
					preparedStatement.setString(4, user.getSurname());
					preparedStatement.setString(5, user.geteMail());
					preparedStatement.setInt(6, user.getRole());
					addedRowsInBase = preparedStatement.executeUpdate();
					
					try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
						if(resultSet.next()) {
							userId = resultSet.getInt(1);
						}
					}					
				}
				
				try(PreparedStatement preparedStatement = connection.prepareStatement(DEFAULT_OPERATION_TYPES_INIT_QUERY)){
					for(OperationType defaultType: defaultOperationTypeList) {					
						preparedStatement.setString(1, defaultType.getOperationType());
						preparedStatement.setInt(2, userId);
						preparedStatement.setInt(3, defaultType.getRole());
						preparedStatement.addBatch();
					}	
					preparedStatement.executeBatch();
				}
				
				connection.commit();
				
			}catch (SQLException e) {
				logger.warn("SQLException int createUser() method UserDAOimpl", e);
				connection.rollback();
			}	
			
			connection.setAutoCommit(true);
			
		} catch (InterruptedException e) {
			logger.warn("InterruptedException in createUser() method of MySQLUserDAO while taking connection from ConnectionPool", e);
			throw new SQLUserDAOException ("Can`t take connection from ConnectionPool in MySQLUserDAO to create user", e);
		} catch (SQLException e) {
			logger.warn("SQLException in createUser() method of MySQLUserDAO while taking connection from ConnectionPool or while doing "
					+ "rollback or setAutoCommit(true) on connection", e);
			throw new SQLUserDAOException ("Can`t do rollback or setAutoCommit(true) on connection in MySQLUserDAO createUser() method", e);
		}	
		
		if(addedRowsInBase == 1) return true;
		else return false;
	}
	
	
	@Override
	public User authorizeUser(String login, String hashPasswordFromUser) throws SQLUserDAOException {
			
		User loggedUser = new User();			
			
			try(Connection connection = connectionPool.takeConnection();
					PreparedStatement prepareStatement = connection.prepareStatement(LOGINATION_QUERY)) {
								                             
				prepareStatement.setString(1, login);
				
				try(ResultSet resultSet = prepareStatement.executeQuery()){
				
					if(resultSet.next())	{
						loggedUser.setId(resultSet.getInt(1));  		
						loggedUser.setNickName(resultSet.getString(2)); 
						loggedUser.setHashPassword(resultSet.getString(3));
						loggedUser.setName(resultSet.getString(4));
						loggedUser.setSurname(resultSet.getString(5));
						loggedUser.seteMail(resultSet.getString(6));
						loggedUser.setRole(resultSet.getInt(7));
						
						return loggedUser;
					}
				}
			} catch (InterruptedException e) {
				throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to login", e);
			} catch (SQLException e) {
				throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO logination() method", e);
			}	
			
			return null;		
	}
	
	
	@Override
	public List<User> showUsers(int role, int usersAmount, int startingFrom) throws SQLUserDAOException {
		
		List<User> usersList = new ArrayList<User>();
		
		try (Connection connection = connectionPool.takeConnection(); 
				PreparedStatement prepareStatement = connection.prepareStatement(SHOW_USERS_QUERY)){
				
				prepareStatement.setInt(1, role);
				prepareStatement.setInt(2, startingFrom);
				prepareStatement.setInt(3, usersAmount);
				
				try(ResultSet resultSet = prepareStatement.executeQuery()){				
					while(resultSet.next())	{
						User user = new User();
						user.setId(resultSet.getInt(1));  		
						user.setNickName(resultSet.getString(2)); 
						user.setHashPassword(resultSet.getString(3));
						user.setName(resultSet.getString(4));
						user.setSurname(resultSet.getString(5));
						user.seteMail(resultSet.getString(6));
						user.setRole(resultSet.getInt(7));
						usersList.add(user);
					}
				}
		} catch (SQLException e) {
			throw new SQLUserDAOException ("Can`t take Prepeared Statement or Result Set in UserDAO showUsers() method", e);
		} catch (InterruptedException e) {
			throw new SQLUserDAOException ("Can`t take connection from ConnectionPool in UserDAO showUsers() method", e);			
		}
				
		return usersList;
	}
	
	
	@Override
	public boolean editUser(int userId, String name, String surname, String email) throws SQLUserDAOException {				
				
		try (Connection connection = connectionPool.takeConnection();				
			PreparedStatement preparedStatement = connection.prepareStatement(USER_CHANGING_QUERY)){						
			
			preparedStatement.setString(1, name);						
			preparedStatement.setString(2, surname);			
			preparedStatement.setString(3, email);			
			preparedStatement.setInt(4, userId);
			
			if(preparedStatement.executeUpdate() != 1) return false;
			else return true;
		} catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to edit user", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO editUser() method", e);
		}				
		
	}
	
	
	@Override
	public boolean removeUser(int userId) throws SQLUserDAOException {
		
		int deletedRowsUsers = 0;		
		
		try (Connection connection = connectionPool.takeConnection()) {			
			try {
				connection.setAutoCommit(false);
			
				try(PreparedStatement statement = connection.prepareStatement(DELETE_USERS_DEPTS_OPERATIONS_QUERY)){
					statement.setInt(1, userId);
					statement.executeUpdate();			
				}		
				try(PreparedStatement statement = connection.prepareStatement(DELETE_USERS_OPERATIONS_TYPES_QUERY)){
					statement.setInt(1, userId);
					statement.executeUpdate();
				}
				try(PreparedStatement statement = connection.prepareStatement(DELETE_USERS_OPERATIONS_QUERY)){
					statement.setInt(1, userId);
					statement.executeUpdate();
				}			
				try(PreparedStatement statement = connection.prepareStatement(USER_DELETE_QUERY)){
					statement.setInt(1, userId);
					deletedRowsUsers = statement.executeUpdate();
				}	
			
				connection.commit();
				
			} catch (SQLException e) {
				logger.warn("SQLException while doing userDelete() method in MySQLUserDAO", e);
				connection.rollback();	
				connection.setAutoCommit(true);
				return false;
			}
				
			connection.setAutoCommit(true);
			
			if(deletedRowsUsers == 1) {
				return true;
			}			
			
		} catch (InterruptedException e) {
			logger.warn("InterruptedException while taking connection from connectionpool in MySQLUserDAO to delete user");
			throw new SQLUserDAOException ("Can`t take connection from connectionpool in MySQLUserDAO to delete user", e);			
		} catch (SQLException e) {			
			logger.warn("SQLException while doing rollback() or setting AutoCommit true in userDelete() method of MySQLUserDAO", e);
			throw new SQLUserDAOException ("SQLException in UserDAO deleteUser() method", e);
		}
		
		return false;
	}

	
	@Override
	public boolean changeLogin(int userId, String newLogin) throws SQLUserDAOException {		
		
		int updatedRows = 0;
		
		try(Connection connection = connectionPool.takeConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(USER_LOGIN_CHANGING_QUERY)) {
						
			preparedStatement.setString(1, newLogin);
			preparedStatement.setInt(2, userId);
			updatedRows = preparedStatement.executeUpdate();
			
			if(updatedRows == 1) {
				return true;
			} else {
				throw new SQLUserDAOException("executeUpdate() updated " + updatedRows + " rows while changing user`s(ID- " + userId +") login");
			}			
		}catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO, can`t update user login", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO, can`t update user login", e);
		}			
	}
	

	@Override
	public boolean changePassword(int userId, String newHashPasswordFromUser) throws SQLUserDAOException {
		
		int updatedRows = 0;
		
		try (Connection connection = connectionPool.takeConnection(); 
				PreparedStatement preparedStatement = connection.prepareStatement(USER_PASSWORD_CHANGING_QUERY)){
						
			preparedStatement.setString(1, newHashPasswordFromUser);
			preparedStatement.setInt(2, userId);
			updatedRows = preparedStatement.executeUpdate();
			
			if(updatedRows == 1) return true;
			
		}catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO, can`t update user password", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO, can`t update user password", e);
		}	
		
		return false;
	}
	
	
	@Override
	public boolean changeUsersRole(int userId, int role) throws SQLUserDAOException {
		
		int updatedRows = 0;
		try (Connection connection = connectionPool.takeConnection(); 
				PreparedStatement prepareStatement = connection.prepareStatement(USER_CHANGE_ROLE_QUERY)){
				
				prepareStatement.setInt(1, role);
				prepareStatement.setInt(2, userId);
				updatedRows = prepareStatement.executeUpdate();
				
				if(updatedRows == 1) {
					return true;
				} else {
					throw new SQLUserDAOException("executeUpdate() updated " + updatedRows + " rows while changing user`s(ID- " + userId +") role");
				}
		} catch (SQLException e) {
			throw new SQLUserDAOException ("Can`t take Prepeared Statement or Result Set in UserDAO amountOfPages() method", e);
		} catch (InterruptedException e) {
			throw new SQLUserDAOException ("Can`t take connection from ConnectionPool in UserDAO amountOfPages() method", e);
		}
		
	}
	
	
	@Override
	public List<OperationType> getUsersOperationTypes(int id) throws SQLUserDAOException {
		
		return null;
	}
	
	
	@Override
	public int countAmountOfPages(int role, int recordingsAmountInTable) throws SQLUserDAOException {
		
		int amountOfAllUsers = 0;
		
		try (Connection connection = connectionPool.takeConnection(); 
			PreparedStatement prepareStatement = connection.prepareStatement(COUNT_ALL_USERS_QUERY)){
			
			prepareStatement.setInt(1, role);
			
			try(ResultSet resultSet = prepareStatement.executeQuery()){
				resultSet.next();
				
				amountOfAllUsers = resultSet.getInt(1);
			}
			
		} catch (SQLException e) {
			throw new SQLUserDAOException ("Can`t take Prepeared Statement or Result Set in UserDAO amountOfPages() method", e);
		} catch (InterruptedException e) {
			throw new SQLUserDAOException ("Can`t take connection from ConnectionPool in UserDAO amountOfPages() method", e);
		}
		
		int amountOfPages = amountOfAllUsers/recordingsAmountInTable;
		
		if((amountOfAllUsers%recordingsAmountInTable) != 0) amountOfPages++;
		
		return amountOfPages;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkLogin(String login) throws SQLUserDAOException {   																	
				
		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement prepareStatement = connection.prepareStatement(CHECK_LOGIN_QUERY)) {
			
			prepareStatement.setString(1, login);
			
			try(ResultSet resultSet =prepareStatement.executeQuery()){
							
				if(resultSet.next()) {
					return true;                                                                               								
				}
			}
		} catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to check login", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO checkLogin() method", e);
		}
			
		return false;                                                                                        
	}
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkPassword(String login, String hashPasswordFromUser) throws SQLUserDAOException {
				
		String hashPasswordFromDB;
				
		try (Connection connection = connectionPool.takeConnection();
				PreparedStatement prepareStatement = connection.prepareStatement(CHECK_PASSWORD_QUERY)){
				
				prepareStatement.setString(1, login);
				
				try(ResultSet resultSet =prepareStatement.executeQuery()){
				
					resultSet.next();
					hashPasswordFromDB = resultSet.getString(1);													
					
					if(hashPasswordFromUser.equals(hashPasswordFromDB)) {
						return true;                                                                                
					}
				}
				
			} catch (InterruptedException e) {
				throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to check password", e);
			} catch (SQLException e) {
				throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO checkPassword() method", e);
			}	
		
		return false;                                                                                             
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkLoginAndPassword(String login, String hashPasswordFromUser) throws SQLUserDAOException {
		if(checkLogin(login) && checkPassword(login, hashPasswordFromUser)) return true;
		else return false;
	}
}
