package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.bean.User;
import by.htp.accountant.controller.Command;
import by.htp.accountant.exception.UserServiceException;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UserService;

public class Registration implements Command{
	
	private static final Logger logger = Logger.getLogger(Registration.class);	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserService userService = ServiceFactory.getInstance().getUserService();		
		RequestDispatcher dispatcher = null;	
		User user = null;
		
		try {
			user = userService.registration(request);
		} catch (UserServiceException e1) {
			logger.warn("ServiceException while making logination", e1);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
			
		if(user !=null) {			
			request.getSession(true).setAttribute("user", user);
			
				try {
					response.sendRedirect(RedirectPath.GO_TO_MAIN_PAGE);
				} catch (IOException e) {
					logger.warn("Can`t do sendRedirect during loginstion", e);	
					throw e;
				}
			
		}else {			
			dispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION_PAGE);		
		
			try {
				dispatcher.forward(request, response);
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
