package lecture03.adv;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import lecture01.basic.JdbcUtils;

import org.junit.Test;

public class S03_CallableTest {


	@Test
	public void testCallable() throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "{ call addUser(?,?,?,?) } ";
			cs = conn.prepareCall(sql);
			cs.setString(1, "ps name");
			cs.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			cs.setFloat(3, 100f);
			cs.registerOutParameter(4, Types.INTEGER);

			cs.executeUpdate();
			int id = cs.getInt(4);
			System.out.println("id=" + id);
		} finally {
			JdbcUtils.free(rs, cs, conn);
		}
	}
}
