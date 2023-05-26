package Methods;


import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

			



public class Listner implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext sc=sce.getServletContext();
    	DataBase baseUtil=new DataBase();
    	sc.setAttribute("sqlEmployee", baseUtil);
    	System.out.println("Connection Started");
    	     		
    }
    public void contextDestroyed(ServletContextEvent sce)  { 
    	  try {
			DataBase.connection.close();
            System.out.println("DataBase Connection is closed ");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        System.out.println("Unable to close the connection");

		}
          
      


   }
	
}