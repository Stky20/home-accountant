package by.htp.accountant.util.validation;

import java.util.List;

public interface OperationParameterValidator {

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
	
	public String validateOperationRemark(String remark);
	
	public String validateOperationDate(String date);
	
	public String validateOperationAmount(String amount);
	
	public List<String> validateOperationParams( String remark, String date, String amount);
	
}
