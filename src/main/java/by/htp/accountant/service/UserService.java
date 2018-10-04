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
	public void authorizeUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void registrateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void showUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * This method can be caused by administrator and by deactivated user himself.
	 * Method sets default user`s role in database by user ID which came as parameter in request
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @throws IOException
	 * @throws ServletException
	 */
	public void restoreUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void diactivateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void makeAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void changeLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void changeUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}
