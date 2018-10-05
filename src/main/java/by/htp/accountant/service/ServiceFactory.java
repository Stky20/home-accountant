package by.htp.accountant.service;

import by.htp.accountant.service.impl.OperationServiceImpl;
import by.htp.accountant.service.impl.OperationTypeServiceImpl;
import by.htp.accountant.service.impl.UserServiceImplTwo;
import by.htp.accountant.service.impl.UtilServiceImpl;

public class ServiceFactory {

	private static final ServiceFactory instance = new ServiceFactory();
	
	private final UserService userService = new UserServiceImplTwo();
	private final UtilService utilService = new UtilServiceImpl();
	private final OperationService operationService = new OperationServiceImpl();
	private final OperationTypeService operationTypeService = new OperationTypeServiceImpl();
	
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
	
	public OperationService getOperationService() {
		return operationService;
	}	

	public OperationTypeService getOperationTypeService() {
		return operationTypeService;
	}	
}
