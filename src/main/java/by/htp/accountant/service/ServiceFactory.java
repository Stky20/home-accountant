package by.htp.accountant.service;

import by.htp.accountant.service.impl.UserServiceImpl;
import by.htp.accountant.service.impl.UtilServiceImpl;

public class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();
	
	private final UserService userService = new UserServiceImpl();
	private final UtilService utilService = new UtilServiceImpl();
	
	private ServiceFactory() {		
	}
	
	public static ServiceFactory getInstance() {
		return instance;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public UtilService getUtilService() {
		return utilService;
	}
	

}
