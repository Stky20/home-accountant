package by.htp.accountant.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.accountant.controller.Command;
import by.htp.accountant.service.OperationTypeService;
import by.htp.accountant.service.ServiceFactory;

public class EditTypeCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		OperationTypeService service = ServiceFactory.getInstance().getOperationTypeService();
		service.editOperationType(request, response);
		
	}

}
