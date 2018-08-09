package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.bean.User;
import by.htp.accountant.controller.Command;
import by.htp.accountant.exception.UserServiceException;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UserService;


public class Logination implements Command{	
	
	private static final Logger logger = Logger.getLogger(Logination.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = ServiceFactory.getInstance().getUserService();		
			
		User user = null;
		
		try {
			user = userService.logination(request, response);
		} catch (UserServiceException e1) {
			logger.warn("ServiceException while making logination", e1);
			request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE).forward(request, response);
		}
			
		if(user !=null) {
			
			request.getSession(true).setAttribute("user", user);
			response.sendRedirect(RedirectPath.GO_TO_MAIN_PAGE);
			
		}else {
			
			try {
				request.getRequestDispatcher(JSPPath.LOGIN_PAGE).forward(request, response);
			} catch (ServletException e) {	
				logger.warn("Can`t do forward during loginstion", e);
				throw e;
			} catch (IOException e) {	
				logger.warn("Can`t do forward during loginstion", e);
				throw e;
			}	
			
		}			
		
	}

}
