package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.controller.Command;

public class GoToContactsPageCommand implements Command{
	
	private static final Logger logger = LoggerFactory.getLogger(GoToContactsPageCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.CONTACTS_PAGE);
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			logger.info("Exception in controller command GoToLoginPageCommand() while doing forward", e);
			throw e;
		} catch (IOException e) {
			logger.info("Exception in controller command GoToLoginPageCommand() while doing forward", e);
			throw e;
		}	
		
	}

}
