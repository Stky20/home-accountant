package by.htp.accountant.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.accountant.bean.User;
import by.htp.accountant.controller.CommandName;
import by.htp.accountant.controller.command.JSPPath;

/**
 * Servlet Filter implementation class RoleCheckFilter
 */
public class RoleCheckFilter implements Filter {

	public final static String ATTRIBUTE_USER = "user";
	private static final String COMMAND_PARAM_NAME = "command";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		User user = null;
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		String commandFromRequest = httpRequest.getParameter(COMMAND_PARAM_NAME).toUpperCase();
		
		Object object = httpRequest.getSession().getAttribute(ATTRIBUTE_USER);
		
		if(object != null) {
			user = (User) object;
		}
		
		CommandName command = CommandName.getCommand(commandFromRequest);
		
		if(!command.containsRole(user)) {			
			((HttpServletResponse)response).sendRedirect(JSPPath.GO_TO_SORRY_PAGE);
			return;			
		}
		
		chain.doFilter(request, response);
	}

	
}
