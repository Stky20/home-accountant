package by.htp.accountant.listener;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.htp.accountant.dao.connectionpool.ConnectionPool;
import by.htp.accountant.service.impl.UserServiceImpl;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
public class ContextListener implements ServletContextListener {	

	private final static Logger logger = LoggerFactory.getLogger(ContextListener.class);
    /**
     * Default constructor. 
     */
    public ContextListener() {
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
        
        ConnectionPool pool = ConnectionPool.getInstance();
         try {
			pool.dispose();			
		} catch (SQLException e) {			
			sce.getServletContext().log("ConnectionPool wasn`t dispose beacourse of exception", e);	
			logger.warn("ConnectionPool wasn`t dispose beacourse of exception", e);
		}         
         
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {   
    	ConnectionPool pool = ConnectionPool.getInstance();    	
    }
	
}
