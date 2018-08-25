package by.htp.accountant.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import by.htp.accountant.bean.User;
import by.htp.accountant.controller.CommandName;
import by.htp.accountant.controller.command.JSPPath;

/**
 * Servlet Filter implementation class RoleCheckFilter
 */
public class RoleCheckFilter implements Filter {

	public final static String ATTRIBUTE_USER = "user";
	private static final String COMMAND_PARAM_NAME = "command";
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		User user = null;
		
		String commandFromRequest = ((HttpServletRequest)request).getParameter(COMMAND_PARAM_NAME).toUpperCase();
		
		Object object = ((HttpServletRequest)request).getSession().getAttribute(ATTRIBUTE_USER);
		
		if(object != null) {
			user = (User) object;
		}
		
		CommandName command = CommandName.valueOf(commandFromRequest);
		
		if(!command.containsRole(user)) {
			((HttpServletRequest)request).getRequestDispatcher(JSPPath.SORRY_PAGE).forward(request, response);
		}
		
		chain.doFilter(request, response);
	}

	
}
