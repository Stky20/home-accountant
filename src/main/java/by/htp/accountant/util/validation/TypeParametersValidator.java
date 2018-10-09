package by.htp.accountant.util.validation;

import java.util.List;

import by.htp.accountant.exception.ValidationException;

public interface TypeParametersValidator {

	public boolean oneParameterNullEmptyCheck(String parameter);
	
	public boolean ifSomeOfParamsNullEmptyCheck(String... params);	
	
	public boolean validateTypeRole(String role) throws ValidationException;
	
	public boolean validateTypeId(String id) throws ValidationException;
	
	public String validateType(String operationType);
	
	public List<String> validateRoleAndTypeParams(String role, String operationType) throws ValidationException;
	
	public List<String> validateIDAndTypeParams(String id, String operationType) throws ValidationException;
	
}
