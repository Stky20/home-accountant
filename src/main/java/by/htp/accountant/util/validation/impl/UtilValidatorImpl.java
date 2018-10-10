package by.htp.accountant.util.validation.impl;


import by.htp.accountant.exception.ValidationException;
import by.htp.accountant.util.validation.UtilValidator;

public class UtilValidatorImpl implements UtilValidator{
	
	public final static String DATE_PATTERN = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
	public final static String OPERATION_TYPE_PATTERN = "[\\wА-Яа-я-\\s]{1,20}";
	public final static String TYPES_ID_PATTERN = "[0-9]+";

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
	public boolean validateDate(String date) throws ValidationException {
		if(oneParameterNullEmptyCheck(date)) {
			return false;
		} else {
			if(!date.matches(DATE_PATTERN)) {
				throw new ValidationException("Date in String don`t matches to the pattern");
			}
		}
		return true;
	}

	@Override
	public boolean validatTypeIdNumber(String operationType, String typeId, String pageNumber)
			throws ValidationException {
		
		if(oneParameterNullEmptyCheck(operationType)|| oneParameterNullEmptyCheck(typeId)) {
			throw new ValidationException("OperationType or typeId came null or empty for validation.");
		} 
		if(!operationType.matches(OPERATION_TYPE_PATTERN) || !typeId.matches(TYPES_ID_PATTERN)){			
			throw new ValidationException("OperationType or typeId don`t matches to the pattern");			
		} 
		
		if(!oneParameterNullEmptyCheck(pageNumber)) {
			if(!pageNumber.matches(TYPES_ID_PATTERN)) {
				throw new ValidationException("pageNumber don`t matches to the pattern");
			}
		} else if (oneParameterNullEmptyCheck(pageNumber)) {
			return false;
		}
		return true;
	}

}
