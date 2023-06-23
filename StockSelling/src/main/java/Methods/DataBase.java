package Methods;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DataBase {
	static Logger log = Logger.getLogger("DataBase");     
	static Connection connection;

	public Connection getConnector() {
		try {
			connection=Listner.ds.getConnection();
			log.info("getting connection for Servlet");
		} catch (SQLException e) {
			log.error(e.getMessage()+" Error in databaseutil");
		}
		return connection;
	}

}