package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.controller.Command;

public class SignOut implements Command {
	
	private final static String USER_PARAMETER = "user";
	
	private static final Logger logger = LoggerFactory.getLogger(SignOut.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		request.getSession().removeAttribute(USER_PARAMETER);
		
		try {
			response.sendRedirect(JSPPath.GO_TO_MAIN_PAGE);
		} catch (IOException e) {
			logger.warn("IOException in SignOut command", e);
			throw e;
		}
		
	}

}
