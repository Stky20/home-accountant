package by.htp.accountant.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class RequestResponseEncodingFilter implements Filter {

	private String requestEncoding;
	private String responseEncoding;
   
    public RequestResponseEncodingFilter() {
        
    }

	
	public void destroy() {		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(requestEncoding);
		response.setContentType(responseEncoding);
		
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		this.requestEncoding = fConfig.getInitParameter("encoding_for_request");
		this.responseEncoding = fConfig.getInitParameter("encoding_for_response");
	}

}
