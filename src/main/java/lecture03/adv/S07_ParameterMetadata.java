package lecture03.adv;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lecture01.basic.JdbcUtils;

import org.junit.Test;

public class S07_ParameterMetadata {


	@Test
	public void setParams() throws SQLException {
		Object[] params = new Object[] { "NUS", 80f };
		String sql = "select * from user where name=? and  money > ?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			ParameterMetaData pmd = ps.getParameterMetaData();
			System.out.println(pmd);
			int count = pmd.getParameterCount();
			System.out.println(count + " " + params.length);
			for (int i = 1; i <= params.length; i++) {
				ps.setObject(i, params[i - 1]);
				
//				System.out.print(pmd.getParameterClassName(i) + "\t");
//				System.out.print(pmd.getParameterType(i) + "\t");
//				System.out.println(pmd.getParameterTypeName(i)+"\t");
			}

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

}
