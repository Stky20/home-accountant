package by.htp.accountant.dao;

import java.util.List;

import by.htp.accountant.bean.OperationType;
import by.htp.accountant.bean.User;
import by.htp.accountant.exception.DAOException;


public interface UserDAO {
		
	boolean createUser(User user, List<OperationType> defaultOperationTypeList) throws DAOException;
	
	User authorizeUser(String login, String hashPasswordFromUser) throws DAOException;
	
	List<User> showUsers(int role, int usersAmount, int startingFrom) throws DAOException;
	
	boolean editUser (int userId, String name, String surname, String email) throws DAOException;
	
	boolean removeUser (int userId) throws DAOException;		
	
	boolean changeLogin(int userId, String newLogin) throws DAOException;
	
	boolean changePassword(int userId, String newHashPasswordFromUser) throws DAOException;
	
	boolean changeUsersRole(int userId, int role) throws DAOException;		
	
	int countAmountOfPages(int role, int recordingsAmountInTable) throws DAOException;	
	
	/**
	 * If login exist returns true
	 * if not returns false
	 */
	boolean checkLogin(String login) throws DAOException;
	
	/**
	 * If password equals returns true
	 * if not returns false
	 */
	boolean checkPassword(String login, String hashPasswordFromUser) throws DAOException;
		
	/**
	 * If login exist and passwords equals returns true
	 * if some of this conditions failed returns false
	 */
	boolean checkLoginAndPassword(String login, String hashPasswordFromUser) throws DAOException;	
}
