package by.htp.accountant.bean;

import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;


public class RequestChecker {
	
	private final static String PARAMETR_LOCALIZATION = "localization";
	private static String parametrValue = null;
	
	private RequestChecker() {
		
	}
	
	public static boolean isItForLocalization(ServletRequest request) {   							//проверяем этот запрос - не на смену локализации ли он
				
		parametrValue = ((HttpServletRequest)request).getParameter(PARAMETR_LOCALIZATION);
		
		if(parametrValue != null) {
			return true;		
		}else {
			return false;
		}
	}
		
	
	public static String makeUriWithParams(ServletRequest request) {               // делаем строку из ури и параметров
		
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
		
		System.out.println(userRequest);
		
		return userRequest;		
	}
	
}
