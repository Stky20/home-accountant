package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UserService;

public class RestoreUserCommand implements Command{

	private static final Logger logger = Logger.getLogger(RestoreUserCommand.class);
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserService userService = ServiceFactory.getInstance().getUserService();			
		
		try {
			userService.restoreUser(request, response);
		} catch (IOException e) {
			logger.warn("IOException while fulfill restore command", e);
			throw e;
		} catch (ServletException e) {
			logger.warn("ServletException while fulfill restore command", e);
			throw e;
		}		
		
	}

}
