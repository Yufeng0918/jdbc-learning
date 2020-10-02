package lecture04.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public final class JdbcUtils {

	// private static String url = "jdbc:mysql://localhost:3306/jdbc";
	// private static String user = "root";
	// private static String password = "root";
	private static DataSource dataSource;

	private JdbcUtils() {
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Properties prop = new Properties();
//			InputStream is = JdbcUtils.class.getClassLoader()
//					.getResourceAsStream("dbcpconfig.properties");
			File dbConfig = new File("dbcpconfig.properties");
			System.out.println(dbConfig.getAbsoluteFile());
			InputStream is = new FileInputStream(dbConfig);
			prop.load(is);
			dataSource = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
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
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}
}
