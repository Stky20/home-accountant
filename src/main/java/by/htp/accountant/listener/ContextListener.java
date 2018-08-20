package by.htp.accountant.listener;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.htp.accountant.dao.connectionpool.ConnectionPool;

/**
 * Application Lifecycle Listener implementation class ContestListener
 *
 */
public class ContextListener implements ServletContextListener {	

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
			sce.getServletContext().log("ConnectionPool closed");
		} catch (SQLException e) {			
			sce.getServletContext().log("ConnectionPool couldnt dispose beacourse of exception", e);			
		}         
         
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {          
    }
	
}
