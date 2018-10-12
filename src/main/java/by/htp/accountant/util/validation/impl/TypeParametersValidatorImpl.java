package by.htp.accountant.util.validation.impl;

import static by.htp.accountant.util.validation.ValidationErrorMessage.*;

import java.util.ArrayList;
import java.util.List;

import by.htp.accountant.exception.ValidationException;
import by.htp.accountant.util.validation.TypeParametersValidator;

public class TypeParametersValidatorImpl implements TypeParametersValidator{
	
	public final static String OPERATION_TYPE_PATTERN = "[\\wА-Яа-я-\\s]{1,20}";
	public final static String TYPES_ROLE_PATTERN = "[1234]{1}";
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
	public boolean validateTypeRole(String role) throws ValidationException {
		if(oneParameterNullEmptyCheck(role)) {
			throw new ValidationException("Somehow OperationType role came empty for validation");
		} else {
			if(!role.matches(TYPES_ROLE_PATTERN)) {
				throw new ValidationException("Somehow OperationType role wich came for validation don`t matches to the pattern");
			}
		}
		return true;
	}
	
	@Override
	public boolean validateTypeId(String id) throws ValidationException {
		if(oneParameterNullEmptyCheck(id)) {
			throw new ValidationException("Somehow OperationID came empty for validation");
		} else {
			if(!id.matches(TYPES_ID_PATTERN)) {
				throw new ValidationException("Somehow OperationID wich came for validation don`t matches to the pattern");
			}
		}
		return true;
	}

	@Override
	public String validateType(String operationType) {
		
		if(oneParameterNullEmptyCheck(operationType)) {
			return EMPTY_OPERATION_TYPE_ERROR_MSG;
		} else {
			if(!operationType.matches(OPERATION_TYPE_PATTERN)) {
				return WRONG_OPERATION_TYPE_ERROR_MSG;
			}
		}
		return null;
	}

	@Override
	public List<String> validateRoleAndTypeParams(String role, String operationType) throws ValidationException {
		List<String> errorMsgs = new ArrayList<String>();
		String operationTypeErrprMsg = validateType(operationType);
		
		if(operationTypeErrprMsg != null) {
			errorMsgs.add(operationTypeErrprMsg);
		}
		
		validateTypeRole(role);
		
		return errorMsgs;
	}

	@Override
	public List<String> validateIDAndTypeParams(String idInString, String operationType) throws ValidationException {
		List<String> errorMsgs = new ArrayList<String>();
		String operationTypeErrprMsg = validateType(operationType);
		
		if(operationTypeErrprMsg != null) {
			errorMsgs.add(operationTypeErrprMsg);
		}
		
		validateTypeId(idInString);
		
		return errorMsgs;
	}

	

}
