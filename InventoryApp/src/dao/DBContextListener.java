package dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.junit.After;
import org.junit.Before;

import dao.impl.MasterEntityDAO;
import dao.interf.IMasterEntityDAO;

/*
 * This class was referenced from the following website
 * 
 * https://www.mkyong.com/servlet/what-is-listener-servletcontextlistener-example
 * 
 * Creates and monitors an instance of the database connection
 * so the the application has permission to perform database
 * transactions
*/

@WebListener
public class DBContextListener implements ServletContextListener {
	
	private static EntityManagerFactory emf  = null;
	private static IMasterEntityDAO med = null;
	
	
	// Initializes the database connection by grabbing
	// the properties from persistence.xml
	@Before
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		emf = Persistence.createEntityManagerFactory("Inventory_AppPU");
		med = createMasterDAO();
		System.out.println("-------> ServletContextListener started");		
	}
	
	// Removes the database connection by when
	// the application is restarted or stoppped
	@After
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
		System.out.println("-------> ServletContextListener destroyed");
		
	}
	
	// Makes sure that only one instance is the Entity Manager
	// is running
    private static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        EntityManager em = emf.createEntityManager();
        return em;
    }
    
    
    private static IMasterEntityDAO createMasterDAO() {
        if (!(med instanceof IMasterEntityDAO)) {
        	med = new MasterEntityDAO();
        }
        return med;
    }
    
    // Get access to the created Entity Manager
    public static EntityManager getEntityManager() {
      return createEntityManager();
    }
    
    public static IMasterEntityDAO getMasterDAO() {
        return createMasterDAO();
      }
}
