package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UserService;

public class Registration implements Command{
	
	private static final Logger logger = Logger.getLogger(Registration.class);	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = ServiceFactory.getInstance().getUserService();			
		
		userService.registration(request, response);
			
		logger.warn("ServiceException while making logination");
	}

}
