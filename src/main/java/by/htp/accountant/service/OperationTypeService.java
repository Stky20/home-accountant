package by.htp.accountant.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OperationTypeService {
	
	void createOperationType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	void editOperationType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	void deleteOperationType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}
