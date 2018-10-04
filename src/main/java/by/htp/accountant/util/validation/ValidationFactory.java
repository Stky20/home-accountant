package by.htp.accountant.util.validation;

import by.htp.accountant.util.validation.impl.ParameterValidatorImpl;

public class ValidationFactory {

	private static final ValidationFactory instance = new ValidationFactory();	
	private final ParameterValidatorImpl validator = new ParameterValidatorImpl();
	
	
	private ValidationFactory() {		
	}
	
	public static ValidationFactory getInstance() {
		return instance;
	}
	
	public ParameterValidatorImpl getValidator() {
		return validator;
	}
	
	
}
