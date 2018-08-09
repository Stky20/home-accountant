package by.htp.accountant.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.UserServiceException;
import by.htp.accountant.bean.Password;
import by.htp.accountant.bean.User;
import by.htp.accountant.service.UserService;

public class UserServiceImpl implements UserService {	
	
	public static final String LOGIN_PARAM = "login";
	public static final String PASSWORD_PARAM = "password";
	public static final String NULL_ERROR_MESSAGE = "Login and password should not be null";
	public static final String NULL_HASH_ERROR_MESSAGE = "Becourse of technical problems logination faild, tru again later";
	public static final String WRONG_LOGIN_PASSWORD_ERROR_MESSAGE = "Wrong login or password";

	@Override
	public User logination(HttpServletRequest request, HttpServletResponse response) throws UserServiceException {
		
		DAOFactory factoryDAO = DAOFactory.getInstance();
		UserDAO userDAO = factoryDAO.getUserDAO();
		
		User user = null;
		String login = null;
		String passwordFromUser = null;
		String hashPassword = null;
		Password hashPasswordMaker = Password.getInstance();
		
		login = request.getParameter(LOGIN_PARAM);
		passwordFromUser = request.getParameter(PASSWORD_PARAM);
		
			if (ifLoginPasswordNull(login, passwordFromUser)){
				request.setAttribute("nullErrorMessage", NULL_ERROR_MESSAGE);
				return null;
			}
		
		hashPassword = hashPasswordMaker.getHashPassword(passwordFromUser);
		
			if (ifLoginPasswordNull(login, passwordFromUser)){
				request.setAttribute("nullHashErrorMessage", NULL_HASH_ERROR_MESSAGE);
				return null;
			}
		
			try {
				
				if(userDAO.checkLoginAndPassword(login, hashPassword)) {
					user = userDAO.logination(login, hashPassword);
					return user;
				} else {
					request.setAttribute("wrongLoginPasswordError", WRONG_LOGIN_PASSWORD_ERROR_MESSAGE);					
				}			
				
			} catch (DAOException e) {				
				throw new UserServiceException(e);
			}
			
		return null;
	}	
	
	
	public boolean ifLoginPasswordNull(String login, String password) {		
		if(login == null || password == null) {			
			return true;
		}		
		return false;
	}
	

}