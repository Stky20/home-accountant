package by.htp.accountant.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.PasswordClassUtilException;
import by.htp.accountant.exception.SQLUserDAOException;
import by.htp.accountant.bean.User;
import by.htp.accountant.bean.UserBuilder;
import by.htp.accountant.controller.command.JSPPath;
import by.htp.accountant.service.UserService;
import by.htp.accountant.util.HashPasswordMaker;
import by.htp.accountant.util.Validator;

public class UserServiceImpl implements UserService {		
	 
	UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
	
	HashPasswordMaker hashPasswordMaker = HashPasswordMaker.getInstance();
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	public static final String LOGIN_PARAM = "login";
	public static final String PASSWORD_PARAM = "password";	
	public static final String NEW_PASSWORD_PARAM = "new_password";
	public static final String NAME_PARAM = "name";
	public static final String SURNAME_PARAM = "surname";	
	public static final String EMAIL_PARAM = "email";
	public static final String ROLE_PARAM = "role";
	public static final String PAGE_NUMBER_PARAM = "pageNumber";
	public static final String AMOUNT_OF_PAGES_PARAM = "amountOfPages";
	public static final String USERS_LIST_PARAM = "usersList";
	public static final String USER_ID_PARAM = "id";
	public final static String ATTRIBUTE_USER = "user";

	public static final String EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE = "emptyLoginPasswordErrorMsg";
	public static final String EMPTY_FIELD_ERROR_MESSAGE = "emptyFieldErrorMsg";
	public static final String WRONG_LOGIN_ERROR_MESSAGE = "wrongLoginErrorMsg";
	public static final String WRONG_PASSWORD_ERROR_MESSAGE = "wrongPasswordErrorMsg";	
	public static final String NO_CHANGES_MSG = "noChangesMsg";
	
