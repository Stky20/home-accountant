package by.htp.accountant.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OperationService {

	void createOperation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void deleteOperation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void editOperation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
}
