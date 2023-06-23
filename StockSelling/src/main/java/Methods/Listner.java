package Methods;


import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class Listner implements ServletContextListener {
	static DataSource ds;
	static Logger log = Logger.getLogger("Listner");     
    public void contextInitialized(ServletContextEvent sce)  { 
    	
		try {			
			Context initContext = new InitialContext();
			ds=(DataSource) initContext.lookup("java:comp/env/jdbc/mydb");
			log.info("Server started  and Lookup has initailized");
		}catch (Exception e) {
			log.error("Unable to start lookup "+e.getMessage());
			e.printStackTrace();
		}
		ServletContext sc=sce.getServletContext();
    	DataBase baseUtil=new DataBase();
    	sc.setAttribute("DB_Connection", baseUtil);
    	     		
    }
    public void contextDestroyed(ServletContextEvent sce)  { 

    	  try {
			DataBase.connection.close();
            log.info("DataBase Connection is closed ");

		} catch (SQLException e) {
			e.printStackTrace();
	        log.warn("Unable to close the connection");

		}                
   }
	
}