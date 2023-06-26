package Methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

//import org.apache.log4j.Logger;

public class DataBase {
//	static Logger log = Logger.getLogger("DataBase");     
	 Connection connection;
	 DataSource ds;

	public Connection getConnection() throws SQLException, ClassNotFoundException {
//		try {
//			connection=Listner.ds.getConnection();
//			log.info("getting connection for Servlet");
//		} catch (SQLException e) {
//			log.error(e.getMessage()+" Error in databaseutil");
//		}Class.forName("com.mysql.jdbc.Driver");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maxmin", "root", "Nitish@1314");
		
		return connection;
	}

}