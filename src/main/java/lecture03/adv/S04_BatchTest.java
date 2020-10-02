package lecture03.adv;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lecture01.basic.JdbcUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class S04_BatchTest {
	
	long start = System.currentTimeMillis();
	long end = System.currentTimeMillis();
	
	
	@Before
	public void startTiming(){
		start = System.currentTimeMillis();
		
	}
	
	@After
	public void endTiming(){
		end = System.currentTimeMillis();
		System.out.println("Duration:" + (end - start));
	}


	@Test
	public void createBatch() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into user(name,birthday, money) values (?, ?, ?) ";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < 100; i++) {
				ps.setString(1, "batch name" + i);
				ps.setDate(2, new Date(System.currentTimeMillis()));
				ps.setFloat(3, 100f + i);

				ps.addBatch();
			}
			int[] is = ps.executeBatch();
			System.out.println(is);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
