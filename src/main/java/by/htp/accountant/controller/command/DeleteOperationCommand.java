package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.OperationService;
import by.htp.accountant.service.ServiceFactory;

public class DeleteOperationCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OperationService service = ServiceFactory.getInstance().getOperationService();
		service.deleteOperation(request, response);
		
	}

}
