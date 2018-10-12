package by.htp.accountant.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.bean.User;
import by.htp.accountant.exception.NoSuchCommandException;

public enum CommandName {
		
	GO_TO_MAIN_PAGE(),
	GO_TO_REGISTRATION_PAGE(),
	GO_TO_LOGIN_PAGE(),
	CHANGE_LOCALIZATION(0,1,2,3),
	LOGINATION(0,1,2,3),
	REGISTRATION(0,1,2,3),
	SIGN_OUT(0,1,2),
	GO_TO_PROFILE(1,2),
	GO_TO_CONTACTS_PAGE(),
	GO_TO_ABOUT_US_PAGE(),
	GO_TO_SLOGAN_PAGE(),
	GO_TO_USER_ADMINISTRATION_PAGE(1),
	GO_TO_SORRY_PAGE(),
	RESTORE_USER(0,1),
	DIACTIVATE_USER(1,2),
	CHANGE_LOGIN(1,2),
	CHANGE_PASSWORD(1,2),
	CHANGE_USER_INFO(1,2),
	MAKE_ADMIN(1),
	DELETE_USER(1),
	GO_TO_USER_ACCOUNT_PAGE(1,2),
	GO_TO_OPERATION_FORM(1,2),
	CREATE_OPERATION(1,2),
	CREATE_TYPE(1,2),
	EDIT_TYPE(1,2),
	DELETE_TYPE(1,2),
	GO_TO_USER_OPERATIONS_PAGE(1,2),
	DELETE_OPERATION(1,2),
	EDIT_OPERATION(1,2),
	GO_TO_RESOURCE_NOT_READY_PAGE();
	
	private static final Logger logger = LoggerFactory.getLogger("CommandName.class");
	
	int[] roles = null;
	
	
	private CommandName() {		
	}	
	
	private CommandName(int...roles) {	
		this.roles = roles;		
	}	
	
	public boolean containsRole(User user) {
		
		if(roles == null) return true;
		
		if(user == null && roles.length == 4) {			
			return true;
		}else if(user == null && roles.length < 4) {
			return false;
		}
		
		for(int role: roles) {
			if(role == user.getRole()) { 
				return true;
			}
		}		
		return false;	
	}
	
	public static CommandName getCommand(String potentialElement) throws NoSuchCommandException {
		try {
			return CommandName.valueOf(potentialElement);
		} catch (IllegalArgumentException e) {
			logger.info("There is no such element as " + potentialElement + " in CommandName enum.", e);
		     throw new NoSuchCommandException("There is no such element as " + potentialElement + " in CommandName enum.", e);
		}		
	}
}
