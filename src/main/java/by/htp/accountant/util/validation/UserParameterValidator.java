package by.htp.accountant.util.validation;

import java.util.List;

public interface UserParameterValidator {
	
	
	/**
	 * Checks any String if it is null or empty method returns true
	 * else returns false
	 * @param parameter - String
	 */
	public boolean oneParameterNullEmptyCheck(String parameter);
	
	
	/**
	 * Checks any amount of Strings if any of it is null or empty method returns true
	 * else if all params not null and not empty it returns false.
	 * @param params - String... params
	 */
	public boolean ifSomeOfParamsNullEmptyCheck(String... params);
	
	
	/**
	 * Checks any amount of Strings if all of it are null or empty method returns true
	 * else if at least one of it is not null and not empty method returns false
	 * @param params - String... params
	 */
	public boolean ifAllParamsNullEmptyCheck(String... params);	
	
	
	/**
	 * Checks if parameter matches to login regular pattern
	 * @param login - String
	 * @return 
	 */
	public boolean loginPatternCheck(String login);
	
	/**
	 * Checks if parameter matches to password regular pattern
	 * @param password
	 * @return
	 */
	public boolean passwordPatternCheck(String password);
	
	/**
	 * Checks if parameter matches to name regular pattern
	 * @param name
	 * @return
	 */
	public boolean namePatternCheck(String name);
	
	/**
	 * Checks if parameter matches to surname regular pattern
	 * @param surname
	 * @return
	 */
	public boolean surnamePatternCheck(String surname);
	
	/**
	 * Checks if parameter matches to email regular pattern
	 * @param email
	 * @return
	 */
	public boolean emailPatternCheck(String email);
	
	
	/**
	 * Checks incoming parameter for compliance to login and returns List<String> with errorMessages.
	 * If incoming parameter meets the requirements of login method returns empty List<String>.
	 * @param login - String
	 * @return errorMessages - List<String>
	 */	 
	public List<String> validateLogin(String login);
	
	/**
	 * 
	 * @return
	 */
	public List<String> validatePassword(String password);
	
	/**
	 * 
	 * @return
	 */
	public List<String> validateNewPassword(String oldPassword, String newPassword, String newPasswordAgain);
	
	/**
	 * 
	 * @return
	 */
	public List<String> validateName(String name);
	
	/**
	 * 
	 * @return
	 */
	public List<String> validateSurname(String surname);
	
	/**
	 * 
	 * @return
	 */
	public List<String> validateEmail(String email);
	
	/**
	 * 
	 * @return
	 */
	public List<String> validateNameSurnameEmail(String name, String surname, String email);
	
	/**
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @return
	 */
	public List<String> validateNameSurnameEmailForRegistration(String name, String surname, String email);	
	
	
}
