package by.htp.accountant.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.bean.OperationType;
import by.htp.accountant.bean.User;
import by.htp.accountant.controller.command.JSPPath;
import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.OperationTypeDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.ValidationException;
import by.htp.accountant.service.OperationTypeService;
import by.htp.accountant.util.validation.TypeParametersValidator;
import by.htp.accountant.util.validation.ValidationFactory;

public class OperationTypeServiceImpl implements OperationTypeService {

	private static final Logger logger = LoggerFactory.getLogger(OperationTypeServiceImpl.class);
	
	private OperationTypeDAO typeDAO = DAOFactory.getInstance().getOperationTypeDAO();
	private TypeParametersValidator validator = ValidationFactory.getInstance().getTypeValidator();
	
	private static final String ROLE_PARAM = "role";	
	private static final String OPERATION_TYPE_PARAM = "operationType";
	private static final String ID_PARAM = "typeId";
		
	private final static String USER_ATTRIBUTE = "user";
	private static final String MODAL_ATTRIBUTE = "modal";
	private static final String CREATE_TYPE_MODAL_ATTRIBUTE = "typeCreateModal";
	private static final String EDIT_TYPE_MODAL_ATTRIBUTE = "typeEditModal";
	private static final String IMPOSIBLE_MODAL_ATTRIBUTE = "imposibleModal";
	
	@Override
	public void createOperationType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		OperationType type = null;
		User user = (User)session.getAttribute(USER_ATTRIBUTE);
		String roleInString = request.getParameter(ROLE_PARAM);		
		String operationType = request.getParameter(OPERATION_TYPE_PARAM);
		int role = 0;
		List<String> validationErrors = new ArrayList<String>();
		
		try {
			validationErrors.addAll(validator.validateRoleAndTypeParams(roleInString, operationType));
		} catch (ValidationException e) {
			logger.warn("ValidationException in createOperationType() method - role failed validation.");
			request.setAttribute(MODAL_ATTRIBUTE, CREATE_TYPE_MODAL_ATTRIBUTE);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		if(validationErrors.isEmpty() && dispatcher == null) {
			type = makeTypeFromStringParams(roleInString, operationType, user.getId());			
		} else if (!validationErrors.isEmpty()) {
			for(String errorMsg: validationErrors) {
				request.setAttribute(errorMsg, errorMsg);
			}
			request.setAttribute(MODAL_ATTRIBUTE, CREATE_TYPE_MODAL_ATTRIBUTE);
			request.setAttribute(ROLE_PARAM, roleInString);
			dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);
		}
		
		if(type != null) {
			try {
				if(!typeDAO.createOperationType(type)) {
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
				}
			} catch (DAOException e) {
				logger.warn("DAOException in createOperationType() method of OperationTypeDAO", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_ACCOUNT_SUCCESS_PAGE);		
	}

	
	/**
	 * 
	 * @param roleInString
	 * @param operationType
	 * @param userId
	 * @return
	 */
	private OperationType makeTypeFromStringParams(String roleInString, String operationType, int userId) {
		OperationType type = new OperationType();
		int role = Integer.parseInt(roleInString);
		type.setOperationType(operationType);
		type.setRole(role);
		type.setUserId(userId);
		
		return type;
	}
	
	
	@Override
	public void editOperationType(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
				
		RequestDispatcher dispatcher = null;
					
		String typeIdInString = request.getParameter(ID_PARAM);		
		String operationType = request.getParameter(OPERATION_TYPE_PARAM);
		int typeId = 0;
		List<String> validationErrors = new ArrayList<String>();
		
		try {
			validationErrors.addAll(validator.validateIDAndTypeParams(typeIdInString, operationType));
		} catch (ValidationException e) {
			logger.warn("ValidationException in editOperationType() method - id failed validation.");
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}		
		
		if(validationErrors.isEmpty() && dispatcher == null) {
			typeId = Integer.parseInt(typeIdInString);			
		} else if (!validationErrors.isEmpty()) {
			for(String errorMsg: validationErrors) {
				request.setAttribute(errorMsg, errorMsg);
			}
			request.setAttribute(MODAL_ATTRIBUTE, EDIT_TYPE_MODAL_ATTRIBUTE);
			request.setAttribute(ID_PARAM, typeIdInString);
			dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);
		}
		
		if(typeId != 0) {
			try {
				typeDAO.editOperationType(typeId, operationType);
			} catch (DAOException e) {
				logger.warn("DAOException in editOperationType() method of OperationTypeDAO", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_ACCOUNT_SUCCESS_PAGE);
		
	}
	
	
	@Override
	public void deleteOperationType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
				
		String typeIdInString = request.getParameter(ID_PARAM);
		String typeRoleInString = request.getParameter(ROLE_PARAM);
		int typeId = 0;
		int typeRole = 0;
		try {
			if(validator.validateTypeId(typeIdInString) && validator.validateTypeRole(typeRoleInString)) {
				typeId = Integer.parseInt(typeIdInString);
				typeRole = Integer.parseInt(typeRoleInString);
			}
		} catch (ValidationException e) {
			logger.warn("ValidationException in deleteOperationType() method - id or role failed validation.");			
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		if(typeRole != OperationType.INCOME_TYPE_UNDELETEBLE_ROLE && typeRole != OperationType.SPENDING_TYPE_UNDELETEBLE_ROLE) {
			try {
				typeDAO.deleteOperationType(typeId);
			} catch (DAOException e) {
				logger.warn("DAOException in deleteOperationType() method of OperationTypeDAO", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		} else {
			request.setAttribute(MODAL_ATTRIBUTE, IMPOSIBLE_MODAL_ATTRIBUTE);
			dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_ACCOUNT_SUCCESS_PAGE);
		
	}


	
	
	/**
	 * Private method of OperationTypeServiceImpl which do forward or redirect depends on RequestDispatcher value. 
	 * Also method makes log if forward or sendRedirect throw exception.
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @param dispatcher - RequestDispatcher
	 * @param redirectPage - String
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doSendRedirectOrForward(HttpServletRequest request, HttpServletResponse response, RequestDispatcher dispatcher, String redirectPage) 
					throws IOException, ServletException {
		
		if(dispatcher == null) {		
			try {
				response.sendRedirect(redirectPage);
			} catch (IOException e) {
				logger.warn("IOException while doing sendRedirect in OperationTypeServiceImpl", e);
				throw e;
			}	
		}else {	
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				logger.warn("ServletException while doing forward in OperationTypeServiceImpl", e);
				throw e;
			} catch (IOException e) {
				logger.warn("IOException while doing forward in OperationTypeServiceImpl", e);
				throw e;
			}				
		}
	}


	
	
	
	
}
