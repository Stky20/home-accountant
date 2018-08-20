package by.htp.accountant.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import by.htp.accountant.bean.UserBuilder;
import by.htp.accountant.controller.command.JSPPath;
import by.htp.accountant.exception.PasswordClassUtilException;

public class Validator {
	
	private static final Logger logger = Logger.getLogger(Validator.class);
	
	public final static String LOGIN_PATTERN = "[\\wА-Яа-я-_]{1,25}";	
	public final static String PASSWORD_PATTERN = "[\\wА-Яа-я]{6,10}";
	public final static String NAME_PATTERN = "[\\wА-Яа-я-&&[^\\d]]{1,20}";
	public final static String SURNAME_PATTERN = "[\\wА-Яа-я-&&[^\\d]]{1,25}";
	public final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,7})$";
	
	public static final String EMPTY_LOGIN_ERROR_MESSAGE = "emptyLoginErrorMsg";
	public static final String EMPTY_PASSWORD_ERROR_MESSAGE = "emptyPasswordErrorMsg";	
	public static final String WRONG_LOGIN_ERROR_MESSAGE = "wrongLoginErrorMsg";
	public static final String WRONG_PASSWORD_ERROR_MESSAGE = "wrongPasswordErrorMsg";
	public static final String WRONG_NAME_ERROR_MESSAGE = "wrongNameErrorMsg";
	public static final String WRONG_SURNAME_ERROR_MESSAGE = "wrongSurnameErrorMsg";
	public static final String WRONG_EMAIL_ERROR_MESSAGE = "wrongEmailErrorMsg";
	
	/**
	 * Checks two Strings if some of it is null or empty returns true
	 * else returns false
	 * @param String
	 * @param String
	 */
	public static boolean simpleNullEmptyCheck(String login, String password) {
		if (login == null || password == null) {
			return true;
		}
		if(login.trim().isEmpty() || password.trim().isEmpty()) {			
			return true;
		}		
		return false;
	}
	
	/**
	 * Checks one String, if it is null or empty returns true
	 * else returns false
	 * @param String
	 * @param String
	 */
	public static boolean simpleOneParameterNullEmptyCheck(String parameter) {
		if (parameter == null ) {
			return true;
		}
		if(parameter.trim().isEmpty()) {			
			return true;
		}		
		return false;
	}
	
	/**
	 * Checks  String if it matches to regular expression for login.
	 * If matches returns true else returns false
	 * @param String
	 * @param String
	 */
	public static boolean loginRegularCheck(String login) {
		
		Pattern p = Pattern.compile(LOGIN_PATTERN);
		Matcher m = p.matcher(login);
		return m.matches();
		
	}
	
	/**
	 * Checks  String if it matches to regular expression for password.
	 * If matches returns true else returns false
	 * @param String
	 * @param String
	 */
	public static boolean passwordRegularCheck(String password) {
		Pattern p = Pattern.compile(PASSWORD_PATTERN);
		Matcher m = p.matcher(password);
		return m.matches();
	}
	
	/**
	 * Checks  String if it matches to regular expression for name.
	 * If matches returns true else returns false
	 * @param String
	 * @param String
	 */
	public static boolean nameRegularCheck(String name) {
		Pattern p = Pattern.compile(NAME_PATTERN, Pattern.UNICODE_CHARACTER_CLASS);
		Matcher m = p.matcher(name);
		return m.matches();
	}
	
	/**
	 * Checks  String if it matches to regular expression for surname.
	 * If matches returns true else returns false
	 * @param String
	 * @param String
	 */
	public static boolean surnameRegularCheck(String surname) {
		Pattern p = Pattern.compile(SURNAME_PATTERN, Pattern.UNICODE_CHARACTER_CLASS);
		Matcher m = p.matcher(surname);
		return m.matches();
	}
	
	/**
	 * Checks  String if it matches to regular expression for email.
	 * If matches returns true else returns false
	 * @param String
	 * @param String
	 */
	public static boolean emailRegularCheck(String email) {
		Pattern p = Pattern.compile(EMAIL_PATTERN);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static void validateLogin(HttpServletRequest request, UserBuilder builder, String login) {
		if(simpleOneParameterNullEmptyCheck(login)) {																//login check if it empty, if it matches pattern, error msg setting
			request.setAttribute(EMPTY_LOGIN_ERROR_MESSAGE, EMPTY_LOGIN_ERROR_MESSAGE);
		} else {
			if(loginRegularCheck(login)) {
				builder = builder.buildNickName(login);
			} else {
				request.setAttribute(WRONG_LOGIN_ERROR_MESSAGE, WRONG_LOGIN_ERROR_MESSAGE);
			}
		}		
	}
	
	public static void validatePassword(HttpServletRequest request, UserBuilder builder, RequestDispatcher dispatcher, String passwordFromUser) {
		if(simpleOneParameterNullEmptyCheck(passwordFromUser)) {														//password check if it is empty, if it matches pattern, if hashPassword not null, error msg setting
			request.setAttribute(EMPTY_PASSWORD_ERROR_MESSAGE, EMPTY_PASSWORD_ERROR_MESSAGE);
		}else {
			if(passwordRegularCheck(passwordFromUser)) {				
				try {
					builder = builder.buildHashFromPassword(passwordFromUser);
				} catch (PasswordClassUtilException e) {
					logger.warn("Cant make hashPassword during registration", e);
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
				}										
			} else {
				request.setAttribute(WRONG_PASSWORD_ERROR_MESSAGE, WRONG_PASSWORD_ERROR_MESSAGE);
			}
		}		
	}
	
	public static void validateName(HttpServletRequest request, UserBuilder builder, String name) {
		if(!simpleOneParameterNullEmptyCheck(name)) {																	//name check it matches pattern, error msg setting
			if(nameRegularCheck(name)) {
				builder = builder.buildName(name);
			} else {
				request.setAttribute(WRONG_NAME_ERROR_MESSAGE, WRONG_NAME_ERROR_MESSAGE);
			}
		}		
	}
	
	public static void validateSurname(HttpServletRequest request, UserBuilder builder, String surname) {
		if(!simpleOneParameterNullEmptyCheck(surname)) {																//surname check it matches pattern, error msg setting
			if(surnameRegularCheck(surname)) {
				builder = builder.buildSurname(surname);
			} else {
				request.setAttribute(WRONG_SURNAME_ERROR_MESSAGE, WRONG_SURNAME_ERROR_MESSAGE);
			}
		}	
	}
	
	public static void validateEmail(HttpServletRequest request, UserBuilder builder, String email) {
		if(!simpleOneParameterNullEmptyCheck(email)) {																	//email check it matches pattern, error msg setting
			if(emailRegularCheck(email)) {
				builder = builder.buildEMail(email);
			} else {
				request.setAttribute(WRONG_EMAIL_ERROR_MESSAGE, WRONG_EMAIL_ERROR_MESSAGE);
			}
		}	
	}

}
