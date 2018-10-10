package by.htp.accountant.util.validation;


import by.htp.accountant.exception.ValidationException;

public interface UtilValidator {

	boolean oneParameterNullEmptyCheck(String parameter);
	
	boolean ifSomeOfParamsNullEmptyCheck(String... params);
	
	boolean validateDate(String date) throws ValidationException;
	
	boolean validatTypeIdNumber(String operationType, String typeId, String pageNumber) throws ValidationException;
	
	
}
