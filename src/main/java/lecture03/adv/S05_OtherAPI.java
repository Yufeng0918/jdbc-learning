package lecture03.adv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lecture01.basic.JdbcUtils;

import org.junit.Test;

public class S05_OtherAPI {


	@Test
	public void testRollableResultSet() throws SQLException, InterruptedException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			rs = st
					.executeQuery("select id, name, money, birthday  from user where id < 5");

			while (rs.next()) {
				int id = rs.getInt("id");
				System.out.println("show " + id + "...");
				Thread.sleep(10000);
				System.out.println(id + "\t" + rs.getObject("name") + "\t"
						+ rs.getObject("birthday") + "\t"
						+ rs.getObject("money"));
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	@Test
	public void createGenerationKey() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user(name,birthday, money) values ('name2 gk', '1987-01-01', 400) ";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			int id = 0;
			if (rs.next())
				id = rs.getInt(1);
			
			System.out.println("Id:\t" + id);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
