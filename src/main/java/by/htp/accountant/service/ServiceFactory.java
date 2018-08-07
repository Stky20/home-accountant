package by.htp.accountant.service;

import by.htp.accountant.service.impl.UserServiceImpl;

public class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();
	
	private final UserService userService = new UserServiceImpl();
	
	private ServiceFactory() {}
	
	public static ServiceFactory getInstance() {
		return instance;
	}
	
	public UserService getUserService() {
		return userService;
	}
	

}
