package lecture01.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.junit.Test;


public class S04_DateTest {

	@Test
	public void readDate() throws SQLException {
		
		int id = 4;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Date birthday = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select birthday  from user where id=" + id);
			while (rs.next()) {
				// birthday = new Date(rs.getDate("birthday").getTime());
				birthday = rs.getDate("birthday");
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}

		System.out.println(birthday);
	}

	@Test
	public void createDate() throws SQLException {

		String name = "SIM";
		Date birthday = new Date();
		float money = 0.0f;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user(name,birthday, money) values (?, ?, ?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setDate(2, new java.sql.Date(birthday.getTime()));
			ps.setFloat(3, money);

			
			int i = ps.executeUpdate();

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
