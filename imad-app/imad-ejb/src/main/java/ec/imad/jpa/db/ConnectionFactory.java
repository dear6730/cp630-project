package ec.imad.jpa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jboss.logging.Logger;

public class ConnectionFactory {

	private static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());
	private static ConnectionFactory connectionFactory = null;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	private static final String USER = "root";
	private static final String PASS = "";

	private ConnectionFactory() {}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			logger.info("Creating ConnectionFactory");
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}

	public Connection getConnection() throws SQLException {
		Connection connection = null;
		logger.info("Getting database connection");
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				logger.info("Closing database connection");
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
