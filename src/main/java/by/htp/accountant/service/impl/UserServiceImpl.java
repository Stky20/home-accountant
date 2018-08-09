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
	public static final String NULL_LOGIN_PASSWORD_ERROR_MESSAGE = "Login and password are null";
	public static final String EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE = "Login and password are null";
	public static final String NULL_HASH_ERROR_MESSAGE = "hashPassword is null";
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
		
			if (isLoginPasswordNull(login, passwordFromUser)){
				request.setAttribute("nullErrorMsg", NULL_LOGIN_PASSWORD_ERROR_MESSAGE);
				return null;
			}
		
		hashPassword = hashPasswordMaker.getHashPassword(passwordFromUser);
		
			if (isLoginPasswordNull(login, hashPassword)){
				request.setAttribute("nullHashErrorMsg", NULL_HASH_ERROR_MESSAGE);
				return null;
			}
			
			if(isLoginPasswordEmpty(login, hashPassword)) {
				request.setAttribute("emptyLoginPasswordErrorMsg", EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE);
				return null;
			}
				
				try {					
						if(userDAO.checkLoginAndPassword(login, hashPassword)) {
							user = userDAO.logination(login, hashPassword);
							return user;
						} else {
							request.setAttribute("wrongLoginPasswordErrorMsg", WRONG_LOGIN_PASSWORD_ERROR_MESSAGE);					
						}			
						
				} catch (DAOException e) {				
						throw new UserServiceException(e);
				}
			
				
		return null;
			
	}	
	
	
	public boolean isLoginPasswordNull(String login, String password) {		
		if(login == null || password == null) {			
			return true;
		}		
		return false;
	}
	
	public boolean isLoginPasswordEmpty(String login, String hashPassword) {		
		if(login.trim().isEmpty() || hashPassword.trim().isEmpty()) {			
			return true;
		}		
		return false;
	}
	

}