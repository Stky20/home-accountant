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
import by.htp.accountant.service.UtilService;
import by.htp.accountant.util.validation.UtilValidator;
import by.htp.accountant.util.validation.ValidationFactory;

public class UtilServiceImpl implements UtilService {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilServiceImpl.class);
	
	private final OperationTypeDAO typeDAO = DAOFactory.getInstance().getOperationTypeDAO();
	private final OperationDAO operationDAO = DAOFactory.getInstance().getOperationDAO();
	
	private final UtilValidator validator = ValidationFactory.getInstance().getUtilValidator();
	
	private static final String LOCALIZATION_PARAM_FROM_REQUEST = "localization";	
	private static final String LOCALIZATION_SESSION_ATTRIBUTE = "local";	
	private static final String PREVIOS_PAGE_SESSION_PARAM ="previousPage";
	private static final String MODAL_ATTRIBUTE = "modal";
	private static final String USER_PARAMETER = "user";
	private static final String OPERATION_TYPE_PARAMETER = "operationType";
	private static final String TYPE_ID_PARAMETER = "typeId";
	private static final String PAGE_NUMBER_PARAMETER = "pageNumber";
	private static final String AMOUNT_OF_PAGES_PARAMETER = "amountOfPages";
	private static final String OPERATIONS_LIST_PARAMETER = "operationsList";

	private static final String BALANCE_ATTRIBUTE = "balance";
	private static final String SPENDING_TYPES_LIST_ATTRIBUTE = "spendingTypesList";
	private static final String INCOME_TYPES_LIST_ATTRIBUTE = "incomeTypesList";
	private static final String FIRST_DATE_ATTRIBUTE = "firstDate";
	private static final String LAST_DATE_ATTRIBUTE = "lastDate";
	
	private static final int FIRST_DAY_OF_MONTH_OR_WEEK_VALUE = 1;
	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final int DEFAULT_RECORDINGS_AMOUNT = 10;
		

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

		RequestDispatcher dispatcher = null;
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute(USER_PARAMETER);
		String firstDateInString = request.getParameter(FIRST_DATE_ATTRIBUTE);
		String lastDateInString = request.getParameter(LAST_DATE_ATTRIBUTE);
		
		LocalDate currentDate = determinLastDate(firstDateInString, lastDateInString);
		LocalDate firstDateOfCurrentMonth = determinFirstDate(firstDateInString, lastDateInString);
		
		List<OperationType> spendingTypesList = null;
		List<OperationType> incomeTypesList = null;
		List<Operation> operationListDuringPeriod = null;
		
		try {
			spendingTypesList = typeDAO.getUserOperationTypesDependingOnTypeRole(user.getId(), OperationType.SPENDING_TYPE_ROLE, OperationType.SPENDING_TYPE_UNDELETEBLE_ROLE);
			incomeTypesList = typeDAO.getUserOperationTypesDependingOnTypeRole(user.getId(), OperationType.INCOME_TYPE_ROLE, OperationType.INCOME_TYPE_UNDELETEBLE_ROLE);
		} catch (DAOException e) {
			logger.warn("DAOException while geting operationTypesLists", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);			
		}	
		
		
		try {
			operationListDuringPeriod = getOperationListDuringPeriod(user.getId(), currentDate, firstDateOfCurrentMonth);
		} catch (ServiceException e) {
			logger.warn("ServiceException while doting getOperationListDuringPeriod() method in goToUserAccountPage() method od UtilService", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);		
		}		
		
		
		if(dispatcher == null) {
			request = setToRequestBalanceSpendingListAndIncomeList(operationListDuringPeriod, spendingTypesList, incomeTypesList, currentDate, firstDateOfCurrentMonth, request);			
			dispatcher = request.getRequestDispatcher(JSPPath.USER_ACCOUNT_PAGE);
		}		
		
		doForwardWithLog(dispatcher, request, response);		
	}
	
	
	private List<Operation> getOperationListDuringPeriod(int userId, LocalDate currentDate, LocalDate firstDateOfCurrentMonth) throws ServiceException{
		List<Operation> operationsList = new ArrayList<Operation>();
		
		try {
			if(currentDate.equals(firstDateOfCurrentMonth)) {
				operationsList = operationDAO.getAllOperationsAtDate(userId, currentDate);
			} else {
				operationsList = operationDAO.getAllOperationsDuringPeriod(userId, firstDateOfCurrentMonth, currentDate);
			}
		} catch (DAOException e) {
			throw new ServiceException("DAOException while doting getAllOperationsDuringPeriod() or getAllOperationsAtDate()", e);
		}
		
		return operationsList;
	}
	
	
	/**
	 * 
	 * @param operationListDuringPeriod
	 * @param spendingTypesList
	 * @param incomeTypesList
	 * @param session
	 * @return
	 */
	private HttpServletRequest setToRequestBalanceSpendingListAndIncomeList(List<Operation> operationListDuringPeriod, 
			List<OperationType> spendingTypesList, List<OperationType> incomeTypesList, LocalDate currentDate, 
			LocalDate firstDateOfMonth, HttpServletRequest request) {
		
		double balance = 0;
		
		balance = getBalance(operationListDuringPeriod, spendingTypesList, incomeTypesList);
		spendingTypesList = calculateSharesOfEachType(operationListDuringPeriod, spendingTypesList);
		incomeTypesList = calculateSharesOfEachType(operationListDuringPeriod, incomeTypesList);
		
		if(currentDate.equals(firstDateOfMonth)) {
			request.setAttribute(FIRST_DATE_ATTRIBUTE, firstDateOfMonth.toString());
		} else {
			if(firstDateOfMonth != null) {
				request.setAttribute(FIRST_DATE_ATTRIBUTE, firstDateOfMonth.toString());
			}
			request.setAttribute(LAST_DATE_ATTRIBUTE, currentDate.toString());
		}		
		request.setAttribute(BALANCE_ATTRIBUTE, balance);
		request.setAttribute(SPENDING_TYPES_LIST_ATTRIBUTE, spendingTypesList);
		request.setAttribute(INCOME_TYPES_LIST_ATTRIBUTE, incomeTypesList);
		
		return request;
	}
	
	/**
	 * 
	 * @param operationListDuringPeriod
	 * @param spendingTypesList
	 * @param incomeTypesList
	 * @return
	 */
	private double getBalance(List<Operation> operationListDuringPeriod, List<OperationType> spendingTypesList, List<OperationType> incomeTypesList) {
		
		double spendingsSumm = 0;
		double incomeSumm = 0;
		
		for(OperationType type: spendingTypesList) {
			for(Operation operation: operationListDuringPeriod) {
				if(operation.getOperationTypeId() == type.getId()) {
					spendingsSumm = spendingsSumm + operation.getAmount();
				}
			}
		}
		
		for(OperationType type: incomeTypesList) {
			for(Operation operation: operationListDuringPeriod) {
				if(operation.getOperationTypeId() == type.getId()) {
					incomeSumm = incomeSumm + operation.getAmount();
				}
			}
		}
		
		return Operation.roundDoubleCalculations(incomeSumm - spendingsSumm);
	}
	
	/**
	 * 
	 * @param operationListDuringPeriod
	 * @param spendingTypesList
	 * @return
	 */
	private List<OperationType> calculateSharesOfEachType(List<Operation> operationListDuringPeriod, List<OperationType> typesList){
		
		double allOperationsSumm = 0;		
		double summOfOperationAmountsOfOneType = 0;
		
		if(!operationListDuringPeriod.isEmpty()) {
			for(OperationType type: typesList) {
				for(Operation operation: operationListDuringPeriod) {
					if(operation.getOperationTypeId() == type.getId()) {
						allOperationsSumm = allOperationsSumm + operation.getAmount();
					}
				}
			}
		}
		
		
		if(allOperationsSumm != 0) {
			for(OperationType type: typesList) {				
				for(Operation operation: operationListDuringPeriod) {
					if(operation.getOperationTypeId() == type.getId()) {
						summOfOperationAmountsOfOneType = summOfOperationAmountsOfOneType + operation.getAmount();
					}
				}
				
				type.setPercentOfAllTypes(Operation.roundDoubleCalculations(summOfOperationAmountsOfOneType*100/allOperationsSumm));
				summOfOperationAmountsOfOneType = 0;
			}
		}
		
		return typesList;
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
	
	
	@Override
	public void goToUserOperationsPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = null;		
		
		String firstDateInString = request.getParameter(FIRST_DATE_ATTRIBUTE);
		String lastDateInString = request.getParameter(LAST_DATE_ATTRIBUTE);
		
		String operationType = request.getParameter(OPERATION_TYPE_PARAMETER);
		String typeIdInString = request.getParameter(TYPE_ID_PARAMETER);		
		String pageNumberInString = request.getParameter(PAGE_NUMBER_PARAMETER);
				
		List<Operation> operationsList = new ArrayList<Operation>();
		int typeId = 0;
		int amountOfPages = 0;
		int pageNumber = 0;
		
		LocalDate firstDate = determinFirstDate(firstDateInString, lastDateInString);
		LocalDate lastDate = determinLastDate(firstDateInString, lastDateInString);
		
		
		try {
			if(validator.validatTypeIdNumber(operationType, typeIdInString, pageNumberInString)) {
				typeId = Integer.parseInt(typeIdInString);
				pageNumber = Integer.parseInt(pageNumberInString);
			} else {
				typeId = Integer.parseInt(typeIdInString);
				pageNumber = DEFAULT_PAGE_NUMBER;
			}
		} catch (ValidationException e) {
			logger.info("ValidationException while validating operationType, typeId, pageNumber to execute goToUserOperationsPage()", e);
			dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
		}
		
		if(dispatcher == null) {			
			try {
				amountOfPages = getAmountOfPages(typeId, firstDate, lastDate);
			} catch (ServiceException e) {
				logger.warn("ServiceException trying to execute getAmountOfPages(typeId, firstDate, lastDate) method of UtilServiceImpl", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}			
		}		
		
		if(dispatcher == null) {
			int firstRecordToGetFromDB = (pageNumber - 1) * DEFAULT_RECORDINGS_AMOUNT;			
			try {
				operationsList = getOneTypeOperationsList(typeId, firstDate, lastDate, firstRecordToGetFromDB);
			} catch (ServiceException e) {
				logger.warn("ServiceException trying to execute getOneTypeOperationsList(typeId, firstDate, lastDate, firstRecordToGetFromDB) method of UtilServiceImpl", e);
				dispatcher = request.getRequestDispatcher(JSPPath.TECHNICAL_ERROR_PAGE);
			}
		}
		
		if(dispatcher == null) {
			request = setAttributesToRequest(firstDate, lastDate, request, amountOfPages, pageNumber, operationsList, operationType, typeId);
			dispatcher = request.getRequestDispatcher(JSPPath.USER_OPERATIONS_PAGE);
		}
		
		doForwardWithLog(dispatcher, request, response);		
		
	}
	
	private LocalDate determinLastDate(String firstDateInString, String lastDateInString) {
		LocalDate lastDate = null;
		LocalDate firstDate = null;
		LocalDate currentDate = LocalDate.now();
		
		if(validator.oneParameterNullEmptyCheck(firstDateInString)&&validator.oneParameterNullEmptyCheck(lastDateInString)) {
			return currentDate;
		}
		
		try {
			if(validator.validateDate(lastDateInString)) {
				lastDate = LocalDate.parse(lastDateInString);
			} else {
				lastDate = LocalDate.now();
			}
		} catch (ValidationException e) {
			logger.info("ValidationException while validating date to execute goToUserOperationsPage()", e);
			lastDate = LocalDate.now();
		}
		
		try {
			if(validator.validateDate(firstDateInString)) {
				firstDate = LocalDate.parse(firstDateInString);
			} else {
				firstDate = null;
			}
		} catch (ValidationException e) {
			logger.info("ValidationException while validating date to execute goToUserOperationsPage()", e);
			firstDate = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), FIRST_DAY_OF_MONTH_OR_WEEK_VALUE);
		}
		
		LocalDate spareDate = null;
		if(firstDate == null) {
			return lastDate;
		}
		
		if(firstDate.isAfter(lastDate)) {
			spareDate = firstDate;
			firstDate = lastDate;
			lastDate = spareDate;
		}
		
		
		return lastDate;
	}
	
	private LocalDate determinFirstDate(String firstDateInString, String lastDateInString) {
		LocalDate lastDate = null;
		LocalDate firstDate = null;
		LocalDate currentDate = LocalDate.now();
		
		if(validator.oneParameterNullEmptyCheck(firstDateInString)&&validator.oneParameterNullEmptyCheck(lastDateInString)) {
			return LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), FIRST_DAY_OF_MONTH_OR_WEEK_VALUE);
		}
		
		try {
			if(validator.validateDate(lastDateInString)) {
				lastDate = LocalDate.parse(lastDateInString);
			} else {
				lastDate = currentDate;
			}
		} catch (ValidationException e) {
			logger.info("ValidationException while validating date to execute goToUserOperationsPage()", e);
			lastDate = currentDate;
		}
		
		try {
			if(validator.validateDate(firstDateInString)) {
				firstDate = LocalDate.parse(firstDateInString);
			} else {
				return null;
			}
		} catch (ValidationException e) {
			logger.info("ValidationException while validating date to execute goToUserOperationsPage()", e);
			firstDate = LocalDate.of(currentDate.getYear(), currentDate.getMonthValue(), FIRST_DAY_OF_MONTH_OR_WEEK_VALUE);
		}
		
		LocalDate spareDate = null;
		if(firstDate == null) {
			return null;
		}
		
		if(firstDate.isAfter(lastDate)) {
			spareDate = firstDate;
			firstDate = lastDate;
			lastDate = spareDate;
		}
		if(firstDate.equals(lastDate)) {
			firstDate = null;
		}	
		
		return firstDate;		
	}
	
	private HttpServletRequest setAttributesToRequest(LocalDate firstDate, LocalDate lastDate, HttpServletRequest request, 
													  int amountOfPages, int pageNumber, List<Operation> operationsList,String operationType, int typeId) {
				
		if(firstDate == null) {
			request.setAttribute(LAST_DATE_ATTRIBUTE, lastDate);			
		} else {
			request.setAttribute(FIRST_DATE_ATTRIBUTE, firstDate);
			request.setAttribute(LAST_DATE_ATTRIBUTE, lastDate);			
		}
		request.setAttribute(AMOUNT_OF_PAGES_PARAMETER, amountOfPages);
		request.setAttribute(OPERATIONS_LIST_PARAMETER, operationsList);
		request.setAttribute(PAGE_NUMBER_PARAMETER, pageNumber);
		request.setAttribute(OPERATION_TYPE_PARAMETER, operationType);
		request.setAttribute(TYPE_ID_PARAMETER, typeId);
		return request;
	}
	
	private int getAmountOfPages(int typeId, LocalDate firstDate, LocalDate lastDate) throws ServiceException{
		int amountOfPages = 0;
		try {
			if(firstDate == null) {				
				amountOfPages = operationDAO.getOneTypeOperationsNumberOfPagesAtDate(typeId, lastDate);				
			} else {
				amountOfPages = operationDAO.getOneTypeOperationsNumberOfPagesBetweenDates(typeId, firstDate, lastDate);
			}
		} catch (DAOException e) {
			throw new ServiceException("DAOException in getAmountOfPages() of UtilServiceImpl", e);
		}
		return amountOfPages;
	}
	
	
	private List<Operation> getOneTypeOperationsList(int typeId, LocalDate firstDate, LocalDate lastDate, int firstRecordToGetFromDB) throws ServiceException{
		List<Operation> operationsList = new ArrayList<Operation>();
		try {
			if(firstDate == null) {
				operationsList = operationDAO.getOneTypeOperationsAtDate(typeId, lastDate, firstRecordToGetFromDB, DEFAULT_RECORDINGS_AMOUNT);
			} else {
				operationsList = operationDAO.getOneTypeOperationsBetweenDates(typeId, firstDate, lastDate, firstRecordToGetFromDB, DEFAULT_RECORDINGS_AMOUNT);
			}
		} catch (DAOException e) {
			throw new ServiceException("DAOException in getOneTypeOperationsList() of UtilServiceImpl", e);
		}
		return operationsList;
	}


	@Override
	public void goToResourceNotReadyPage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.RESOURCE_NOT_READY_PAGE);
		doForwardWithLog(dispatcher, request, response);	
	}

	
}
