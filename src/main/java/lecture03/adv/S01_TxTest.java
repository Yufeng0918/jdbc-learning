package lecture03.adv;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lecture01.basic.JdbcUtils;

import org.junit.Test;

/**
 * 
 * 2008-12-7
 * 
 * @author <a href="mailto:liyongibm@gmail.com">liyong</a>
 * 
 */
public class S01_TxTest {

	
	@Test
	public void testTx() throws SQLException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			st = conn.createStatement();
			String sql = "update user set money=money-10 where id=1";
			st.executeUpdate(sql);

			sql = "select money from user where id=2";
			rs = st.executeQuery(sql);
			float money = 0.0f;
			if (rs.next()) {
				money = rs.getFloat("money");
			}
			if (money > 400)
				throw new RuntimeException("Exceeded Maximum Amount");
			sql = "update user set money=money+10 where id=2";
			st.executeUpdate(sql);
			conn.commit();
		} catch (SQLException e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}
}
