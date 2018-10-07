package by.htp.accountant.util.validation.impl;

import java.util.ArrayList;
import java.util.List;

import by.htp.accountant.util.validation.OperationParameterValidator;

import static by.htp.accountant.util.validation.ValidationErrorMessage.*;

public class OperationParameterValidatorImpl implements OperationParameterValidator {
	
	public final static String OPERATION_REMARK_PATTERN = "[\\wА-Яа-я-_\\.,:;\\(\\)\\s]{1,60}";	
	public final static String OPERATION_DATE_PATTERN = "^((19|20)\\\\d\\\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
	public final static String OPERATION_AMOUNT_PATTERN = "\\d+([,.]\\d{1,2})?";
	

	@Override
	public boolean oneParameterNullEmptyCheck(String parameter) {
		if (parameter == null ) {
			return true;
		}							
		return parameter.trim().isEmpty();	
	}

	@Override
	public boolean ifSomeOfParamsNullEmptyCheck(String... params) {
		for(int i = 0; i < params.length; i++) {
			if(params[i] == null) return true;
			if(params[i].trim().isEmpty()) return true;
		}
		
		return false;
	}
	
	
	@Override
	public String validateOperationRemark(String remark) {
		if(!oneParameterNullEmptyCheck(remark)) {
			if(!remark.matches(OPERATION_REMARK_PATTERN)) {
				return WRONG_OPERATION_REMARK_ERROR_MSG;
			}
		}
		return null;
	}

	@Override
	public String validateOperationDate(String date) {
		if(!oneParameterNullEmptyCheck(date)) {
			if(!date.matches(OPERATION_DATE_PATTERN)) {
				return WRONG_OPERATION_DATE_ERROR_MSG;
			}
		}
		return null;
	}

	@Override
	public String validateOperationAmount(String amount) {
		
		if(oneParameterNullEmptyCheck(amount)) {
			return EMPTY_OPERATION_AMOUNT_ERROR_MSG;
		} else {
			if(!amount.matches(OPERATION_AMOUNT_PATTERN)) {
				return WRONG_OPERATION_AMOUNT_ERROR_MSG;
			}
		}
		return null;
	}

	@Override
	public List<String> validateOperationParams(String remark, String date, String amount) {
		List<String> errorMsgs = new ArrayList<String>();
		String errorMsgRemark = validateOperationRemark(remark);
		String errorMsgDate = validateOperationDate(date);
		String errorMsgAmount = validateOperationAmount(amount);
		
		if(errorMsgRemark != null) {
			errorMsgs.add(errorMsgRemark);
		}
		
		if(errorMsgDate != null) {
			errorMsgs.add(errorMsgDate);
		}
		
		if(errorMsgAmount != null) {
			errorMsgs.add(errorMsgAmount);
		}
		
		return errorMsgs;
	}

}
