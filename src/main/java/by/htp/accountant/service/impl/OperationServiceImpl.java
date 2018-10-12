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
import by.htp.accountant.exception.ValidationException;
import by.htp.accountant.service.OperationService;
import by.htp.accountant.util.validation.OperationParameterValidator;
import by.htp.accountant.util.validation.ValidationFactory;

public class OperationServiceImpl implements OperationService {

	private static final Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);
	
	private OperationDAO operationDAO = DAOFactory.getInstance().getOperationDAO();
	private OperationTypeDAO typeDAO = DAOFactory.getInstance().getOperationTypeDAO();
	private OperationParameterValidator validator = ValidationFactory.getInstance().getOperationValidator();
	
	private static final String OPERATION_TYPE_PARAM = "operationType";
	private static final String OPERATION_TYPE_ROLE_PARAM = "operationTypeRole";
	private static final String OPERATION_TYPE_ID_PARAM = "typeId";
	private static final String OPERATION_TYPE_SPENTING_PARAM = "spending";
	private static final String OPERATION_TYPE_INCOME_PARAM = "income";
	private static final String OPERATION_REMARK_PARAM = "remark";
	private static final String OPERATION_DATE_PARAM = "date";
	private static final String OPERATION_AMOUNT_PARAM = "amount";
	private static final String OPERATION_ID_PARAM = "operationId";
	
	private final static String USER_ATTRIBUTE = "user";	
	private static final String SPENDING_TYPES_LIST_ATTRIBUTE = "spendingTypesList";
	private static final String INCOME_TYPES_LIST_ATTRIBUTE = "incomeTypesList";
	
	private static final String MODAL_ATTRIBUTE = "modal";
	private static final String MODAL_CREATE_OPERATION_ATTRIBUTE = "operationCreateModal";	
	private static final String MODAL_EDIT_OPERATION_ATTRIBUTE = "modalEditOperation";
	
	private static final String FIRST_DATE_ATTRIBUTE = "firstDate";
	private static final String LAST_DATE_ATTRIBUTE = "lastDate";
	private static final String OPERATION_TYPE_PARAMETER = "operationType";
	private static final String TYPE_ID_PARAMETER = "typeId";
	private static final String PAGE_NUMBER_PARAMETER = "pageNumber";
	private static final String AMOUNT_OF_PAGES_PARAMETER = "amountOfPages";
	
	
	
	@Override
	public void createOperation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		
		User user = (User) session.getAttribute(USER_ATTRIBUTE);
		String typeIdInString = request.getParameter(OPERATION_TYPE_ID_PARAM);		
		String remark = request.getParameter(OPERATION_REMARK_PARAM);
		String date = request.getParameter(OPERATION_DATE_PARAM);
		String amount = request.getParameter(OPERATION_AMOUNT_PARAM);
		int operationTypeId = 0;
		
		List<String> paramsValidationErrors = new ArrayList<String>();
		Operation operation = null;
			
		try {
			operationTypeId = findtypeId(typeIdInString, user.getId(), request);
		} catch (ServiceException e) {
			logger.warn("ServiceException while doing findtypeId() in createOperation() method of OperationServiceImpl", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}	
		
		if(dispatcher == null) {			
	
			paramsValidationErrors.addAll(validator.validateOperationParams(remark, date, amount));			
			
			if(!paramsValidationErrors.isEmpty()) {
				for(String errorMsg: paramsValidationErrors) {
					request.setAttribute(errorMsg, errorMsg);					
				}
				request.setAttribute(MODAL_ATTRIBUTE, MODAL_CREATE_OPERATION_ATTRIBUTE);
				dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);				
			} else {
				operation = makeOperationFromStringParams(user.getId(), operationTypeId, date, amount, remark);				
			}
			
			if (operation != null) {
				try {
					if(!operationDAO.createOperation(operation)) {
						logger.info("Couldn`t create operation becourse of operationDAO.createOperation(operation) returned false"
								+ " in createOperation() method");
						dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
					}						
					
				} catch (DAOException e) {
					logger.warn("DAOException while creatingOperation() method", e);
					dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);					
				}
			}		
		}
		
		doSendRedirectOrForward(request, response, dispatcher, JSPPath.GO_TO_USER_ACCOUNT_SUCCESS_PAGE);
		
	}
	
	
	private int findtypeId(String typeIdInString, int userId, HttpServletRequest request) throws ServiceException {
		
		String operationTypeRole =  null;
		String operationType =  null;
		int operationTypeId = 0;
		List<OperationType> spendingTypesList = null; 
		List<OperationType> incomeTypesList = null;			
		
		if(validator.oneParameterNullEmptyCheck(typeIdInString)) {
			operationTypeRole =  request.getParameter(OPERATION_TYPE_ROLE_PARAM);
			try {
				if(validator.validateTypeRole(operationTypeRole)) {
					operationType = getParamOperationTypeOnRole(operationTypeRole, request);
					validator.validateOperationType(operationType);
					System.out.println(validator.validateOperationType(operationType));
				}
			} catch (ValidationException e) {
				throw new ServiceException("ValidationException while validating operationTypeRole and operationType in findtypeId() method of OperationServiceImpl",e);				
			}
			
			try {
				spendingTypesList = typeDAO.getUserOperationTypesDependingOnTypeRole(userId, OperationType.SPENDING_TYPE_ROLE, OperationType.SPENDING_TYPE_UNDELETEBLE_ROLE);
				incomeTypesList = typeDAO.getUserOperationTypesDependingOnTypeRole(userId, OperationType.INCOME_TYPE_ROLE, OperationType.INCOME_TYPE_UNDELETEBLE_ROLE);
				operationTypeId = getOperationTypeIdFromTypesLists(operationTypeRole, operationType, spendingTypesList, incomeTypesList);
			} catch (DAOException e) {
				throw new ServiceException("DAOException while geting operationTypesLists in findtypeId() method of OperationServiceImpl",e);
			}							
			
		} else {
			try {
				validator.validateTypeId(typeIdInString);
				operationTypeId = Integer.parseInt(typeIdInString);
			} catch (ValidationException e) {
				throw new ServiceException("ValidationException while validating typeIdInString in createOperation() method of OperationServiceImpl", e);
			}
		}
		
		return operationTypeId;
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
			
			if(realOperationTypeRole == OperationType.SPENDING_TYPE_ROLE) {
				operationType = request.getParameter(OPERATION_TYPE_SPENTING_PARAM);
			} else if (realOperationTypeRole == OperationType.INCOME_TYPE_ROLE) {
				operationType = request.getParameter(OPERATION_TYPE_INCOME_PARAM);
			} 
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
			logger.info("Somehow in getOperationTypeIdFromTypesLists() method of OperationServiceImpl came operationTypeRole: " 
						+ operationTypeRole + " and operationType: " + operationType);
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
	private Operation makeOperationFromStringParams(int userId, int operationTypeId, String date, String amount, String remark) {
		Operation operation = new Operation();
		LocalDate operationDate = null;
		String rightAmount = amount.replace(",", ".");
		double operationAmount = 0;
		
		if (validator.oneParameterNullEmptyCheck(date)) {
			operationDate = LocalDate.now();
		} else {
			operationDate = LocalDate.parse(date);
		}
		
		operationAmount = Double.parseDouble(rightAmount);		
		
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
	
	
	@Override
	public void deleteOperation(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		int operationId = 0;	
		String redirectPath = JSPPath.GO_TO_USER_ACCOUNT_SUCCESS_PAGE;
		
		String operationIdIdInString = request.getParameter(OPERATION_ID_PARAM);		
		
		try {
			if(validator.validateTypeId(operationIdIdInString)) {			
				operationId = Integer.parseInt(request.getParameter(OPERATION_ID_PARAM));
			}
		} catch (ValidationException e) {
			logger.warn("operationIdIdInString didn`t pass validation in deleteOperation() of OperationServiceImpl",e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		} 
		
		
		if(dispatcher == null) {
			try {
				if(!operationDAO.deleteOperationById(operationId)) {
					redirectPath = JSPPath.GO_TO_USER_ACCOUNT_FAIL_PAGE;
				}
			} catch (DAOException e) {
				logger.warn("DAOException while trying to execute deleteOperation() of OperationServiceImpl",e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}
		
		doSendRedirectOrForward(request, response, dispatcher, redirectPath);
		
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


	@Override
	public void editOperation(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession(true);
		
		User user = (User) session.getAttribute(USER_ATTRIBUTE);
		String typeIdInString = request.getParameter(OPERATION_TYPE_ID_PARAM);
		String operationType = request.getParameter(OPERATION_TYPE_PARAM);
		String operationIdInString = request.getParameter(OPERATION_ID_PARAM);
		String remark = request.getParameter(OPERATION_REMARK_PARAM);
		String dateInString = request.getParameter(OPERATION_DATE_PARAM);
		String amountInString = request.getParameter(OPERATION_AMOUNT_PARAM);
		String redirectPage = JSPPath.GO_TO_USER_ACCOUNT_SUCCESS_PAGE;
		
		int operationId = 0;
		double amount = 0;
		
		List<String> paramsValidationErrors = new ArrayList<String>();
		LocalDate date = null;
		
		System.out.println(operationIdInString);
		paramsValidationErrors.addAll(validator.validateOperationParams(remark, dateInString, amountInString));
		
		if(paramsValidationErrors.isEmpty()) {
			try {
				if(validator.validateTypeId(operationIdInString)){
					operationId = Integer.parseInt(operationIdInString);
					amount = Double.parseDouble(amountInString.replaceAll(",", "."));
					if(!validator.oneParameterNullEmptyCheck(dateInString)) {
						date = LocalDate.parse(dateInString);
					} else {
						date = LocalDate.now();
					}
					try {
						if(!operationDAO.editOperation(operationId, amount, remark, date)){
							redirectPage = JSPPath.GO_TO_USER_ACCOUNT_FAIL_PAGE;
						}
					} catch (DAOException e) {
						logger.warn("DAOException in editOperation() of OperationServiceImpl");
						dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
					}
				}
			} catch (ValidationException e) {
				logger.warn("operationIdInString didn`t pass validation in editOperation() of OperationServiceImpl");
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		} else {
			for(String errorMsg: paramsValidationErrors) {
				request.setAttribute(errorMsg, errorMsg);
			}
			request.setAttribute(MODAL_ATTRIBUTE, MODAL_EDIT_OPERATION_ATTRIBUTE);
			dispatcher = request.getRequestDispatcher(JSPPath.USER_OPERATIONS_PAGE);
		}
		
		doSendRedirectOrForward(request, response, dispatcher, redirectPage);	
		
	}	

}
