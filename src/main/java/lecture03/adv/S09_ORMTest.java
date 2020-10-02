package lecture03.adv;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lecture01.basic.JdbcUtils;
import lecture02.daoDesgin.dao.impl.domain.User;

import org.junit.Test;

/**
 * 
 * 2008-12-7
 * 
 * @author <a href="mailto:liyongibm@gmail.com">liyong</a>
 * 
 */
public class S09_ORMTest {

	@Test
	public void testGetUser() throws SQLException, IllegalAccessException,
			InvocationTargetException, Exception {
		User user = (User) getObject(
				"select id as Id, name as Name, birthday as Birthday, money as Money  from user where id=1",
				User.class);
		System.out.println(user);
	}

	public List<Object> getObjects(String sql, Class<?> clazz)
			throws SQLException, Exception, IllegalAccessException,
			InvocationTargetException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String[] colNames = getColNames(rs);

			List<Object> objects = new ArrayList<Object>();
			Method[] ms = clazz.getMethods();
			while (rs.next()) {
				Object object = clazz.newInstance();
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					String methodName = "set" + colName;
					for (Method m : ms) {
						if (methodName.equals(m.getName())) {
							m.invoke(object, rs.getObject(colName));
							break;
						}
					}
					objects.add(object);
				}
			}
			return objects;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	private String[] getColNames(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		String[] colNames = new String[count];
		for (int i = 1; i <= count; i++) {
			colNames[i - 1] = rsmd.getColumnLabel(i);
		}
		return colNames;
	}

	public Object getObject(String sql, Class<?> clazz) throws SQLException,
			Exception, IllegalAccessException, InvocationTargetException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String[] colNames = getColNames(rs);

			Object object = null;
			Method[] ms = clazz.getMethods();
			if (rs.next()) {
				object = clazz.newInstance();
				for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					String methodName = "set" + colName;
					for (Method m : ms) {
						if (methodName.equals(m.getName())) {
							m.invoke(object, rs.getObject(colName));
							break;
						}
					}
				}
			}
			return object;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
