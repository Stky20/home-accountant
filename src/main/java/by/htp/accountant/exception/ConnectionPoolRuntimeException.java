package by.htp.accountant.exception;

public class ConnectionPoolRuntimeException extends RuntimeException{	
	
	private static final long serialVersionUID = 1L;
	
	public ConnectionPoolRuntimeException() {
		super();		
	}
	
	public ConnectionPoolRuntimeException(String message) {
		super(message);		
	}

	public ConnectionPoolRuntimeException(Throwable exception) {
		super(exception);		
	}
	
	public ConnectionPoolRuntimeException(String message, Throwable exception) {
		super(message, exception);		
	}

}
