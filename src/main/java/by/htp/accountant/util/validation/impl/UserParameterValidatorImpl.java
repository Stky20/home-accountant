package by.htp.accountant.util.validation.impl;

import static by.htp.accountant.util.validation.ValidationErrorMessage.*;

import java.util.ArrayList;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import by.htp.accountant.util.validation.UserParameterValidator;

public class UserParameterValidatorImpl implements UserParameterValidator{
	
	//private static final Logger logger = LoggerFactory.getLogger(ParameterValidatorImpl.class);
	
	public final static String LOGIN_PATTERN = "[\\wА-Яа-я-_]{1,25}";	
	public final static String PASSWORD_PATTERN = "[\\wА-Яа-я]{6,10}";
	public final static String NAME_PATTERN = "[\\wА-Яа-я-&&[^\\d]]{1,20}";
	public final static String SURNAME_PATTERN = "[\\wА-Яа-я-&&[^\\d]]{1,25}";
	public final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,7})$";	

	@Override
	public boolean oneParameterNullEmptyCheck(String parameter) {
		if (parameter == null ) {
			return true;
		}							
		return parameter.trim().isEmpty();	
	}

	@Override
	public boolean ifSomeOfParamsNullEmptyCheck(String... params) {
		for(int i = 0; i < params.length; i++) {
			if(params[i] == null) return true;
			if(params[i].trim().isEmpty()) return true;
		}
		
		return false;
	}

	@Override
	public boolean ifAllParamsNullEmptyCheck(String... params) {
		for(int i = 0; i < params.length; i++) {
			if(params[i] != null) {
				if(!params[i].trim().isEmpty()) return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean loginPatternCheck(String login) {
		if(login != null) {
			return login.matches(LOGIN_PATTERN);
		}
		return false;
	}

	@Override
	public boolean passwordPatternCheck(String password) {
		if(password != null) {
			return password.matches(PASSWORD_PATTERN);
		}
		return false;
	}

	@Override
	public boolean namePatternCheck(String name) {
		if(name != null) {
			return name.matches(NAME_PATTERN);
		}
		return false;
	}

	@Override
	public boolean surnamePatternCheck(String surname) {
		if(surname != null) {
			return surname.matches(SURNAME_PATTERN);
		}
		return false;
	}

	@Override
	public boolean emailPatternCheck(String email) {
		if(email != null) {
			return email.matches(EMAIL_PATTERN);
		}
		return false;
	}

	@Override
	public List<String> validateLogin(String login) {
		List<String> errorMessages = new ArrayList<String>();
		
		if(oneParameterNullEmptyCheck(login)) {
			errorMessages.add(EMPTY_LOGIN_ERROR_MESSAGE);
		} else {
			if(!loginPatternCheck(login)) {
				errorMessages.add(WRONG_LOGIN_ERROR_MESSAGE);
			}
		}
		return errorMessages;
	}

	@Override
	public List<String> validatePassword(String password) {
		List<String> errorMessages = new ArrayList<String>();
		
		if(oneParameterNullEmptyCheck(password)) {
			errorMessages.add(EMPTY_PASSWORD_ERROR_MESSAGE);
		} else {
			if(!loginPatternCheck(password)) {
				errorMessages.add(WRONG_PASSWORD_ERROR_MESSAGE);
			}
		}
		return errorMessages;
	}

	@Override
	public List<String> validateNewPassword(String oldPassword, String newPassword, String newPasswordAgain) {
		
		List<String> errorMessages = new ArrayList<String>();
		
		if(ifSomeOfParamsNullEmptyCheck(oldPassword, newPassword, newPasswordAgain)) {
			errorMessages.add(EMPTY_PASSWORD_ERROR_MESSAGE);
			return errorMessages;
		}
		
		if(!passwordPatternCheck(newPassword)) {
			errorMessages.add(PASSWORD_MATCHER_ERROR_MESSAGE);
		}
		if(!newPassword.equals(newPasswordAgain)) {
			errorMessages.add(DIFFERENT_NEW_PASSWORDS_ERROR_MESSAGE);
		}
		
		return errorMessages;
	}

	@Override
	public List<String> validateName(String name) {
		List<String> errorMessages = new ArrayList<String>();
		
		if(!oneParameterNullEmptyCheck(name)) {
			if(!namePatternCheck(name)) {
				errorMessages.add(WRONG_NAME_ERROR_MESSAGE);
			}
		}
		return errorMessages;
	}

	@Override
	public List<String> validateSurname(String surname) {
		List<String> errorMessages = new ArrayList<String>();
		
		if(!oneParameterNullEmptyCheck(surname)) {
			if(!surnamePatternCheck(surname)) {
				errorMessages.add(WRONG_SURNAME_ERROR_MESSAGE);
			}
		}
		return errorMessages;
	}

	@Override
	public List<String> validateEmail(String email) {
		List<String> errorMessages = new ArrayList<String>();
		
		if(!oneParameterNullEmptyCheck(email)) {
			if(!emailPatternCheck(email)) {
				errorMessages.add(WRONG_EMAIL_ERROR_MESSAGE);
			}
		}
		return errorMessages;
	}

	@Override
	public List<String> validateNameSurnameEmail(String name, String surname, String email) {
		List<String> errorMessages = new ArrayList<String>();
		
		if(ifAllParamsNullEmptyCheck(name, surname,email)) {
			errorMessages.add(NO_CHANGES_MSG);
		}
		
		errorMessages.addAll(validateName(name));
		errorMessages.addAll(validateSurname(surname));
		errorMessages.addAll(validateEmail(email));
		
		return errorMessages;
	}
	
	@Override
	public List<String> validateNameSurnameEmailForRegistration(String name, String surname, String email) {
		List<String> errorMessages = new ArrayList<String>();
		
		if(!oneParameterNullEmptyCheck(name)) {
			errorMessages.addAll(validateName(name));
		}
		if(!oneParameterNullEmptyCheck(surname)) {
			errorMessages.addAll(validateSurname(surname));
		}
		if(!oneParameterNullEmptyCheck(email)) {
			errorMessages.addAll(validateEmail(email));
		}
		
		return errorMessages;
	}

}
