package lecture01.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtilsSingleton {

	private String url = "jdbc:mysql://localhost:3306/jdbc";
	private String user = "root";
	private String password = "";

	// private static JdbcUtilsSing instance = new JdbcUtilsSing();
	private static JdbcUtilsSingleton instance = null;

	private JdbcUtilsSingleton() {
	}

	public static JdbcUtilsSingleton getInstance() {
		if (instance == null) {
			synchronized (JdbcUtilsSingleton.class) {
				if (instance == null) {
					instance = new JdbcUtilsSingleton();
				}
			}
		}
		return instance;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public void free(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}
}
