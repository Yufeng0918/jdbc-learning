package lecture01.basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


public class S02_JdbcCRUD {

	/**
	 * @param args
	 * @throws SQLException
	 */

	@Test
	public void testDelete() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			st = conn.createStatement();

			String sql = "delete from user where id>4";
			int i = st.executeUpdate(sql);

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	@Test
	public void testUpdate() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();

			String sql = "update user set money=money+10 ";

			int i = st.executeUpdate(sql);

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	@Test
	public void testCreate() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();

			String sql = "insert into user(name,birthday, money) values ('SMU', '1987-01-01', 400) ";

			int i = st.executeUpdate(sql);

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	@Test
	public void testRead() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection();
			st = conn.createStatement();

			rs = st.executeQuery("select id, name, money, birthday  from user");

			while (rs.next()) {
				System.out.println(rs.getObject("id") + "\t"
						+ rs.getObject("name") + "\t"
						+ rs.getObject("birthday") + "\t"
						+ rs.getObject("money"));
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

}
