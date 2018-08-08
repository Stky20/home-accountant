package by.htp.accountant.dao;

import by.htp.accountant.bean.User;
import by.htp.accountant.exception.DAOException;

public interface UserDAO {

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
	User logination(String login, String hashPasswordFromUser) throws DAOException;
	boolean createUser(User user) throws DAOException;
	User editUser (User user, User newUser) throws DAOException;
	boolean removeUser (int userId) throws DAOException;
	boolean changeLogin(User user, String newLogin) throws DAOException;
	boolean changePassword(User user, String newHashPasswordFromUser) throws DAOException;
	
}
