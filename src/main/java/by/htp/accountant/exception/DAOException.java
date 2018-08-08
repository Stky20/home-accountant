package by.htp.accountant.exception;

public class DAOException extends Exception{	

	private static final long serialVersionUID = 1L;
	
	public DAOException() {
		super();		
	}
	
	public DAOException(String message) {
		super(message);		
	}

	public DAOException(Throwable exception) {
		super(exception);		
	}
	
	public DAOException(String message, Throwable exception) {
		super(message, exception);		
	}

}
