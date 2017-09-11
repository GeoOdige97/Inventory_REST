package controller;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.junit.After;
import org.junit.Before;

/*
 * This class was referenced from the following website
 * 
 * https://www.mkyong.com/servlet/what-is-listener-servletcontextlistener-example

*/

@WebListener
public class ServletListener implements ServletContextListener {
	
	private static EntityManagerFactory emf  = null;
	
	
	@Before
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		emf = Persistence.createEntityManagerFactory("Inventory_AppPU");
		System.out.println("-------> ServletContextListener started");		
	}
	
	
	@After
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
		System.out.println("-------> ServletContextListener destroyed");
		
	}
	
    private static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return emf.createEntityManager();
    }
    
    public static EntityManager getEntityManager() {
      return createEntityManager();
    }
}
