package by.htp.accountant.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OperationService {

	public void createOperation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
