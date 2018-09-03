package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.controller.Command;


public class Localization implements Command{
	
	private static final Logger logger = LoggerFactory.getLogger(Localization.class);
	
	private static final String LOCALIZATION_PARAM_FROM_REQUEST = "localization";
	
	private static final String PREVIOS_PAGE_SESSION_PARAM ="previousPage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String local = request.getParameter(LOCALIZATION_PARAM_FROM_REQUEST);
		
		request.getSession(true).setAttribute("local", local);	
		
		String pageToLocale = (String)request.getSession().getAttribute(PREVIOS_PAGE_SESSION_PARAM);
		
		try {
			response.sendRedirect(pageToLocale);
		} catch (IOException e) {
			logger.warn("sendRedirect() method of response in Localization command, threw IOException", e);
			try {
				request.getRequestDispatcher(JSPPath.MAIN_PAGE).forward(request, response);
			} catch (ServletException e1) {
				logger.warn("sendRedirect() method of response in Localization command, threw IOException", e);
				throw e1;
			} catch (IOException e1) {
				logger.warn("sendRedirect() method of response in Localization command, threw IOException", e);
				throw e1;
			}
		}
		
		
	}

}
