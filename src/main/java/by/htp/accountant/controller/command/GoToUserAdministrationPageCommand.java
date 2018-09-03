package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UserService;

public class GoToUserAdministrationPageCommand implements Command{
	
	private static final Logger logger = Logger.getLogger(GoToUserAdministrationPageCommand.class);
	
	private static final String MODAL_ATTRIBUTE = "modal";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = ServiceFactory.getInstance().getUserService();		
		
		String modalAttribute = request.getParameter(MODAL_ATTRIBUTE);
		
		if(modalAttribute != null) {
			request.setAttribute(MODAL_ATTRIBUTE, modalAttribute);
		}
		
		try {
			userService.showUsers(request, response);
		} catch (IOException e) {
			logger.warn("ServiceException while making logination", e);
			throw e;
		} catch (ServletException e) {
			logger.warn("ServiceException while making logination", e);
			throw e;
		}
	
		
	}

}
