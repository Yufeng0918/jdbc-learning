package lecture05.daoReflector.dao.impl;

import lecture02.daoDesgin.dao.impl.domain.User;
import lecture04.datasource.JdbcUtils;
import lecture05.daoReflector.dao.UserDao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class UserDaoSpringImpl implements UserDao {

	// private SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(
	// JdbcUtils.getDataSource());

	private NamedParameterJdbcTemplate simpleJdbcTemplate = new NamedParameterJdbcTemplate(
			JdbcUtils.getDataSource());

	public void addUser(User user) {
		String sql = "insert into user (name, money, birthday) values (:name, :money, :birthday)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.simpleJdbcTemplate.update(sql, param, keyHolder);
		user.setId(keyHolder.getKey().intValue());
	}

	public void delete(User user) {
		String sql = "delete from user where id=:id";
		this.simpleJdbcTemplate.update(sql,
				new MapSqlParameterSource().addValue("id", user.getId()));
	}

	public User findUser(String loginName, String password) {
		String sql = "select id, name, money, birthday  from user where name=:name";
		return this.simpleJdbcTemplate.queryForObject(sql,
				new MapSqlParameterSource().addValue("name", loginName),
				ParameterizedBeanPropertyRowMapper.newInstance(User.class));
	}

	public User getUser(int userId) {
		String sql = "select id, name, money, birthday  from user where id=:id";
		return this.simpleJdbcTemplate.queryForObject(sql,
				new MapSqlParameterSource().addValue("id", userId),
				ParameterizedBeanPropertyRowMapper.newInstance(User.class));
	}

	public void update(User user) {
		// String sql =
		// "update user set name=?, birthday=?, money=? where id=? ";
		// this.simpleJdbcTemplate.update(sql, user.getName(),
		// user.getBirthday(),
		// user.getMoney(), user.getId());

		String sql = "update user set name=:name, birthday=:birthday, money=:money where id=:id ";
		this.simpleJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(
				user));
	}

}
