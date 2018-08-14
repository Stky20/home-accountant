package by.htp.accountant.service.impl;

import javax.servlet.http.HttpServletRequest;

import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.UserServiceException;
import by.htp.accountant.bean.HashPasswordMaker;
import by.htp.accountant.bean.User;
import by.htp.accountant.bean.UserBuilder;
import by.htp.accountant.bean.Validator;
import by.htp.accountant.service.UserService;

public class UserServiceImpl implements UserService {		
	 
	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();	
	
	public static final String LOGIN_PARAM = "login";
	public static final String PASSWORD_PARAM = "password";
	public static final String NAME_PARAM = "name";
	public static final String SURNAME_PARAM = "surname";	
	public static final String EMAIL_PARAM = "email";

	public static final String EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE = "emptyLoginPasswordErrorMsg";
	public static final String EMPTY_LOGIN_ERROR_MESSAGE = "emptyLoginErrorMsg";
	public static final String EMPTY_PASSWORD_ERROR_MESSAGE = "emptyPasswordErrorMsg";
	public static final String NULL_HASH_ERROR_MESSAGE = "nullHashErrorMsg";
	public static final String WRONG_LOGIN_ERROR_MESSAGE = "wrongLoginErrorMsg";
	public static final String WRONG_PASSWORD_ERROR_MESSAGE = "wrongPasswordErrorMsg";
	public static final String WRONG_NAME_ERROR_MESSAGE = "wrongNameErrorMsg";
	public static final String WRONG_SURNAME_ERROR_MESSAGE = "wrongSurnameErrorMsg";
	public static final String WRONG_EMAIL_ERROR_MESSAGE = "wrongEmailErrorMsg";
	

	@Override
	public User logination(HttpServletRequest request) throws UserServiceException {		
		
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
	
	
	@Override
	public User registration(HttpServletRequest request) throws UserServiceException {
		
		User user = null;
		UserBuilder builder = new UserBuilder();
		
		String login = null;
		String passwordFromUser = null;		
		String name = null;
		String surname = null;
		String email = null;
		
		login = request.getParameter(LOGIN_PARAM);
		passwordFromUser = request.getParameter(PASSWORD_PARAM);	
		name = request.getParameter(NAME_PARAM);
		surname = request.getParameter(SURNAME_PARAM);		
		email = request.getParameter(EMAIL_PARAM);

		if(isParameterEmpty(login)) {																//login check if it empty, if it matches pattern, error msg setting
			request.setAttribute(EMPTY_LOGIN_ERROR_MESSAGE, EMPTY_LOGIN_ERROR_MESSAGE);
		} else {
			if(Validator.loginRegularCheck(login)) {
				builder = builder.buildNickName(login);
			} else {
				request.setAttribute(WRONG_LOGIN_ERROR_MESSAGE, WRONG_LOGIN_ERROR_MESSAGE);
			}
		}		
		
		if(isParameterEmpty(passwordFromUser)) {														//password check if it is empty, if it matches pattern, if hashPassword not null, error msg setting
			request.setAttribute(EMPTY_PASSWORD_ERROR_MESSAGE, EMPTY_PASSWORD_ERROR_MESSAGE);
		}else {
			if(Validator.loginRegularCheck(login)) {				
				builder = builder.buildHashFromPassword(passwordFromUser);				
					if (builder.buildUser().getHashPassword() == null){																		//one more check because of hashPassword can be null
						request.setAttribute(NULL_HASH_ERROR_MESSAGE, NULL_HASH_ERROR_MESSAGE);							
					}					
			} else {
				request.setAttribute(WRONG_PASSWORD_ERROR_MESSAGE, WRONG_PASSWORD_ERROR_MESSAGE);
			}
		}		
		
		if(!isParameterEmpty(name)) {																	//name check it matches pattern, error msg setting
			if(Validator.nameRegularCheck(name)) {
				builder = builder.buildName(name);
			} else {
				request.setAttribute(WRONG_NAME_ERROR_MESSAGE, WRONG_NAME_ERROR_MESSAGE);
			}
		}		
		
		if(!isParameterEmpty(surname)) {																//surname check it matches pattern, error msg setting
			if(Validator.surnameRegularCheck(surname)) {
				builder = builder.buildSurname(surname);
			} else {
				request.setAttribute(WRONG_SURNAME_ERROR_MESSAGE, WRONG_SURNAME_ERROR_MESSAGE);
			}
		}		
		
		if(!isParameterEmpty(email)) {																	//email check it matches pattern, error msg setting
			if(Validator.emailRegularCheck(email)) {
				builder = builder.buildEMail(email);
			} else {
				request.setAttribute(WRONG_EMAIL_ERROR_MESSAGE, WRONG_EMAIL_ERROR_MESSAGE);
			}
		}	
		
		user = builder.buildUser();
		
		if(isLoginPasswordEmpty(user.getNickName(), user.getHashPassword())) {			
			return null;			
		} else if ((!isParameterEmpty(name)&& !Validator.nameRegularCheck(name)) || 								//check if name or surname or email are not null and are not match patterns
				(!isParameterEmpty(surname) && !Validator.surnameRegularCheck(surname)) || 
				(!isParameterEmpty(email) && !Validator.emailRegularCheck(email))) {					
			return null;
		}		
		
		try {
			if(userDAO.createUser(user)) {
				user = userDAO.logination(user.getNickName(), user.getHashPassword());
			}
		} catch (DAOException e) {			
			throw new UserServiceException("DAO Exception during registration user occured", e);
		}
		
		return user;
	}
	
	
	public boolean isLoginPasswordEmpty(String login, String hashPassword) {		
		if (login == null || hashPassword == null) {
			return true;
		}
		if(login.trim().isEmpty() || hashPassword.trim().isEmpty()) {			
			return true;
		}		
		return false;
	}
	
	public boolean isParameterEmpty(String parameter) {		
		return parameter.trim().isEmpty();
	}


	
	

}