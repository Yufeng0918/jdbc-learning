package lecture05.daoReflector.dao.refactor;

import java.sql.ResultSet;
import java.sql.SQLException;

import lecture02.daoDesgin.dao.impl.domain.User;


public class UserDaoImplAdv {
	
	DaoTemplate template = new DaoTemplate();

	public User findUser(String loginName, String password) {
		String sql = "select id, name, money, birthday  from user where name=?";
		Object[] args = new Object[] { loginName };
		RowMapper mapper = new UserRowMapper();
		Object user = this.template.find(sql, args, mapper);
		return (User) user;
	}

	public String findUserName(int id) {
		String sql = "select name from user where id=?";
		Object[] args = new Object[] { id };
		Object name = this.template.find(sql, args, new RowMapper() {

			public Object mapRow(ResultSet rs) throws SQLException {
				return rs.getString("name");
			}
		});
		return (String) name;
	}
}

class UserRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setMoney(rs.getFloat("money"));
		user.setBirthday(rs.getDate("birthday"));
		return user;
	}
}
