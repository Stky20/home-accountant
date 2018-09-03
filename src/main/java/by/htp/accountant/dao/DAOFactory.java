package by.htp.accountant.dao;

import by.htp.accountant.dao.impl.MySQLOperationDAO;
import by.htp.accountant.dao.impl.MySQLUserDAO;

public class DAOFactory {

private static final DAOFactory instance = new DAOFactory();
	
	private final UserDAO userDAO = new MySQLUserDAO();
	private final OperationDAO operationDAO = new MySQLOperationDAO();
	
	private DAOFactory() {}
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public OperationDAO getOperationDAO() {
		return operationDAO;
	}
	
}
