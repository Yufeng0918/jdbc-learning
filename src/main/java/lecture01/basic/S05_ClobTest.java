package lecture01.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;


public class S05_ClobTest {

	@Test
	public void readClob() throws SQLException, IOException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select big_text  from clob_test");

			while (rs.next()) {
				Clob clob = rs.getClob(1);
				Reader reader = clob.getCharacterStream();
				// reader = rs.getCharacterStream(1);
				// String s = rs.getString(1);

				File file = new File("JdbUtils_bak.java");
				Writer writer = new BufferedWriter(new FileWriter(file));
				char[] buff = new char[1024];
				for (int i = 0; (i = reader.read(buff)) > 0;) {
					writer.write(buff, 0, i);
				}
				writer.close();
				reader.close();
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	@Test
	public void createClob() throws SQLException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into clob_test(big_text) values (?) ";
			ps = conn.prepareStatement(sql);
			File file = new File("src/lecture01/basic/JdbcUtils.java");
			Reader reader = new BufferedReader(new FileReader(file));

			ps.setCharacterStream(1, reader, (int) file.length());
			int i = ps.executeUpdate();

			reader.close();

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

}
