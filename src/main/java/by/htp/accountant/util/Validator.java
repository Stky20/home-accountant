package by.htp.accountant.util;





public class Validator {	
	
	HashPasswordMaker passwordMaker = HashPasswordMaker.getInstance();
	
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
	public static final String DIFFERENT_NEW_PASSWORDS_ERROR_MESSAGE = "differentNewPasswordErrorMsg";
	public static final String PASSWORD_MATCHER_ERROR_MESSAGE = "passwordDoNotMatches";
	public static final String NO_CHANGES_MSG = "noChangesMsg";
	
	public static final String PASSWORD_PARAM = "password";
	public static final String NEW_PASSWORD_PARAM = "new_password";
	public static final String NEW_PASSWORD_AGAIN_PARAM = "new_password_again";	
	
		
	public static boolean simpleNullEmptyParamsCheck(String... params) {  
		
		for(int i = 0; i < params.length; i++) {
			if(params[i] == null) return true;
			if(params[i].trim().isEmpty()) return true;
		}
		
		return false;
	}
	
	public static boolean ifAllParamsNullOrEmpty(String... params) {  
		
		for(int i = 0; i < params.length; i++) {
			if(params[i] != null) {
				if(!params[i].trim().isEmpty()) return false;
			}
		}
		
		return true;
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
		return parameter.trim().isEmpty();	
	}
}
