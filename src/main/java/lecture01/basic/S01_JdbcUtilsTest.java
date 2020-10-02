package lecture01.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


public class S01_JdbcUtilsTest {

	@Test
	public void testJdbcUtils() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();

			rs = st.executeQuery("select * from user");

			while (rs.next()) {
				System.out.println(rs.getObject(1) + "\t" + rs.getObject(2)
						+ "\t" + rs.getObject(3) + "\t" + rs.getObject(4));
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}

	}

	@Test
	public void testNativeJdbc() throws SQLException, ClassNotFoundException {
		// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/jdbc";
		String user = "root";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, user, password);

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("select * from user");

		while (rs.next()) {
			System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t"
					+ rs.getObject(3) + "\t" + rs.getObject(4));
		}

		rs.close();
		st.close();
		conn.close();
	}
}
