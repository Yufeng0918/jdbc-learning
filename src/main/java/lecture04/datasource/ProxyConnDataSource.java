package lecture04.datasource;

import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class ProxyConnDataSource implements DataSource {

	private static String url = "jdbc:mysql://localhost:3306/jdbc";
	private static String user = "root";
	private static String password = "root";

	private static int initCount = 1;
	private static int maxCount = 1;
	int currentCount = 0;

	LinkedList<Connection> connectionsPool = new LinkedList<Connection>();

	public ProxyConnDataSource() {
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

			throw new SQLException("Exception");
		}
	}

	public void free(Connection conn) {
		this.connectionsPool.addLast(conn);
	}

	private Connection createConnection() throws SQLException {
		Connection realConn = DriverManager.getConnection(url, user, password);
		ProxyConnectionHandler proxyConnHandler = new ProxyConnectionHandler(
				this, realConn);
		return (Connection) Proxy.newProxyInstance(this.getClass()
				.getClassLoader(), new Class[] { Connection.class },
				proxyConnHandler);
	}

	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
