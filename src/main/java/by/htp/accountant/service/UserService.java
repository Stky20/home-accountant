package by.htp.accountant.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UserService {	
	
	
	/**
	 * Method makes logination
	 * @param HttpServletRequest 
	 * @param HttpServletResponse
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void logination(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void registration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void showUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void restoreUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void diactivateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void makeAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void changeLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void changeUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}
