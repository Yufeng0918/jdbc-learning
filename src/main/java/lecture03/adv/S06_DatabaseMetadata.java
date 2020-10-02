package lecture03.adv;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import lecture01.basic.JdbcUtils;

import org.junit.Test;

/**
 * 
 * 2008-12-7
 * 
 * @author <a href="mailto:liyongibm@gmail.com">liyong</a>
 * 
 */
public class S06_DatabaseMetadata {

	/**
	 * @param args
	 * @throws SQLException
	 */
	
	@Test
	public void getDBMetadata() throws SQLException {
		java.sql.Connection conn = JdbcUtils.getConnection();
		DatabaseMetaData dbmd = conn.getMetaData();
		System.out.println("db name: " + dbmd.getDatabaseProductName());
		System.out.println("tx: " + dbmd.supportsTransactions());
		conn.close();
	}

}
