package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.controller.Command;

public class GoToProfile implements Command{
	
	private static final Logger logger = Logger.getLogger(GoToProfile.class);
	private static final String MODAL_ATTRIBUTE = "modal";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);
		
		String modalAttribute = request.getParameter(MODAL_ATTRIBUTE);
		
		if(modalAttribute != null) {
			request.setAttribute(MODAL_ATTRIBUTE, modalAttribute);
		}
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			logger.info("Exception in controller command GoToProfile() while doing forward", e);
			throw e;
		} catch (IOException e) {
			logger.info("Exception in controller command GoToProfile() while doing forward", e);
			throw e;
		}		
		
	}

}
