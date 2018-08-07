package by.htp.accountant.dao;

import by.htp.accountant.dao.impl.MySQLUserDAO;

public class DAOFactory {

private static final DAOFactory instance = new DAOFactory();
	
	private final UserDAO userDAO = new MySQLUserDAO();
	
	private DAOFactory() {}
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
}
