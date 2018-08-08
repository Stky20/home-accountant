package by.htp.accountant.exception;


public class BeanException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public BeanException() {
		super();		
	}
	
	public BeanException(String message) {
		super(message);		
	}

	public BeanException(Throwable exception) {
		super(exception);		
	}
	
	public BeanException(String message, Throwable exception) {
		super(message, exception);		
	}

	
	

}
