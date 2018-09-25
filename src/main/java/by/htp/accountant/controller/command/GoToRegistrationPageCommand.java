package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.ServiceFactory;
import by.htp.accountant.service.UtilService;

public class GoToRegistrationPageCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UtilService service = ServiceFactory.getInstance().getUtilService();			
		service.goToRegistrationPage(request, response);		
		
	}

}
