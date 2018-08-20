package by.htp.accountant.service.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.PasswordClassUtilException;
import by.htp.accountant.bean.User;
import by.htp.accountant.bean.UserBuilder;
import by.htp.accountant.controller.command.JSPPath;
import by.htp.accountant.service.UserService;
import by.htp.accountant.util.HashPasswordMaker;
import by.htp.accountant.util.Validator;

public class UserServiceImpl implements UserService {		
	 
	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
	HashPasswordMaker hashPasswordMaker = HashPasswordMaker.getInstance();
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	public static final String LOGIN_PARAM = "login";
	public static final String PASSWORD_PARAM = "password";
	public static final String NAME_PARAM = "name";
	public static final String SURNAME_PARAM = "surname";	
	public static final String EMAIL_PARAM = "email";

	public static final String EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE = "emptyLoginPasswordErrorMsg";
	public static final String WRONG_LOGIN_ERROR_MESSAGE = "wrongLoginErrorMsg";
	public static final String WRONG_PASSWORD_ERROR_MESSAGE = "wrongPasswordErrorMsg";
	
	
	/**
	 * Method of UserServiceImpl which gets user from DB if login and password correspond to login and password in DB 
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@Override
	public void logination(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		User user = null;
		String login = request.getParameter(LOGIN_PARAM);		
		String hashPassword = null;
		
		RequestDispatcher dispatcher = null;
		
		try {
			hashPassword = hashPasswordMaker.getHashPassword(request.getParameter(PASSWORD_PARAM));
		} catch (PasswordClassUtilException e1) {			
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}			
		
		if (Validator.simpleNullEmptyCheck(login, hashPassword)){   											//simple  empty check				
			request.setAttribute(EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE, EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE);
			if(dispatcher == null) {
				dispatcher = request.getRequestDispatcher(JSPPath.LOGIN_PAGE);
			}
		} else {			
			try {					
					if(!userDAO.checkLogin(login)) {													//check login in DB for error msg					
						request.setAttribute(WRONG_LOGIN_ERROR_MESSAGE, WRONG_LOGIN_ERROR_MESSAGE);
						if(dispatcher == null) {
							dispatcher = request.getRequestDispatcher(JSPPath.LOGIN_PAGE);
						}
					} 
					else if(!userDAO.checkPassword(login, hashPassword)){									//check password in DB for error msg
						request.setAttribute(WRONG_PASSWORD_ERROR_MESSAGE, WRONG_PASSWORD_ERROR_MESSAGE);	
						if(dispatcher == null) {
							dispatcher = request.getRequestDispatcher(JSPPath.LOGIN_PAGE);
						}
					} else {						
						user = userDAO.logination(login, hashPassword);
					}						
			} catch (DAOException e) {				
					logger.warn("Cant do logination in UserServiceImpl", e);
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}	
		if(user != null) {
			request.getSession(true).setAttribute("user", user);
		}
		
		doSendRedirectOrForward(request, response, dispatcher);								
	}
	
	
	@Override
	public void registration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		User user = null;
		UserBuilder builder = new UserBuilder();
		
		String login = request.getParameter(LOGIN_PARAM);;
		String passwordFromUser = request.getParameter(PASSWORD_PARAM);		
		String name = request.getParameter(NAME_PARAM);
		String surname = request.getParameter(SURNAME_PARAM);
		String email = request.getParameter(EMAIL_PARAM);
		
		RequestDispatcher dispatcher = null;
		
		Validator.validateLogin(request, builder, login);
		
		Validator.validatePassword(request, builder, dispatcher, passwordFromUser);
		
		Validator.validateName(request, builder, surname);
			
		Validator.validateSurname(request, builder, surname);
		
		Validator.validateEmail(request, builder, email);
				
		user = builder.buildUser();
		
		if(Validator.simpleNullEmptyCheck(user.getNickName(), user.getHashPassword()) ||
		   (!Validator.simpleOneParameterNullEmptyCheck(name) && !Validator.nameRegularCheck(name)) ||	
		   (!Validator.simpleOneParameterNullEmptyCheck(surname) && !Validator.surnameRegularCheck(surname)) ||
		   (!Validator.simpleOneParameterNullEmptyCheck(email) && !Validator.emailRegularCheck(email))) {			
			
			if (dispatcher == null) {
				dispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION_PAGE);
			}			
		} 	
		
		if(dispatcher == null) {
			try {
				
				if(userDAO.createUser(user)) {
					user = userDAO.logination(user.getNickName(), user.getHashPassword());
					request.getSession(true).setAttribute("user", user);
				}
				
			} catch (DAOException e) {			
				logger.warn("Cant registrate user", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}					
		
		doSendRedirectOrForward(request, response, dispatcher);
	}		

	
	/**
	 * Private method of UserServiceImpl which do forward or redirect depends on RequestDispatcher value
	 * @param HttpServletRequest request
	 * @param HttpServletResponse response
	 * @param RequestDispatcher dispatcher
	 * @param User user
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doSendRedirectOrForward(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher) throws IOException, ServletException {
		
		if(dispatcher == null) {		
			
			try {
				response.sendRedirect(JSPPath.GO_TO_MAIN_PAGE);
			} catch (IOException e) {
				logger.warn("Can`t do sendRedirect during loginstion", e);	
				throw e;
			}
			
		}else {		
								
			try {
					dispatcher.forward(request, response);
				} catch (ServletException e) {	
					logger.warn("Can`t do forward during loginstion", e);
					throw e;
				} catch (IOException e) {	
					logger.warn("Can`t do forward during loginstion", e);
					throw e;
				}
		}			
	}	

}