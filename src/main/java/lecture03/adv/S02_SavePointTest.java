package lecture03.adv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import lecture01.basic.JdbcUtils;

import org.junit.Test;

public class S02_SavePointTest {


	@Test
	public void testSavePoint() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Savepoint sp = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);			
			st = conn.createStatement();
			String sql = "update user set money=money-10 where id=1";
			st.executeUpdate(sql);
			sp = conn.setSavepoint();

			sql = "update user set money=money-10 where id=3";
			st.executeUpdate(sql);

			sql = "select money from user where id=2";
			rs = st.executeQuery(sql);
			float money = 0.0f;
			if (rs.next()) {
				money = rs.getFloat("money");
			}
			if (money > 300)
				throw new RuntimeException("Exceeded Maximum Amount");

			sql = "update user set money=money+10 where id=2";
			st.executeUpdate(sql);

			conn.commit();
		} catch (RuntimeException e) {
			if (conn != null && sp != null) {
				conn.rollback(sp);
				conn.commit();
			}
			throw e;
		} catch (SQLException e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}
}
