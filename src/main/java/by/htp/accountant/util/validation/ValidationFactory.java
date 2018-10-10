package by.htp.accountant.util.validation;

import by.htp.accountant.util.validation.impl.OperationParameterValidatorImpl;
import by.htp.accountant.util.validation.impl.TypeParametersValidatorImpl;
import by.htp.accountant.util.validation.impl.UserParameterValidatorImpl;
import by.htp.accountant.util.validation.impl.UtilValidatorImpl;

public class ValidationFactory {

	private static final ValidationFactory instance = new ValidationFactory();	
	
	private final UserParameterValidator userValidator = new UserParameterValidatorImpl();
	private final OperationParameterValidator operationValidator = new OperationParameterValidatorImpl();
	private final TypeParametersValidator typeValidator = new TypeParametersValidatorImpl();
	private final UtilValidator utilValidator = new UtilValidatorImpl();
	
	
	private ValidationFactory() {		
	}
	
	public static ValidationFactory getInstance() {
		return instance;
	}
	
	public UserParameterValidator getUserValidator() {
		return userValidator;
	}
	
	public OperationParameterValidator getOperationValidator() {
		return operationValidator;
	}
	
	public TypeParametersValidator getTypeValidator() {
		return typeValidator;
	}
	
	public UtilValidator getUtilValidator() {
		return utilValidator;
	}
}
