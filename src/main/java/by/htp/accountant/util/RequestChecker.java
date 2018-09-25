package by.htp.accountant.util;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


public class RequestChecker {
	
	private final static String PARAMETR_LOCALIZATION = "localization";
	
	private RequestChecker() {
		
	}
	
	public static boolean isItForLocalization(ServletRequest request) {   							//checking request if it to chenge locale
				
		String parametrValue = ((HttpServletRequest)request).getParameter(PARAMETR_LOCALIZATION);
		
		if(parametrValue != null) {
			return true;		
		}else {
			return false;
		}
	}
		
	
	public static String makeUriWithParams(ServletRequest request) {               					// making String  uri?params
		
		String userRequest;		
		String uri = ((HttpServletRequest)request).getRequestURI();
		Map<String, String[]> parameters = ((HttpServletRequest)request).getParameterMap();
		
		userRequest = uri + "?";
				
		for (Map.Entry<String, String[]> entry: parameters.entrySet()) { 
			String key = entry.getKey(); 
			String[] value = entry.getValue(); 
			userRequest = userRequest + key + "=" + value[0] + "&";
		} 
		
		userRequest = userRequest.substring(0, (userRequest.length() - 1));		
		
		return userRequest;		
	}
	
}
