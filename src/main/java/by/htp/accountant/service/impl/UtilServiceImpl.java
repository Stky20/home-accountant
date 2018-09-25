package by.htp.accountant.service.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.controller.command.JSPPath;
import by.htp.accountant.service.UtilService;

public class UtilServiceImpl implements UtilService {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilServiceImpl.class);
	
	private static final String LOCALIZATION_PARAM_FROM_REQUEST = "localization";	
	private static final String LOCALIZATION_SESSION_ATTRIBUTE = "local";	
	private static final String PREVIOS_PAGE_SESSION_PARAM ="previousPage";
	private static final String MODAL_ATTRIBUTE = "modal";
	private final static String USER_PARAMETER = "user";
	

	@Override
	public void changeLocalization(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {		
		
		String local = request.getParameter(LOCALIZATION_PARAM_FROM_REQUEST);		
		request.getSession(true).setAttribute(LOCALIZATION_SESSION_ATTRIBUTE, local);			
		String pageToLocale = (String)request.getSession().getAttribute(PREVIOS_PAGE_SESSION_PARAM);
		
		try {
			response.sendRedirect(pageToLocale);
		} catch (IOException e) {
			logger.warn("sendRedirect() method of response in changeLocalization method of UtilService, threw IOException", e);
			throw e;			
		}		
	}


	@Override
	public void goToAboutUsPage(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException  {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.ABOUT_US_PAGE);		
		doForwardWithLog(dispatcher, request, response);		
	}
	
	
	@Override
	public void goToContactsPage(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.CONTACTS_PAGE);
		doForwardWithLog(dispatcher, request, response);		
	}
	
	
	@Override
	public void goToLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.LOGIN_PAGE);
		doForwardWithLog(dispatcher, request, response);		
	}
	
	
	@Override
	public void goToMainPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.MAIN_PAGE);
		doForwardWithLog(dispatcher, request, response);		
	}
	
	
	@Override
	public void goToOperationForm(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.OPERATION_FORM_PAGE);
		doForwardWithLog(dispatcher, request, response);
	}	
	
	
	@Override
	public void goToProfilePage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.PROFILE_PAGE);		
		String modalAttribute = request.getParameter(MODAL_ATTRIBUTE);
		
		if(modalAttribute != null) {
			request.setAttribute(MODAL_ATTRIBUTE, modalAttribute);
		}
		
		doForwardWithLog(dispatcher, request, response);
	}
	
	
	@Override
	public void goToRegistrationPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.REGISTRATION_PAGE);
		doForwardWithLog(dispatcher, request, response);
	}	
	
	
	@Override
	public void goToSloganPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.SLOGAN_PAGE);
		doForwardWithLog(dispatcher, request, response);
	}
	
	
	@Override
	public void goToSorryPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.SORRY_PAGE);	
		doForwardWithLog(dispatcher, request, response);
	}
	
	
	@Override
	public void goToUserAccountPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);
		doForwardWithLog(dispatcher, request, response);		
	}
	
	
	@Override
	public void signOut(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		request.getSession().removeAttribute(USER_PARAMETER);
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.MAIN_PAGE);
		doForwardWithLog(dispatcher, request, response);
	}
	
	
	private void doForwardWithLog(RequestDispatcher dispatcher, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			logger.warn("ServletException during forward in method of UtilService", e);
			throw e;
		} catch (IOException e) {
			logger.warn("IOException during forward in method of UtilService", e);
			throw e;
		}		
	}
}
