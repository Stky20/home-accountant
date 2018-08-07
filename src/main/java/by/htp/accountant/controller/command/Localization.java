package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.controller.Command;


public class Localization implements Command{
	
	private static final Logger logger = Logger.getLogger(Localization.class);
	
	private static final String LOCALIZATION_PARAM_FROM_REQUEST = "localization";
	
	private static final String PREVIOS_PAGE_SESSION_PARAM ="previousPage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String local = request.getParameter(LOCALIZATION_PARAM_FROM_REQUEST);
		
		request.getSession(true).setAttribute("local", local);	
		
		String pageToLocale = (String)request.getSession().getAttribute(PREVIOS_PAGE_SESSION_PARAM);
		
		try {
			response.sendRedirect(pageToLocale);
		} catch (IOException e) {
			logger.warn("sendRedirect() method of response in Localization command, threw IOException", e);
			request.getRequestDispatcher(JSPPath.MAIN_PAGE).forward(request, response);
		}
		
		
	}

}
