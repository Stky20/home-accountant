package by.htp.accountant.exception;

public class NoSuchCommandException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchCommandException() {
		super();		
	}

	public NoSuchCommandException(String arg0, Throwable arg1) {
		super(arg0, arg1);		
	}

	public NoSuchCommandException(String arg0) {
		super(arg0);		
	}

	public NoSuchCommandException(Throwable arg0) {
		super(arg0);		
	}
	
	

}
