package lecture01.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


public class S03_PrepareStatmentAndSQLInjection {

	@Test
	public void testPrepareStatement() throws SQLException {
		
		String name = "SMU";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "select id, name, money, birthday  from user where name=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t"
						+ rs.getString("name") + "\t" + rs.getDate("birthday")
						+ "\t" + rs.getFloat("money"));
			}

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	@Test
	public void testStatement() throws SQLException {
		String name = "SMU";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();

			String sql = "select id, name, money, birthday  from user where name='"
					+ name + "'";
			st = conn.createStatement();
			rs = st.executeQuery(sql);

			while (rs.next()) {
				// System.out.println(rs.getObject("id") + "\t"
				// + rs.getObject("name") + "\t"
				// + rs.getObject("birthday") + "\t"
				// + rs.getObject("money"));
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

}
