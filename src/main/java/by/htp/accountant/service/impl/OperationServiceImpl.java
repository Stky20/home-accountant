package by.htp.accountant.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.bean.Operation;
import by.htp.accountant.bean.OperationType;
import by.htp.accountant.bean.User;
import by.htp.accountant.controller.command.JSPPath;
import by.htp.accountant.dao.DAOFactory;
import by.htp.accountant.dao.OperationDAO;
import by.htp.accountant.dao.OperationTypeDAO;
import by.htp.accountant.exception.DAOException;
import by.htp.accountant.exception.ServiceException;
import by.htp.accountant.service.OperationService;
import by.htp.accountant.util.validation.OperationParameterValidator;
import by.htp.accountant.util.validation.UserParameterValidator;
import by.htp.accountant.util.validation.ValidationFactory;

import static by.htp.accountant.util.validation.ValidationErrorMessage.*;

public class OperationServiceImpl implements OperationService {

	private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	private OperationDAO operationDAO = DAOFactory.getInstance().getOperationDAO();
	private OperationTypeDAO typeDAO = DAOFactory.getInstance().getOperationTypeDAO();
	private OperationParameterValidator validator = ValidationFactory.getInstance().getOperationValidator();
	
	private static final String OPERATION_TYPE_ROLE_PARAM = "operationTypeRole";
	private static final String OPERATION_TYPE_SPENTING_PARAM = "spending";
	private static final String OPERATION_TYPE_INCOME_PARAM = "income";
	private static final String OPERATION_REMARK_PARAM = "remark";
	private static final String OPERATION_DATE_PARAM = "date";
	private static final String OPERATION_AMOUNT_PARAM = "amount";
	
	private final static String USER_ATTRIBUTE = "user";
	private static final String SPENDING_TYPES_LIST_ATTRIBUTE = "spendingTypesList";
	private static final String INCOME_TYPES_LIST_ATTRIBUTE = "incomeTypesList";
	private static final String MODAL_CREATE_OPERATION_ATTRIBUTE = "operationCreateModal";
	private static final String MODAL_CREATE_OPERATION_TYPE_ATTRIBUTE = "modalCreateOperationType";
	private static final String MODAL_EDIT_OPERATION_ATTRIBUTE = "modalEditOperation";
	private static final String MODAL_EDIT_OPERATION_TYPE_ATTRIBUTE = "modalEditOperationType";
	private static final String MODAL_SUCCESS_ATTRIBUTE = "modalSuccess";
	private static final String MODAL_ARE_U_SURE_TYPE_ATTRIBUTE = "modalAreUSure";
	
	
	
	@Override
	public void createOperation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		
		User user = (User) session.getAttribute(USER_ATTRIBUTE);		
		String operationTypeRole = request.getParameter(OPERATION_TYPE_ROLE_PARAM);		
		String operationType = getParamOperationTypeOnRole(operationTypeRole, request);
		String remark = request.getParameter(OPERATION_REMARK_PARAM);
		String date = request.getParameter(OPERATION_DATE_PARAM);
		String amount = request.getParameter(OPERATION_AMOUNT_PARAM);
		
		List<String> paramsValidationErrors = new ArrayList<String>();
		List<OperationType> spendingTypesList = null; 
		List<OperationType> incomeTypesList = null;		
		Operation operation = null;
		
		try {
			spendingTypesList = typeDAO.getUserOperationTypesDependingOnTypeRole(user.getId(), OperationType.SPENDING_TYPE_ROLE);
			incomeTypesList = typeDAO.getUserOperationTypesDependingOnTypeRole(user.getId(), OperationType.INCOME_TYPE_ROLE);
		} catch (DAOException e) {
			logger.warn("DAOException while geting operationTypesLists", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);			
		}		
		
		if(dispatcher == null) {			
	
			paramsValidationErrors.addAll(validator.validateOperationParams(remark, date, amount));
			
			int operationTypeId = getOperationTypeIdFromTypesLists(operationTypeRole, operationType, spendingTypesList, incomeTypesList);
			
			if (operationTypeId < 0) {
				paramsValidationErrors.add(WRONG_OPERATION_TYPE_OR_ROLE_ERROR_MSG);
			}
			
			if(!paramsValidationErrors.isEmpty()) {
				for(String errorMsg: paramsValidationErrors) {
					request.setAttribute(errorMsg, errorMsg);					
				}
				request.setAttribute(MODAL_CREATE_OPERATION_ATTRIBUTE, MODAL_CREATE_OPERATION_ATTRIBUTE);
				dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);				
			} else {
				try {
					operation = makeOperationFromStringParams(user.getId(), operationTypeId, date, amount, remark);
				} catch (ServiceException e) {
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
				}
			}
			
