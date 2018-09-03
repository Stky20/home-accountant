package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UserService;

public class Registration implements Command{
	
	private static final Logger logger = LoggerFactory.getLogger(Registration.class);	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = ServiceFactory.getInstance().getUserService();			
		
		try {
			userService.registration(request, response);
		} catch (IOException e) {
			logger.warn("IOException while making registration", e);
			throw e;
		} catch (ServletException e) {
			logger.warn("ServletException while making registration", e);
			throw e;
		}
		
	}

}
