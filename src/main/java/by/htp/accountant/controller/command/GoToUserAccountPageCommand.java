package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.controller.Command;

public class GoToUserAccountPageCommand implements Command{
	
	private static final Logger logger = Logger.getLogger(GoToUserAccountPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			logger.info("Exception in controller command GoToUserAccountPageCommand while doing forward", e);
			throw e;
		} catch (IOException e) {
			logger.info("Exception in controller command GoToUserAccountPageCommand while doing forward", e);
			throw e;
		}	
		
	}

}
