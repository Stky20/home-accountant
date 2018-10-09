package by.htp.accountant.exception;

public class ValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();		
	}

	public ValidationException(String msg, Throwable e) {
		super(msg);		
	}

	public ValidationException(String msg) {
		super(msg);		
	}

	public ValidationException(Throwable e) {
		super(e);		
	}
	
	

}
