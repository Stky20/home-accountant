package by.htp.homeaccountant.test.servise;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import by.htp.accountant.dao.impl.MySQLUserDAO;
import by.htp.accountant.service.impl.UserServiceImpl;

public class UserServiceImplTest {

	UserServiceImpl userService = new UserServiceImpl();
	MySQLUserDAO userDAO = Mockito.mock(MySQLUserDAO.class);
	
	 @Test
	  public void successfulFlow() throws IOException, ServletException {
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
	    userService.diactivateUser(request, response);
	    
	  }
}
