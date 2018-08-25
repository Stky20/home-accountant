package by.htp.accountant.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.accountant.controller.command.GoToAboutUsPageCommand;
import by.htp.accountant.controller.command.GoToContactsPageCommand;
import by.htp.accountant.controller.command.GoToLoginPageCommand;
import by.htp.accountant.controller.command.GoToMainPageCommand;
import by.htp.accountant.controller.command.GoToProfile;
import by.htp.accountant.controller.command.GoToRegistrationPageCommand;
import by.htp.accountant.controller.command.GoToSloganPageCommand;
import by.htp.accountant.controller.command.GoToUserAdministrationPageCommand;
import by.htp.accountant.controller.command.Localization;
import by.htp.accountant.controller.command.Logination;
import by.htp.accountant.controller.command.Registration;
import by.htp.accountant.controller.command.SignOut;

public class CommandProvider {
	
private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
	
	public CommandProvider() {
		commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
		commands.put(CommandName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.LOCALIZATION, new Localization());
		commands.put(CommandName.LOGINATION, new Logination());
		commands.put(CommandName.REGISTRATION, new Registration());
		commands.put(CommandName.SIGN_OUT, new SignOut());
		commands.put(CommandName.GO_TO_PROFILE, new GoToProfile());
		commands.put(CommandName.GO_TO_CONTACTS_PAGE, new GoToContactsPageCommand());
		commands.put(CommandName.GO_TO_ABOUT_US_PAGE, new GoToAboutUsPageCommand());
		commands.put(CommandName.GO_TO_SLOGAN_PAGE, new GoToSloganPageCommand());
		commands.put(CommandName.GO_TO_USER_ADMINISTRATION_PAGE, new GoToUserAdministrationPageCommand());
	}

	public Command getCommand(String commandName) {
		Command command = commands.get(CommandName.valueOf(commandName.toUpperCase()));
		return command;
	}



}
