package by.htp.accountant.util.validation;

import java.util.List;

import by.htp.accountant.exception.ValidationException;

public interface OperationParameterValidator {

	/**
	 * Checks any String if it is null or empty method returns true
	 * else returns false
	 * @param parameter - String
	 */
	boolean oneParameterNullEmptyCheck(String parameter);
	
	
	/**
	 * Checks any amount of Strings if any of it is null or empty method returns true
	 * else if all params not null and not empty it returns false.
	 * @param params - String... params
	 */
	boolean ifSomeOfParamsNullEmptyCheck(String... params);	
	
	String validateOperationRemark(String remark);
	
	String validateOperationDate(String date);
	
	String validateOperationAmount(String amount);
	
	boolean validateTypeRole(String typeRoleInString) throws ValidationException;
	
	boolean validateOperationType(String operationType) throws ValidationException;
	
	boolean validateTypeId(String typeId) throws ValidationException;
	
	List<String> validateOperationParams( String remark, String date, String amount);	
	
}
