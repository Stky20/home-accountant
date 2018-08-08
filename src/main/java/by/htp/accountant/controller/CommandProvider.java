package by.htp.accountant.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.accountant.controller.command.GoToLoginPageCommand;
import by.htp.accountant.controller.command.GoToMainPageCommand;
import by.htp.accountant.controller.command.GoToRegistrationPageCommand;
import by.htp.accountant.controller.command.Localization;
import by.htp.accountant.controller.command.Logination;

public class CommandProvider {
	
private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
	
	public CommandProvider() {
		commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
		commands.put(CommandName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.LOCALIZATION, new Localization());
		commands.put(CommandName.LOGINATION, new Logination());
//		commands.put(ComandName.);
//		commands.put(ComandName.);
//		commands.put(ComandName.);
	}

	public Command getCommand(String commandName) {
		Command command = commands.get(CommandName.valueOf(commandName.toUpperCase()));
		return command;
	}



}