	public static final int DEFAULT_ROLE = 2;
	public static final int UNACTIVE_ROLE = 0;
	public static final int ADMIN_ROLE = 1;
	public static final int DEFAULT_PAGE_NUMBER = 1;
	public static final int DEFAULT_RECORDINGS_AMOUNT = 5;
	
	
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
			request.getSession(true).setAttribute(ATTRIBUTE_USER, user);
		}
		
		doSendRedirectOrForward(request, response, dispatcher);								
	}
	
	
	
	@Override
	public void registration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		User user = null;
		UserBuilder builder = new UserBuilder();
		
		String login = request.getParameter(LOGIN_PARAM);
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
	
	
	
	@Override
	public void showUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		int role;
		int pageNumber;
		RequestDispatcher dispatcher = null;
		String roleFromRequest =request.getParameter(ROLE_PARAM);
		String pageNumberInString = request.getParameter(PAGE_NUMBER_PARAM);
		
		if(roleFromRequest != null) {
			role = Integer.parseInt(roleFromRequest);
		}else {
			role = DEFAULT_ROLE;
		}
				
		if(pageNumberInString != null) {
			pageNumber = Integer.parseInt(pageNumberInString);
		}else {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}
				
		int amountOfPages = 0;
		
		try {
			amountOfPages = userDAO.countAmountOfPages(role, DEFAULT_RECORDINGS_AMOUNT);			
		} catch (SQLUserDAOException e) {
			logger.warn("Cant show Users beacorse of Exception in showUsers() method", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		} 
		
		int startingFrom = (pageNumber - 1) * 5;		
		
		List<User> usersList = null;
		
		try {
			usersList = userDAO.showUsers(role, DEFAULT_RECORDINGS_AMOUNT, startingFrom);
		} catch (SQLUserDAOException e) {
			logger.warn("Cant show Users beacorse of Exception in showUsers() method", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
				
		request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
		request.setAttribute(AMOUNT_OF_PAGES_PARAM, amountOfPages);
		request.setAttribute(USERS_LIST_PARAM, usersList);
		
		if(dispatcher == null) {
			dispatcher = request.getRequestDispatcher(JSPPath.USER_ADMINISTRATION_PAGE);
		}
		
		dispatcher.forward(request, response);
		
	}	

	
	@Override
	public void restoreUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		int userId = Integer.parseInt(request.getParameter(USER_ID_PARAM));
		User user = null;
		
		try {
			if(userDAO.changeUsersRole(userId, DEFAULT_ROLE)) {
				
				user = (User)session.getAttribute(ATTRIBUTE_USER);
				
				if(user.getRole() == 0) {
					user.setRole(DEFAULT_ROLE);
					session.removeAttribute(ATTRIBUTE_USER);
					session.setAttribute(ATTRIBUTE_USER, user);
				}
				
			}
		} catch (SQLUserDAOException e) {
			logger.warn("Can`t make user activ in restoreUserMethod()", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		if(dispatcher == null) {		
			if(user.getRole() == 0) {
				response.sendRedirect(JSPPath.GO_TO_MAIN_PAGE);
			} else {
				response.sendRedirect(JSPPath.GO_TO_USER_ADMINISTRATION_PAGE);
			}
		}else {	
			dispatcher.forward(request, response);				
		}	
		
	}
	
	
	@Override
	public void diactivateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		int userId = Integer.parseInt(request.getParameter(USER_ID_PARAM));
		User user = null;
		
		try {
			if(userDAO.changeUsersRole(userId, UNACTIVE_ROLE)) {
				user = (User)session.getAttribute(ATTRIBUTE_USER);
				user.setRole(UNACTIVE_ROLE);
				session.removeAttribute(ATTRIBUTE_USER);
				session.setAttribute(ATTRIBUTE_USER, user);
			}
		} catch (SQLUserDAOException e) {
			logger.warn("Can`t make user activ in diactivateUserMethod()", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		doSendRedirectOrForward(request, response, dispatcher);		
		
	}
	
	@Override
	public void makeAdmin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		int userId = Integer.parseInt(request.getParameter(USER_ID_PARAM));
		User user = null;
		
		try {
			if(userDAO.changeUsersRole(userId, ADMIN_ROLE)) {
				user = (User)session.getAttribute(ATTRIBUTE_USER);
				user.setRole(ADMIN_ROLE);
				session.removeAttribute(ATTRIBUTE_USER);
				session.setAttribute(ATTRIBUTE_USER, user);
			}
		} catch (SQLUserDAOException e) {
			logger.warn("Can`t make user activ in diactivateUserMethod()", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		if(dispatcher == null) {		
			response.sendRedirect(JSPPath.GO_TO_USER_ADMINISTRATION_PAGE);	
		}else {	
			dispatcher.forward(request, response);				
		}		
	}



	
	
	
	@Override
	public void changeLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
				
		UserBuilder builder = new UserBuilder();
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute(ATTRIBUTE_USER);
		String newLogin = request.getParameter(LOGIN_PARAM);
		int userId = user.getId();
		
		Validator.validateLogin(request, builder, newLogin);
				
		if(Validator.simpleOneParameterNullEmptyCheck(newLogin) || !Validator.loginRegularCheck(newLogin)) {			
			dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
		}	
		
		try {
			if(dispatcher == null) {
				userDAO.changeLogin(userId, newLogin);
				
				user = builder.buildName(user.getName()).
						buildExistingHashPassword(user.getHashPassword()).
						buildId(userId).
						buildSurname(user.getSurname()).
						buildEMail(user.geteMail()).
						buildRole(user.getRole()).
						buildUser();
				session.removeAttribute(ATTRIBUTE_USER);
				session.setAttribute(ATTRIBUTE_USER, user);
			}
		} catch (DAOException e) {
			logger.warn("Can`t change login in UserService", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		
		 	
		if(dispatcher == null) {			
			response.sendRedirect(JSPPath.GO_TO_USER_PROFILE_PAGE);			
		}else {				
			dispatcher.forward(request, response);				
		}			
		
	}

	

	@Override
	public void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		
		User user = (User)request.getSession(true).getAttribute(ATTRIBUTE_USER);
		String oldHashPassword = null;
		String newHashPassword = null;		
		
		if(!Validator.validateNewPassword(request)) {
			dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
		} else {
			try {
				oldHashPassword = hashPasswordMaker.getHashPassword(request.getParameter(PASSWORD_PARAM));
				newHashPassword = hashPasswordMaker.getHashPassword(request.getParameter(NEW_PASSWORD_PARAM));
			} catch (PasswordClassUtilException e) {
					logger.warn("Cant make hashPassword during changePassword method", e);
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
			
			try {				
				if (!userDAO.checkPassword(user.getNickName(), oldHashPassword)) {
					request.setAttribute(WRONG_PASSWORD_ERROR_MESSAGE, WRONG_PASSWORD_ERROR_MESSAGE);			
					dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
				}else {					
					if(dispatcher == null) {
						userDAO.changePassword(user.getId(), newHashPassword);
					}					
				}				
			} catch (DAOException e) {
				logger.warn("Cant check or change password in Service during chengePassword method", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}			
		}		
		
		if(dispatcher == null) {			
			response.sendRedirect(JSPPath.GO_TO_USER_PROFILE_PAGE);			
		}else {				
			dispatcher.forward(request, response);				
		}					
		
	}


	
	@Override
	public void changeUserInfo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		
		User user = (User)session.getAttribute(ATTRIBUTE_USER);
		String name = request.getParameter(NAME_PARAM);
		String surname = request.getParameter(SURNAME_PARAM);
		String email = request.getParameter(EMAIL_PARAM);
		
		if(!Validator.validateUserInfo(request, user, name, surname, email)) {
			dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
		}			
		
		if(dispatcher == null) {
						
			try {
				if(userDAO.editUser(user.getId(), user.getName(), user.getSurname(), user.geteMail())) {						
					
					session.removeAttribute(ATTRIBUTE_USER);
					session.setAttribute(ATTRIBUTE_USER, user);					
					
				}else {
					request.setAttribute(NO_CHANGES_MSG, NO_CHANGES_MSG);
					logger.info("Somehow changes woren`t dun in method chengeUserInfo becourse of prepear statement in DAO returned 0");
					dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
				}
			} catch (DAOException e) {
				logger.warn("Cant edit User in Service during editUser() method", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
			
		}
		
		if(dispatcher == null) {		
			response.sendRedirect(JSPPath.GO_TO_USER_PROFILE_PAGE);	
		}else {	
			dispatcher.forward(request, response);				
		}				

	}
	
	
		
	@Override
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {		
		
		RequestDispatcher dispatcher = null;		
		int userId = Integer.parseInt(request.getParameter(USER_ID_PARAM));		
		
		try {
			if(!userDAO.removeUser(userId)) {	
				dispatcher = request.getRequestDispatcher(JSPPath.USER_ADMINISTRATION_PAGE);
				logger.info("Deletion wasn`t performed");
			}
		} catch (DAOException e) {
			logger.warn("Can`t delete user in deleteUser() method", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		if(dispatcher == null) {		
			response.sendRedirect(JSPPath.GO_TO_USER_ADMINISTRATION_PAGE);	
		}else {	
			dispatcher.forward(request, response);				
		}		
		
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
	private void doSendRedirectOrForward(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher)
										throws IOException, ServletException {
		
		if(dispatcher == null) {		
			response.sendRedirect(JSPPath.GO_TO_MAIN_PAGE);	
		}else {	
			dispatcher.forward(request, response);				
		}			
	}



	


	

}