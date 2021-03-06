package by.htp.accountant.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.exception.NoSuchCommandException;


public class Controller extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String COMMAND_PARAM_NAME = "command";
	
	private static final CommandProvider commandProvider = new CommandProvider();
	
	private static final Logger logger = LoggerFactory.getLogger("Controller.class");
       
    
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String commandName = request.getParameter(COMMAND_PARAM_NAME);
		Command command = null;
		command = commandProvider.getCommand(commandName);		
		
		if(command != null) {
			command.execute(request, response);
		} else {
			logger.info("Wrong comandName or the command did not come from user. - " + commandName);
			throw new NoSuchCommandException();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
