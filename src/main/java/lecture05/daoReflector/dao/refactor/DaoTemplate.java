package lecture05.daoReflector.dao.refactor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lecture04.datasource.JdbcUtils;
import lecture05.daoReflector.dao.DaoException;


public class DaoTemplate {

	public Object find(String sql, Object[] args, RowMapper rowMapper) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++)
				ps.setObject(i + 1, args[i]);
			rs = ps.executeQuery();
			Object obj = null;
			if (rs.next()) {
				obj = rowMapper.mapRow(rs);
			}
			return obj;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}
}
