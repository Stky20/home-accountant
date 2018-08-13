package by.htp.accountant.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	
	public final static String LOGIN_PATTERN = "[\\w-_]{1,25}";	
	public final static String PASSWORD_PATTERN = "[\\w]{6,10}";
	public final static String NAME_PATTERN = "[\\w-&&[^\\d]]{1,20}";
	public final static String SURNAME_PATTERN = "[\\w-&&[^\\d]]{1,25}";
	public final static String EMAIL_PATTERN = "^[\\w\\+]+@" + "[A-Za-z0-9]+(\\.[A-Za-z]{2,7})$";
	
	public final static String LOGIN_ERROR_MSG = "wrong login";
	public final static String PASSWORD_ERROR_MSG = "wromg password";
	public final static String NAME_ERROR_MSG = "wrong name";
	public final static String SURNAME_ERROR_MSG = "wrong surname";
	public final static String EMAIL_ERROR_MSG = "wrong email";	
	
	/**
	 * Checks two Strings if some of it is null or empty returns true
	 * else returns false
	 * @param String
	 * @param String
	 */
	public static boolean simpleNullEmptyCheck(String login, String password) {
		
		if(login.trim().isEmpty() || password.trim().isEmpty() || login == null || password == null) {			
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

}
