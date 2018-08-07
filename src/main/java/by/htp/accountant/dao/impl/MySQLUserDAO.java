package by.htp.accountant.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.htp.accountant.bean.Password;
import by.htp.accountant.bean.User;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.dao.connectionpool.ConnectionPool;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.SQLUserDAOException;

public class MySQLUserDAO implements UserDAO{
	
	private ConnectionPool connectionPool;
	
//	private static final Logger logger = Logger.getLogger(MySQLUserDAO.class);														 //логировать будем в контроллере
	
	private final static String CHECK_LOGIN_QUERY = "SELECT nickName FROM users WHERE (nickName = ?);";                              // проверка логинов
	private final static String CHECK_PASSWORD_QUERY = "SELECT hashPassword FROM users WHERE (nickName = ?);";                       //пароль для логина
	private final static String LOGINATION_QUERY = "SELECT * FROM users WHERE (nickName = ?);";                                      //достаем юзера из бызы по логину
	private final static String USER_CREATION_QUERY = "INSERT INTO users (nickName, hashPassword, name, surname, e_mail) VALUES (?, ?, ?, ?, ?, ?);"; //пхаем юзера
	private final static String USER_CHANGING_QUERY = "UPDATE users SET name=?, surname=?, e_mail=? WHERE id=?;";
	private final static String USER_DELETE_QUERY = "DELETE FROM users WHERE id = ?;";
	private final static String USER_LOGIN_CHANGING_QUERY = "UPDATE users SET nickName=? WHERE id=?;";
	private final static String USER_PASSWORD_CHANGING_QUERY = "UPDATE users SET password=? WHERE id=?;";
	
	public MySQLUserDAO() {
		connectionPool = ConnectionPool.getInstance();
	}

	@Override
	public boolean checkLogin(String login) throws SQLUserDAOException {   																	//логин есть возвращает тру, нет - фолс
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;		
		
		try {
			
			connection = connectionPool.takeConnection();
			prepareStatement = connection.prepareStatement(CHECK_LOGIN_QUERY);                             								//поиск юзера по логину
			prepareStatement.setString(1, login);
			resultSet = prepareStatement.executeQuery();	
			
			if(resultSet.next()) {
				return true;                                                                               								// логин в базе есть
			}
			
		} catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to check login", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO checkLogin() method", e);
		}finally {	
			
				if(resultSet != null) {
					try {
						resultSet.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close result set in UserDAO checkLogin() method",e);
					}		
				}
				if(prepareStatement != null) {
					try {
						prepareStatement.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close statement in UserDAO checkLogin() method",e);
					}					
				}
				if(connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close connection  in UserDAO checkLogin() method",e);
					}			
				}
			
		}
			
		return false;                                                                                        //логина в базе нет
	}

	@Override
	public boolean checkPassword(String login, String hashPasswordFromUser) throws SQLUserDAOException {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String hashPasswordFromDB;
				
			try {
				
				connection = connectionPool.takeConnection();
				prepareStatement = connection.prepareStatement(CHECK_PASSWORD_QUERY);                             // поиск юзера по логину
				prepareStatement.setString(1, login);
				resultSet = prepareStatement.executeQuery();
				
				resultSet.next();
				hashPasswordFromDB = resultSet.getString(1);													// получили хэш пароля из базы
				
				if(Password.checkPassword(hashPasswordFromUser, hashPasswordFromDB)) {
					return true;                                                                                // пароль из базы соответствует паролю от юзера
				}
				
			} catch (InterruptedException e) {
				throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to check password", e);
			} catch (SQLException e) {
				throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO checkPassword() method", e);
			}finally {
				                                         
					if(resultSet != null)
						try {
							resultSet.close();
						} catch (SQLException e) {
							throw new SQLUserDAOException("Can`t close result set in UserDAO checkPassword() method", e);
						}			
					if(prepareStatement != null)
						try {
							prepareStatement.close();
						} catch (SQLException e) {
							throw new SQLUserDAOException("Can`t close statement in UserDAO checkPassword() method", e);
						}						
					if(connection != null)
						try {
							connection.close();
						} catch (SQLException e) {
							throw new SQLUserDAOException("Can`t close connection  in UserDAO checkPassword() method", e);
						}					
			}		
		
		return false;                                                                                             //пароль из базы не соотв паролю от юзера
	}
	
	@Override
	public boolean checkLoginAndPassword(String login, String hashPasswordFromUser) throws SQLUserDAOException {
		if(checkLogin(login) && checkPassword(login, hashPasswordFromUser)) return true;
		else return false;
	}


	@Override
	public User logination(String login, String hashPasswordFromUser) throws SQLUserDAOException {
		
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		User loggedUser = new User();		
		
			
			try {
				
				connection = connectionPool.takeConnection();
				prepareStatement = connection.prepareStatement(LOGINATION_QUERY);                             //достаем юзера по логину
				prepareStatement.setString(1, login);
				resultSet = prepareStatement.executeQuery();
				
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
				
			} catch (InterruptedException e) {
				throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to login", e);
			} catch (SQLException e) {
				throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO logination() method", e);
			}finally {
				                                         
					if(resultSet != null)
						try {
							resultSet.close();
						} catch (SQLException e) {
							throw new SQLUserDAOException("Can`t close result set in UserDAO logination() method", e);
						}			
					if(prepareStatement != null)
						try {
							prepareStatement.close();
						} catch (SQLException e) {
							throw new SQLUserDAOException("Can`t close statement in UserDAO logination() method", e);
						}						
					if(connection != null)
						try {
							connection.close();
						} catch (SQLException e) {
							throw new SQLUserDAOException("Can`t close connection  in UserDAO logination() method", e);
						}	
			}		
			
			return null;		
	}

	@Override
	public boolean createUser(User user) throws SQLUserDAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int addedRowsInBase = 0;
		
		try {
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(USER_CREATION_QUERY);
			preparedStatement.setString(1, user.getNickName().trim());
			preparedStatement.setString(2, user.getHashPassword().trim());
			preparedStatement.setString(3, user.getName().trim());
			preparedStatement.setString(4, user.getSurname().trim());
			preparedStatement.setString(5, user.geteMail().trim());
			preparedStatement.setInt(6, user.getRole());
			addedRowsInBase = preparedStatement.executeUpdate();
		} catch (InterruptedException e) {
			throw new SQLUserDAOException ("Can`t take connection from ConnectionPool in UserDAO to create user", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException ("Can`t create statement or execute query in UserDAO createUser() method", e);
		}finally {
			                                        
				if(preparedStatement != null)
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException ("Can`t close statement in UserDAO createUser() method", e);
					}						
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException ("Can`t close connection  in UserDAO createUser() method", e);
					}	
		}		
		
		if(addedRowsInBase == 1) return true;
		else return false;
	}

	@Override
	public User editUser(User user, User newUser) throws SQLUserDAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
				
		try {
			
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(USER_CHANGING_QUERY);
			
			if (newUser.getName() == null || newUser.getName().trim().isEmpty()) {         //проверяем какие даные изменены а какие нет
				preparedStatement.setString(1, user.getName().trim());
			}else{
				preparedStatement.setString(1, newUser.getName().trim());
			}
			
			if(newUser.getSurname() == null || newUser.getSurname().trim().isEmpty()) {
				preparedStatement.setString(2, user.getSurname().trim());
			}else {
				preparedStatement.setString(2, newUser.getSurname().trim());
			}
			
			if(newUser.geteMail() == null || newUser.geteMail().trim().isEmpty()) {
				preparedStatement.setString(3, user.geteMail().trim());
			}else {
				preparedStatement.setString(3, newUser.geteMail().trim());
			}
			
			preparedStatement.setInt(4, user.getId());
			preparedStatement.executeUpdate();
			
		} catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO to edit user", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO editUser() method", e);
		}finally {
			                                        
				if(preparedStatement != null)
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException ("Can`t close statement in UserDAO editUser() method", e);
					}						
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException ("Can`t close connection  in UserDAO editUser() method", e);
					}				
