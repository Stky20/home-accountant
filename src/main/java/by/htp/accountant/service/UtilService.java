package by.htp.accountant.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UtilService {
	
	void changeLocalization(HttpServletRequest request, HttpServletResponse response) throws IOException;
	void goToAboutUsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToContactsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToOperationForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToProfilePage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToSloganPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToSorryPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToUserAccountPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToUserOperationsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	void goToResourceNotReadyPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	
}
