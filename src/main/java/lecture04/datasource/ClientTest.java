package lecture04.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class ClientTest {

	Connection conn;

	@Before
	public void initConn() throws SQLException {
		conn = JdbcUtils.getConnection();
	}

	@Test
	public void testQuery() throws Exception {
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
}
