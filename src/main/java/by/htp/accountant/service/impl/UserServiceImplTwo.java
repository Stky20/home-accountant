package by.htp.accountant.service.impl;

import static by.htp.accountant.util.validation.ValidationErrorMessage.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.bean.OperationType;
import by.htp.accountant.bean.User;
import by.htp.accountant.bean.UserBuilder;
import by.htp.accountant.controller.command.JSPPath;
import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.UserDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.PasswordClassUtilException;
import by.htp.accountant.exception.SQLUserDAOException;
import by.htp.accountant.exception.UserServiceException;
import by.htp.accountant.service.UserService;
import by.htp.accountant.util.DefaultOperationTypeManager;
import by.htp.accountant.util.HashPasswordMaker;
import by.htp.accountant.util.validation.ParameterValidator;
import by.htp.accountant.util.validation.ValidationFactory;

public class UserServiceImplTwo implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTwo.class);
	
	private UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
	private HashPasswordMaker hashPasswordMaker = HashPasswordMaker.getInstance();
	private ParameterValidator validator = ValidationFactory.getInstance().getValidator();
	
	private static final String LOGIN_PARAM = "login";
	private static final String PASSWORD_PARAM = "password";	
	private static final String NEW_PASSWORD_PARAM = "new_password";
	public static final String NEW_PASSWORD_AGAIN_PARAM = "new_password_again";
	private static final String NAME_PARAM = "name";
	private static final String SURNAME_PARAM = "surname";	
	private static final String EMAIL_PARAM = "email";
	private static final String ROLE_PARAM = "role";
	private static final String PAGE_NUMBER_PARAM = "pageNumber";
	private static final String AMOUNT_OF_PAGES_PARAM = "amountOfPages";
	private static final String USERS_LIST_PARAM = "usersList";
	private static final String USER_ID_PARAM = "id";
	private final static String ATTRIBUTE_USER = "user";
	private final static String ATTRIBUTE_OPERATION_TYPE_LIST = "operationTypeList";
	
	public static final String LOCALIZATION_ATTRIBUTE = "local";
	private static final String MODAL_ATTRIBUTE = "modal";
	
	private static final int DEFAULT_ROLE = 2;
	private static final int UNACTIVE_ROLE = 0;
	private static final int ADMIN_ROLE = 1;
	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final int DEFAULT_RECORDINGS_AMOUNT = 5;

	
	@Override	
	public void authorizeUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		User user = null;
		String login = request.getParameter(LOGIN_PARAM);		
		String hashPassword = null;
		String errorMessage = null;
		
		RequestDispatcher dispatcher = null;
		
		try {
			hashPassword = hashPasswordMaker.getHashPassword(request.getParameter(PASSWORD_PARAM));
		} catch (PasswordClassUtilException e1) {			
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}			
		
		if(dispatcher == null) {
			try {
				errorMessage = authorizationLoginPasswordCheck(login, hashPassword);
				if(errorMessage != null) {
					request.setAttribute(errorMessage, errorMessage);
					dispatcher = request.getRequestDispatcher(JSPPath.LOGIN_PAGE);
				}
			} catch (UserServiceException e) {				
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}		
		if(errorMessage == null && dispatcher == null) {
			try {
				user = userDAO.authorizeUser(login, hashPassword);
			} catch (DAOException e) {
				logger.warn("Cant do authorization User in authorizeUser() of UserServiceImpl", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}		
		if(user != null) {
			request.getSession(true).setAttribute(ATTRIBUTE_USER, user);
		}		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_MAIN_PAGE);		
	}
	
	

	@Override
	public void registrateUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		User user = null;
		UserBuilder builder = new UserBuilder();
		RequestDispatcher dispatcher = null;		
		
		String login = request.getParameter(LOGIN_PARAM);
		String passwordFromUser = request.getParameter(PASSWORD_PARAM);		
		String name = request.getParameter(NAME_PARAM);
		String surname = request.getParameter(SURNAME_PARAM);
		String email = request.getParameter(EMAIL_PARAM);		
		String local = (String)request.getSession().getAttribute(LOCALIZATION_ATTRIBUTE);
						
		List<OperationType> defaultOperationTypeList = DefaultOperationTypeManager.getDefaultOperationTypesFromPropertie(local);
		List<String> validationErrors = new ArrayList<String>();
		
		try {
			validationErrors.addAll(allParamsRegistrationCheck(login, passwordFromUser, name, surname, email));
		} catch (UserServiceException e2) {
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}		
		if(!validationErrors.isEmpty() && dispatcher == null) {
			for(String errorMsg : validationErrors) {
				request.setAttribute(errorMsg, errorMsg);
				System.out.println(errorMsg);
			}
			dispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION_PAGE);
		}		
		if(dispatcher == null) {
			try {
				user = builder.buildName(name).buildSurname(surname).buildNickName(login).
						buildHashFromPassword(passwordFromUser).buildEMail(email).buildUser();				
			} catch (PasswordClassUtilException e1) {
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}		
		if(dispatcher == null) {			
			try {
				if(userDAO.createUser(user, defaultOperationTypeList)) {
					user = userDAO.authorizeUser(user.getNickName(), user.getHashPassword());
					request.getSession(true).setAttribute(ATTRIBUTE_USER, user);					
				}			
			} catch (DAOException e) {			
				logger.warn("Cant registrate user", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_MAIN_PAGE);		
	}
	

	@Override
	public void showUsers(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		int role;
		int pageNumber;
		int amountOfPages = 0;
		
		RequestDispatcher dispatcher = null;
		String roleFromRequest =request.getParameter(ROLE_PARAM);
		String pageNumberInString = request.getParameter(PAGE_NUMBER_PARAM);		
		String modalAttribute = request.getParameter(MODAL_ATTRIBUTE);
		
		if(!validator.oneParameterNullEmptyCheck(modalAttribute)) {
			request.setAttribute(MODAL_ATTRIBUTE, modalAttribute);
		}
		
		if(!validator.oneParameterNullEmptyCheck(roleFromRequest)) {
			role = Integer.parseInt(roleFromRequest);
		}else {
			role = DEFAULT_ROLE;
		}
				
		if(!validator.oneParameterNullEmptyCheck(pageNumberInString)) {
			pageNumber = Integer.parseInt(pageNumberInString);
		}else {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}	
		
		try {
			amountOfPages = userDAO.countAmountOfPages(role, DEFAULT_RECORDINGS_AMOUNT);			
		} catch (SQLUserDAOException e) {
			logger.warn("Cant show Users beacorse of Exception in showUsers() method", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		} 
		
		int startingFrom = (pageNumber - 1) * 5;		
		
		List<User> usersList = null;
		
		if(dispatcher == null){
			try {
				usersList = userDAO.showUsers(role, DEFAULT_RECORDINGS_AMOUNT, startingFrom);
				request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
				request.setAttribute(AMOUNT_OF_PAGES_PARAM, amountOfPages);
				request.setAttribute(USERS_LIST_PARAM, usersList);
			} catch (SQLUserDAOException e) {
				logger.warn("Cant show Users beacorse of Exception in showUsers() method", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}			
		}		
		
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
		User user = (User)session.getAttribute(ATTRIBUTE_USER);
		
		try {
			if(userDAO.changeUsersRole(userId, DEFAULT_ROLE)) {
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
		
		doSendRedirectOrForwardWhithLogInRestoreMethod(request, response, dispatcher, user.getRole());
					
	}

	
	@Override
	public void diactivateUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		int userId = Integer.parseInt(request.getParameter(USER_ID_PARAM));
		User user = (User)session.getAttribute(ATTRIBUTE_USER);
		
		try {
			if(userDAO.changeUsersRole(userId, UNACTIVE_ROLE)) {
				user.setRole(UNACTIVE_ROLE);
				session.removeAttribute(ATTRIBUTE_USER);
				session.setAttribute(ATTRIBUTE_USER, user);
			}
		} catch (SQLUserDAOException e) {
			logger.warn("Can`t make user unactiv in diactivateUserMethod()", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_MAIN_PAGE);	
		
	}

	@Override
	public void makeAdmin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;		
		int userId = Integer.parseInt(request.getParameter(USER_ID_PARAM));
				
		try {
			userDAO.changeUsersRole(userId, ADMIN_ROLE);
		} catch (SQLUserDAOException e) {
			logger.warn("Can`t change user`s role to administrator in makeAdmin()", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_ADMINISTRATION_PAGE);
				
	}

	@Override
	public void changeLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
				
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();		
		User user = (User)session.getAttribute(ATTRIBUTE_USER);
		String newLogin = request.getParameter(LOGIN_PARAM);
		int userId = user.getId();
		
		List<String> validationErrorMsgs = validator.validateLogin(newLogin);
				
		if(!validationErrorMsgs.isEmpty()) {
			for(String errorMsg : validationErrorMsgs) {
				request.setAttribute(errorMsg, errorMsg);
			}
			dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
		}	
		
		try {
			if(dispatcher == null) {
				userDAO.changeLogin(userId, newLogin);	
				user.setNickName(newLogin);
				session.removeAttribute(ATTRIBUTE_USER);
				session.setAttribute(ATTRIBUTE_USER, user);
			}
		} catch (DAOException e) {
			logger.warn("Can`t change login in UserService", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_PROFILE_PAGE);		
	}

	
	@Override
	public void changePassword(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		
		User user = (User)request.getSession(true).getAttribute(ATTRIBUTE_USER);
		String oldPassword = request.getParameter(PASSWORD_PARAM);
		String newPassword = request.getParameter(NEW_PASSWORD_PARAM);		
		String newHashPassword = null;
		
		List<String> validationErrors = new ArrayList<String>();
		
		try {
			validationErrors.addAll(passwordChangingValidationCheck(user.getNickName(), oldPassword, newPassword, newHashPassword));
		} catch (UserServiceException e) {			
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}		
		
		if(!validationErrors.isEmpty() && dispatcher == null) {
			for(String errorMsg : validationErrors) {
				request.setAttribute(errorMsg, errorMsg);
			}
			dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
		} 
		
		if(dispatcher == null) {
			try {				
				newHashPassword = hashPasswordMaker.getHashPassword(newPassword);
			} catch (PasswordClassUtilException e) {
					logger.warn("Cant make hashPassword during changePassword method", e);
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
			if(dispatcher == null) {
				try {				
					userDAO.changePassword(user.getId(), newHashPassword);							
				} catch (DAOException e) {
					logger.warn("Cant check or change password in Service during chengePassword method", e);
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
				}	
			}
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_PROFILE_PAGE);				
		
	}

	@Override
	public void changeUserInfo(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		List<String> validationErrors = new ArrayList<String>();
		
		User user = (User)session.getAttribute(ATTRIBUTE_USER);
		String name = request.getParameter(NAME_PARAM);
		String surname = request.getParameter(SURNAME_PARAM);
		String email = request.getParameter(EMAIL_PARAM);
		
		validationErrors.addAll(validator.validateNameSurnameEmail(name, surname, email));
				
		if(validationErrors.isEmpty()) {						
			try {
				if(userDAO.editUser(user.getId(), name, surname, email)) {						
					if(!validator.oneParameterNullEmptyCheck(name)) {
						user.setName(name);
					}
					if(!validator.oneParameterNullEmptyCheck(surname)) {
						user.setSurname(surname);
					}
					if(!validator.oneParameterNullEmptyCheck(email)) {
						user.seteMail(email);
					}
					session.removeAttribute(ATTRIBUTE_USER);
					session.setAttribute(ATTRIBUTE_USER, user);					
				}else {
					request.setAttribute(NO_CHANGES_MSG, NO_CHANGES_MSG);
					logger.info("Somehow changes woren`t dun in method chengeUserInfo becourse of prepear statement in DAO returned 0");
					dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
				}
			} catch (DAOException e) {
				logger.warn("Cant edit User in Service during changeUserInfo() method", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}			
		} else {
			for(String errorMsg : validationErrors) {
				request.setAttribute(errorMsg, errorMsg);
			}
			dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_PROFILE_PAGE);		
	}

	@Override
	public void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		String userIdInString = request.getParameter(USER_ID_PARAM);
		int userId = 0;
		
		if(!validator.oneParameterNullEmptyCheck(userIdInString)) {
			userId = Integer.parseInt(userIdInString);
		} else {
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}		
		
		try {
			if(!userDAO.removeUser(userId)) {
				logger.info("Deletion wasn`t performed");
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);				
			}
		} catch (DAOException e) {
			logger.warn("Can`t delete user in deleteUser() method", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_ADMINISTRATION_PAGE);		
	}
		
	
	/**
	 * Private method which checks login and hashPasword if it null or empty, 
	 * if non of it is empty method checks login and password in DataBase.
	 * If some of parameters not fulfill database values, method returns String - errorMessage.
	 * If parameters satisfy to conditions method returns null.
	 * @param login - String
	 * @param hashPassword - String
	 * @return errorMessage - String, constant String which describes not fulfilled conditions, or null if conditions are met.
	 * @throws UserServiceException - when there is an exception in database.
	 */
	private String authorizationLoginPasswordCheck(String login, String hashPassword) throws UserServiceException{
		String errorMessages = null;
		
		if (validator.ifSomeOfParamsNullEmptyCheck(login, hashPassword)){   															
			return EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE;			 
		}
		
		try {					
			if(!userDAO.checkLogin(login)) {																		
				return WRONG_LOGIN_ERROR_MESSAGE;				
			} 
			else if(!userDAO.checkPassword(login, hashPassword)){
				return WRONG_PASSWORD_ERROR_MESSAGE;				
			} 					
		} catch (DAOException e) {				
				logger.warn("Can`t check login or password during authorization", e);
				throw new UserServiceException("Couldn`t execute login or password check in DB", e);
		}		
		return errorMessages;
	}
	
	
	/**
	 * Private method which checks login if it null or empty , 
	 * then check if login is already exist in DataBase.
	 * If login null or empty or exist in database then method returns String - errorMessage.
	 * If parameter satisfy to conditions method returns null.
	 * @param login - String
	 * @return errorMessages - String - constant String which describes not fulfilled conditions, or null if conditions are met.
	 * @throws UserServiceException - when there is an exception in database.
	 */
	private String loginCheckForRegistration(String login) throws UserServiceException {
		String errorMessages = null;
		
		if (validator.oneParameterNullEmptyCheck(login)){   															
			return EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE;			 
		}
		
		try {					
			if(userDAO.checkLogin(login)) {																		
				return LOGIN_OCCUPIED_ERROR_MESSAGE;				
			}
		} catch (DAOException e) {				
			logger.warn("Can`t check login during registration", e);
			throw new UserServiceException("Couldn`t execute login in DB", e);
		}
		return errorMessages;
	}
	
	
	/**
	 * Private method used for User registrations, checks all parameters with ParameterValidator class
	 * and checks login in DB with private method - loginCheckForRegistration();
	 * @param login - String
	 * @param password - String
	 * @param name - String
	 * @param surname - String
	 * @param email - String
	 * @return errorMsgs - List<String> - if all Requirements match method returns empty List.
	 * @throws UserServiceException  - when there is an exception in database.
	 */
	private List<String> allParamsRegistrationCheck(String login, String password, String name, String surname, String email) throws UserServiceException{
		List<String> errorMsgs = new ArrayList<String>();
		
		errorMsgs.addAll(validator.validateLogin(login));
		errorMsgs.addAll(validator.validatePassword(password));
		errorMsgs.addAll(validator.validateNameSurnameEmailForRegistration(name, surname, email));
		
		if(errorMsgs.isEmpty()) {
			String messageAfterLoginDBCheck = null; 
			
			try {
				messageAfterLoginDBCheck = loginCheckForRegistration(login);				
			} catch (UserServiceException e) {
				throw e;
			}
			
			if(messageAfterLoginDBCheck != null) {
				errorMsgs.add(messageAfterLoginDBCheck);
			}
		}		
		
		
		
		return errorMsgs;
	}
	
	/**
	 * 
	 * @param login
	 * @param oldPassword
	 * @param newPassword
	 * @param newPasswordAgain
	 * @return
	 * @throws UserServiceException
	 */
	private List<String> passwordChangingValidationCheck(String login, String oldPassword, String newPassword, String newPasswordAgain) 
			throws UserServiceException{
		
		List<String> errorMsgs = new ArrayList<String>();
		String passwordDBCheckErrorMSG = null;
		
		errorMsgs.addAll(validator.validateNewPassword(oldPassword, newPassword, newPasswordAgain));		
		
		if(errorMsgs.isEmpty()) {
			passwordDBCheckErrorMSG = passwordDBCheckForChanging(login, oldPassword);			
		}
		
		if(passwordDBCheckErrorMSG != null) {
			errorMsgs.add(passwordDBCheckErrorMSG);
		}
		
		return errorMsgs;
	}
	
	
	/**
	 * 
	 * @param login
	 * @param hashPassword
	 * @return
	 * @throws UserServiceException
	 */
	private String passwordDBCheckForChanging(String login, String password) throws UserServiceException {
		String errorMsg = null;
		String hashPassword = null;
		
		try {
			hashPassword = hashPasswordMaker.getHashPassword(password);
		} catch (PasswordClassUtilException e) {
			logger.warn("Can`t mace hashPassword during passwordCheckForChanging", e);
			throw new UserServiceException("PasswordClassUtilException during passwordCheckForChanging", e);
		}
		
		if (validator.oneParameterNullEmptyCheck(hashPassword)){   															
			return EMPTY_LOGIN_PASSWORD_ERROR_MESSAGE;			 
		}
		
		try {					
			if(!userDAO.checkPassword(login, hashPassword)) {																		
				return WRONG_PASSWORD_ERROR_MESSAGE;				
			}
		} catch (DAOException e) {				
			logger.warn("Can`t check password during passwordCheckForChanging", e);
			throw new UserServiceException("Couldn`t execute passwordCheck in DB", e);
		}
		
		return errorMsg;
	}
	
	
	/**
	 * Private method of UserServiceImpl which do forward or redirect depends on RequestDispatcher value. 
	 * Also method makes log if forward or sendRedirect throw exception.
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @param dispatcher - RequestDispatcher
	 * @param redirectPage - String
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doSendRedirectOrForward(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher, String redirectPage) 
					throws IOException, ServletException {
		
		if(dispatcher == null) {		
			try {
				response.sendRedirect(redirectPage);
			} catch (IOException e) {
				logger.warn("IOException while doing sendRedirect in UserServiceImpl", e);
				throw e;
			}	
		}else {	
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				logger.warn("ServletException while doing forward in UserServiceImpl", e);
				throw e;
			} catch (IOException e) {
				logger.warn("IOException while doing forward in UserServiceImpl", e);
				throw e;
			}				
		}
	}
		
	/**
	 * Private method of UserServiceImpl which do forward or redirect depends on RequestDispatcher and user`s role values. 
	 * Also method makes log if forward or sendRedirect throw exception.
	 * @param request - HttpServletResponse
	 * @param response - HttpServletResponse
	 * @param dispatcher - RequestDispatcher
	 * @param userRole - int, role of user which calls this method.
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doSendRedirectOrForwardWhithLogInRestoreMethod
				(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher, int userRole) 
				throws IOException, ServletException {
	
		if(dispatcher == null) {		
			try {
				if(userRole == 2) {				
					response.sendRedirect(JSPPath.GO_TO_MAIN_PAGE);				
				} else {
					response.sendRedirect(JSPPath.GO_TO_USER_ADMINISTRATION_PAGE);
				}
			} catch (IOException e) {
				logger.warn("IOException while doing sendRedirect in doSendRedirectOrForwardWhithLogInRestoreMethod() method of UserServiceImplTwo", e);
				throw e;
			}
		} else {			
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				logger.warn("ServletException while doing forward in doSendRedirectOrForwardWhithLogInRestoreMethod() method of UserServiceImplTwo", e);
				throw e;
			} catch (IOException e) {
				logger.warn("IOException while doing forward in doSendRedirectOrForwardWhithLogInRestoreMethod() method of UserServiceImplTwo", e);
				throw e;
			}				
		}			
	}

}
