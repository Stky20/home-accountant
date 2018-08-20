package by.htp.accountant.exception;


public class UtilException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public UtilException() {
		super();		
	}
	
	public UtilException(String message) {
		super(message);		
	}

	public UtilException(Throwable exception) {
		super(exception);		
	}
	
	public UtilException(String message, Throwable exception) {
		super(message, exception);		
	}

	
	

}