			if (operation != null && dispatcher == null) {
				try {
					if(!operationDAO.createOperation(operation)) {
						logger.info("Couldn`t create operation becourse of operationDAO.createOperation(operation) returned false"
								+ " in createOperation() method - line 107");
						dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
					}						
					
				} catch (DAOException e) {
					logger.warn("DAOException while creatingOperation() method", e);
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);					
				}
			}		
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_ACCOUNT_PAGE);
		
	}
	
	
	/**
	 * 
	 * @param operationTypeRole
	 * @param request
	 * @return
	 */
	private String getParamOperationTypeOnRole(String operationTypeRole, HttpServletRequest request){
		int realOperationTypeRole = 0;
		String operationType = null;
		
		if(!validator.oneParameterNullEmptyCheck(operationTypeRole)) {
			try {
				realOperationTypeRole = Integer.parseInt(operationTypeRole);
			} catch(NumberFormatException e) {
				logger.info("NumberFormatException while trying to do parseInt() in getParamOperationTypeOnRole() method of OperationServiceImpl", e);				
			}
		} 
		
		if(realOperationTypeRole == OperationType.SPENDING_TYPE_ROLE) {
			operationType = request.getParameter(OPERATION_TYPE_SPENTING_PARAM);
		} else if (realOperationTypeRole == OperationType.INCOME_TYPE_ROLE) {
			operationType = request.getParameter(OPERATION_TYPE_INCOME_PARAM);
		} 
		
		return operationType;
	}
	
	
	/**
	 * 
	 * @param operationTypeRole
	 * @param operationType
	 * @param spendingTypesList
	 * @param incomeTypesList
	 * @return
	 */
	private int getOperationTypeIdFromTypesLists(String operationTypeRole, String operationType, 
			List<OperationType> spendingTypesList, List<OperationType> incomeTypesList) {
		
		int wrongOperationTypeId = -1;
		int realOperationTypeRole = 0;		
		
		if(validator.ifSomeOfParamsNullEmptyCheck(operationTypeRole, operationType)) {
			logger.info("Somehow in getOperationTypeIdFromTypesLists() method of OperationServiceImpl as parameters came operationTypeRole -" 
						+ operationTypeRole + " and as operationType - " + operationType);
			return -1;
		}
		
		try {
			realOperationTypeRole = Integer.parseInt(operationTypeRole);			
		} catch(NumberFormatException e) {
			logger.info("NumberFormatException while trying to do parseInt() of parameter - operationTypeRole in  "
					+ "getOperationTypeIdFromTypesLists() method of OperationServiceImpl", e);
			return wrongOperationTypeId;
		}
		
		
		if(realOperationTypeRole == OperationType.SPENDING_TYPE_ROLE) {
			for (OperationType type: spendingTypesList) {				
				if(type.getOperationType().equals(operationType)) {					
					return type.getId();
				}
			}
		} else if (realOperationTypeRole == OperationType.INCOME_TYPE_ROLE) {
			for (OperationType type: incomeTypesList) {				
				if(type.getOperationType().equals(operationType)) {					
					return type.getId();
				}
			}
		}
		
		return wrongOperationTypeId;
	}
	
	/**
	 * 
	 * @param userId
	 * @param operationTypeId
	 * @param date
	 * @param amount
	 * @param remark
	 * @return
	 * @throws ServiceException 
	 */
	private Operation makeOperationFromStringParams(int userId, int operationTypeId, String date, String amount, String remark) throws ServiceException {
		Operation operation = new Operation();
		LocalDate operationDate = null;
		String rightAmount = amount.replace(",", ".");
		double operationAmount = 0;
		
		if (validator.oneParameterNullEmptyCheck(date)) {
			operationDate = LocalDate.now();
		} else {
			operationDate = LocalDate.parse(date);
		}
		
		try {
			operationAmount = Double.parseDouble(rightAmount);
		} catch (NumberFormatException e) {
			logger.info("NumberFormatException while trying to do parseDouble() parameter rightAmount in "
					+ "makeOperationFromStringParams() method of OperationServiceImpl", e);
			throw new ServiceException("NumberFormatException while trying to do parseDouble() parameter rightAmount in \"\r\n" + 
					"					+ \"makeOperationFromStringParams() method of OperationServiceImpl", e);
		}
		
		if(validator.oneParameterNullEmptyCheck(remark)) {
			remark = null;
		}
		
		operation = new Operation();
		operation.setAmount(operationAmount);
		operation.setDate(operationDate);
		operation.setRemark(remark);
		operation.setOperationTypeId(operationTypeId);
		operation.setUserId(userId);
		
		return operation;
	}
	
	/**
	 * Private method of OperationServiceImpl which do forward or redirect depends on RequestDispatcher value. 
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
				logger.warn("IOException while doing sendRedirect in OperationServiceImpl", e);
				throw e;
			}	
		}else {	
			try {
				dispatcher.forward(request, response);
			} catch (ServletException e) {
				logger.warn("ServletException while doing forward in OperationServiceImpl", e);
				throw e;
			} catch (IOException e) {
				logger.warn("IOException while doing forward in OperationServiceImpl", e);
				throw e;
			}				
		}
	}

}