//			}catch(SQLException e) {
//				throw new SQLUserDAOException("Can`t close statement or connection  in UserDAO editUser() method", e);				
//			}
		}		
		
		if (newUser.getName() != null || !(newUser.getName().trim().isEmpty())) {                //для возвращаемого юзера тоже проверяем, чт опоменять, а что взять старое
			user.setName(newUser.getName());
		}
		
		if(newUser.getSurname() != null || !(newUser.getSurname().trim().isEmpty())) {
			user.setSurname(newUser.getSurname());
		}
		
		if(newUser.geteMail() != null || newUser.geteMail().trim().isEmpty()) {
			user.seteMail(newUser.geteMail());
		}
		
		return user;
	}

	@Override
	public boolean removeUser(int userId) throws SQLUserDAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int deletedRows = 0;
		
		try {
			
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(USER_DELETE_QUERY);
			preparedStatement.setInt(1, userId);
			deletedRows = preparedStatement.executeUpdate();
			
			if(deletedRows == 1) return true;
			
		}catch (InterruptedException e) {
			throw new SQLUserDAOException ("Can`t take connection from ConnectionPool in UserDAO to delete user", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException ("Can`t create statement or execute query in UserDAO deleteUser() method", e);
		}finally {
			                                         
				if(preparedStatement != null)
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close statement in UserDAO deleteUser() method", e);
					}						
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close connection in UserDAO deleteUser() method", e);
					}	
		}		
		
		return false;
	}

	@Override
	public boolean changeLogin(User user, String newLogin) throws SQLUserDAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int updatedRows = 0;
		
		try {
			
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(USER_LOGIN_CHANGING_QUERY);
			preparedStatement.setString(1, newLogin);
			preparedStatement.setInt(2, user.getId());
			updatedRows = preparedStatement.executeUpdate();
			
			if(updatedRows == 1) return true;
			
		}catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO, can`t update user login", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO, can`t update user login", e);
		}finally {
			                                       
				if(preparedStatement != null)
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close statement in UserDAO cahngeLogin() method", e);
					}						
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close connection  in UserDAO cahngeLogin() method", e);
					}	

		}		
		
		return false;
	}

	@Override
	public boolean changePassword(User user, String newHashPasswordFromUser) throws SQLUserDAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int updatedRows = 0;
		
		try {
			
			connection = connectionPool.takeConnection();
			preparedStatement = connection.prepareStatement(USER_PASSWORD_CHANGING_QUERY);
			preparedStatement.setString(1, newHashPasswordFromUser);
			preparedStatement.setInt(2, user.getId());
			updatedRows = preparedStatement.executeUpdate();
			
			if(updatedRows == 1) return true;
			
		}catch (InterruptedException e) {
			throw new SQLUserDAOException("Can`t take connection from ConnectionPool in UserDAO, can`t update user password", e);
		} catch (SQLException e) {
			throw new SQLUserDAOException("Can`t create statement or execute query in UserDAO, can`t update user password", e);
		}finally {
			                                         
				if(preparedStatement != null)
					try {
						preparedStatement.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close statement in UserDAO cahngePassword() method", e);
					}						
				if(connection != null)
					try {
						connection.close();
					} catch (SQLException e) {
						throw new SQLUserDAOException("Can`t close connection  in UserDAO cahngePassword() method", e);
					}				

		}		
		
		return false;
	}
	
}
