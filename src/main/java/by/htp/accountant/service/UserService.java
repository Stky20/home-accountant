package by.htp.accountant.service;

import javax.servlet.http.HttpServletRequest;

import by.htp.accountant.bean.User;
import by.htp.accountant.exception.UserServiceException;


public interface UserService {	
	
	
	/**
	 * Method makes logination
	 * @param HttpServletRequest 
	 * @return User
	 * @throws UserServiceException 
	 */
	public User logination(HttpServletRequest request) throws UserServiceException;

}
