package by.htp.accountant.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UtilService {
	
	public void changeLocalization(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public void goToAboutUsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToContactsPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToMainPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToOperationForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToProfilePage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToRegistrationPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToSloganPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToSorryPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void goToUserAccountPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
