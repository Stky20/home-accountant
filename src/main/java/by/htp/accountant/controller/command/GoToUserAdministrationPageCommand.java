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

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
//		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.USER_ADMINISTRATION_PAGE);
//		
//		
//		dispatcher.forward(request, response);
		UserService userService = ServiceFactory.getInstance().getUserService();			
		
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
