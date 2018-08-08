package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.accountant.bean.User;
import by.htp.accountant.bean.UserBuilder;
import by.htp.accountant.controller.Command;
import by.htp.accountant.exception.PasswordClassBeanException;


public class Logination implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = null;
		
		try {
			user = new UserBuilder().buildNickName(request.getParameter("login")).buildHashFromPassword(request.getParameter("password")).buildUser();
		} catch (PasswordClassBeanException e) {
			e.printStackTrace();
		}
		request.getSession(true).setAttribute("user", user);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPath.MAIN_PAGE);
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {			
			throw e;
		} catch (IOException e) {			
			throw e;
		}	
		
	}

}
