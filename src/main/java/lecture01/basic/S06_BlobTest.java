package lecture01.basic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class S06_BlobTest {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SQLException
	 */

	@Test
	public void readBlob() throws SQLException, IOException {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {

			conn = JdbcUtils.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select big_bit from blob_test");

			while (rs.next()) {
				Blob blob = rs.getBlob("big_bit");
				InputStream in = blob.getBinaryStream();
				// InputStream in = rs.getBinaryStream("big_bit");

				File file = new File("Image.bak.jpg");
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(file));
				byte[] buff = new byte[1024];
				for (int i = 0; (i = in.read(buff)) > 0;) {
					out.write(buff, 0, i);
				}
				out.close();
				in.close();
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}
	}

	@Test
	public void createBlob() throws SQLException, IOException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			String sql = "insert into blob_test(big_bit) values (?) ";
			ps = conn.prepareStatement(sql);
			File file = new File("Image.jpg");
			InputStream in = new BufferedInputStream(new FileInputStream(file));

			ps.setBinaryStream(1, in, (int) file.length());
			int i = ps.executeUpdate();

			in.close();

			System.out.println("i=" + i);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
