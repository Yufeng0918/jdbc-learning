package lecture04.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionDataSource {
	private static String url = "jdbc:mysql://localhost:3306/jdbc";
	private static String user = "root";
	private static String password = "root";

	private static int initCount = 5;
	private static int maxCount = 10;
	private int currentCount = 0;

	LinkedList<Connection> connectionsPool = new LinkedList<Connection>();

	public ConnectionDataSource() {
		try {
			for (int i = 0; i < initCount; i++) {
				this.connectionsPool.addLast(this.createConnection());
				this.currentCount++;
			}
		} catch (SQLException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Connection getConnection() throws SQLException {
		synchronized (connectionsPool) {
			if (this.connectionsPool.size() > 0)
				return this.connectionsPool.removeFirst();

			if (this.currentCount < maxCount) {
				this.currentCount++;
				return this.createConnection();
			}

			throw new SQLException("Connection");
		}
	}

	public void free(Connection conn) {
		this.connectionsPool.addLast(conn);
	}

	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
}
