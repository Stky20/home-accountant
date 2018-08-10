package by.htp.accountant.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.UserServiceException;
import by.htp.accountant.bean.HashPasswordMaker;
import by.htp.accountant.bean.User;
import by.htp.accountant.service.UserService;

public class UserServiceImpl implements UserService {	
	
	
	public static final String LOGIN_PARAM = "login";
	public static final String PASSWORD_PARAM = "password";

	public static final String EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE = "emptyLoginPasswordErrorMsg";
	public static final String NULL_HASH_ERROR_MESSAGE = "nullHashErrorMsg";
	public static final String WRONG_LOGIN_ERROR_MESSAGE = "wrongLoginErrorMsg";
	public static final String WRONG_PASSWORD_ERROR_MESSAGE = "wrongPasswordErrorMsg";
	

	@Override
	public User logination(HttpServletRequest request) throws UserServiceException {
		
		DAOFactory factoryDAO = DAOFactory.getInstance();
		UserDAO userDAO = factoryDAO.getUserDAO();
		
		User user = null;
		String login = null;
		String passwordFromUser = null;
		String hashPassword = null;
		HashPasswordMaker hashPasswordMaker = HashPasswordMaker.getInstance();
		
		login = request.getParameter(LOGIN_PARAM);
		passwordFromUser = request.getParameter(PASSWORD_PARAM);
		
			if (isLoginPasswordEmpty(login, passwordFromUser)){   											//simple  empty check				
				request.setAttribute(EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE, EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE);
				return null;
			}
		
		hashPassword = hashPasswordMaker.getHashPassword(passwordFromUser);                                 //returns null if there are problem/exception while making hash
		
			if (hashPassword == null){																		//check again because of hashPassword can be null
				request.setAttribute(NULL_HASH_ERROR_MESSAGE, NULL_HASH_ERROR_MESSAGE);
				return null;
			}
			
				try {					
						if(!userDAO.checkLogin(login)) {													//check login in DB for error msg					
							request.setAttribute(WRONG_LOGIN_ERROR_MESSAGE, WRONG_LOGIN_ERROR_MESSAGE);
						} 
						else if(!userDAO.checkPassword(login, hashPassword)){									//check password in DB for error msg
							request.setAttribute(WRONG_PASSWORD_ERROR_MESSAGE, WRONG_PASSWORD_ERROR_MESSAGE);	
						}	
						if(userDAO.checkLoginAndPassword(login, hashPassword)) {
							user = userDAO.logination(login, hashPassword);
							return user;
						}
						
				} catch (DAOException e) {				
						throw new UserServiceException(e);
				}
			
				
		return null;
			
	}
	
	
	public boolean isLoginPasswordEmpty(String login, String hashPassword) {		
		if(login.trim().isEmpty() || hashPassword.trim().isEmpty()) {			
			return true;
		}		
		return false;
	}
	

}