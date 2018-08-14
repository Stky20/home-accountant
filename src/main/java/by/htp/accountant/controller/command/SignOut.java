package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import by.htp.accountant.controller.Command;

public class SignOut implements Command {
	
	private final static String USER_PARAMETER = "user";
	
	private static final Logger logger = Logger.getLogger(SignOut.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.getSession().removeAttribute(USER_PARAMETER);
		
		try {
			response.sendRedirect(RedirectPath.GO_TO_MAIN_PAGE);
		} catch (IOException e) {
			logger.warn("IOException in SignOut command", e);
			throw e;
		}
		
	}

}
