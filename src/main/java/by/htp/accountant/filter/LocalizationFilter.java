package by.htp.accountant.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import by.htp.accountant.bean.RequestChecker;

public class LocalizationFilter implements Filter{
	
	public final static String ATTRIBUTE = "previousPage";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String uriWithParams;		
		
		if(!RequestChecker.isItForLocalization(request)) {
		
			uriWithParams = RequestChecker.makeUriWithParams(request);
			
			((HttpServletRequest)request).getSession().setAttribute(ATTRIBUTE, uriWithParams);	
			
		}
		
		chain.doFilter(request, response);
	}

}
