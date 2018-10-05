package by.htp.accountant.controller;

import java.util.HashMap;
import java.util.Map;

import by.htp.accountant.controller.command.ChangeLoginCommand;
import by.htp.accountant.controller.command.ChangePasswordCommand;
import by.htp.accountant.controller.command.ChangeUserInfoCommand;
import by.htp.accountant.controller.command.CreateOperationCommand;
import by.htp.accountant.controller.command.DeleteUserCommand;
import by.htp.accountant.controller.command.DiactivateUserCommand;
import by.htp.accountant.controller.command.GoToAboutUsPageCommand;
import by.htp.accountant.controller.command.GoToContactsPageCommand;
import by.htp.accountant.controller.command.GoToLoginPageCommand;
import by.htp.accountant.controller.command.GoToMainPageCommand;
import by.htp.accountant.controller.command.GoToOperationFormCommand;
import by.htp.accountant.controller.command.GoToProfile;
import by.htp.accountant.controller.command.GoToRegistrationPageCommand;
import by.htp.accountant.controller.command.GoToSloganPageCommand;
import by.htp.accountant.controller.command.GoToSorryPageCommand;
import by.htp.accountant.controller.command.GoToUserAccountPageCommand;
import by.htp.accountant.controller.command.GoToUserAdministrationPageCommand;
import by.htp.accountant.controller.command.ChangeLocalization;
import by.htp.accountant.controller.command.Logination;
import by.htp.accountant.controller.command.MakeAdminCommand;
import by.htp.accountant.controller.command.Registration;
import by.htp.accountant.controller.command.RestoreUserCommand;
import by.htp.accountant.controller.command.SignOut;

public class CommandProvider {
	
private Map<CommandName, Command> commands = new HashMap<CommandName, Command>();
	
	public CommandProvider() {
		commands.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
		commands.put(CommandName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
		commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
		commands.put(CommandName.CHANGE_LOCALIZATION, new ChangeLocalization());
		commands.put(CommandName.LOGINATION, new Logination());
		commands.put(CommandName.REGISTRATION, new Registration());
		commands.put(CommandName.SIGN_OUT, new SignOut());
		commands.put(CommandName.GO_TO_PROFILE, new GoToProfile());
		commands.put(CommandName.GO_TO_CONTACTS_PAGE, new GoToContactsPageCommand());
		commands.put(CommandName.GO_TO_ABOUT_US_PAGE, new GoToAboutUsPageCommand());
		commands.put(CommandName.GO_TO_SLOGAN_PAGE, new GoToSloganPageCommand());
		commands.put(CommandName.GO_TO_USER_ADMINISTRATION_PAGE, new GoToUserAdministrationPageCommand());
		commands.put(CommandName.GO_TO_SORRY_PAGE, new GoToSorryPageCommand());
		commands.put(CommandName.RESTORE_USER, new RestoreUserCommand());
		commands.put(CommandName.DIACTIVATE_USER, new DiactivateUserCommand());
		commands.put(CommandName.CHANGE_LOGIN, new ChangeLoginCommand());
		commands.put(CommandName.CHANGE_PASSWORD, new ChangePasswordCommand());
		commands.put(CommandName.CHANGE_USER_INFO, new ChangeUserInfoCommand());
		commands.put(CommandName.MAKE_ADMIN, new MakeAdminCommand());
		commands.put(CommandName.DELETE_USER, new DeleteUserCommand());
		commands.put(CommandName.GO_TO_USER_ACCOUNT_PAGE, new GoToUserAccountPageCommand());
		commands.put(CommandName.GO_TO_OPERATION_FORM, new GoToOperationFormCommand());
		commands.put(CommandName.CREATE_OPERATION, new CreateOperationCommand());
	}

	public Command getCommand(String commandName) {
		if(commandName != null) {			
				Command command = commands.get(CommandName.getCommand(commandName.toUpperCase()));
				return command;			
		} else {
			return null;
		}
	}



}
