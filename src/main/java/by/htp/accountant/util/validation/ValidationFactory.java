package by.htp.accountant.util.validation;

import by.htp.accountant.util.validation.impl.OperationParameterValidatorImpl;
import by.htp.accountant.util.validation.impl.UserParameterValidatorImpl;

public class ValidationFactory {

	private static final ValidationFactory instance = new ValidationFactory();	
	
	private final UserParameterValidator userValidator = new UserParameterValidatorImpl();
	private final OperationParameterValidator operationValidator = new OperationParameterValidatorImpl();
	
	
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
	
	
}
