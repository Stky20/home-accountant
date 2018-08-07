package by.htp.accountant.exception;

public class SQLUserDAOException extends DAOException{
	
	private static final long serialVersionUID = 1L;

	public SQLUserDAOException() {
		super();		
	}

	public SQLUserDAOException(String message, Throwable exception) {
		super(message, exception);
	}

	public SQLUserDAOException(String message) {
		super(message);
	}

	public SQLUserDAOException(Throwable exception) {
		super(exception);
	}	
	
}
