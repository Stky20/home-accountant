package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UserService;

public class RestoreUserCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		UserService userService = ServiceFactory.getInstance().getUserService();			
		userService.restoreUser(request, response);
		
	}
}
