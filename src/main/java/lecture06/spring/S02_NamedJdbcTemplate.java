package lecture06.spring;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lecture02.daoDesgin.dao.impl.domain.User;
import lecture04.datasource.JdbcUtils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class S02_NamedJdbcTemplate {
	
	NamedParameterJdbcTemplate named;
	
	@Before
	public void init() {
		named = new NamedParameterJdbcTemplate(JdbcUtils.getDataSource());
	}
	
	@Test
	public void testAddUser() {
		
		User user = new User();
		user.setName("user");
		user.setBirthday(new Date());
		user.setMoney(200f);
		
		String sql = "insert into user(name,birthday, money) values (:name,:birthday,:money) ";
		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		named.update(sql, ps, keyHolder);
		int id = keyHolder.getKey().intValue();
		user.setId(id);

		Map<String, Object> map = keyHolder.getKeys();
		System.out.println(map);
	}

	
	@Test
	public void findUser() {
		User user = new User();
		user.setName("user");
		user.setId(2);
		user.setBirthday(new Date());
		user.setMoney(20f);
		String sql = "select id, name, money, birthday  from user "
				+ "where money > :m and id < :id";
		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("n", user.getName());
		params.put("m", user.getMoney());
		params.put("id", user.getId());
		Object u = named.queryForObject(sql, params, new BeanPropertyRowMapper<User>(
				User.class));
		System.out.println(u);
	}

	@Test
	public void findUserWithSqlParam() {
		User user = new User();
		user.setName("user");
		user.setId(2);
		user.setBirthday(new Date());
		user.setMoney(20f);
		
		String sql = "select id, name, money, birthday  from user "
				+ "where money > :money and id < :id";
		SqlParameterSource ps = new BeanPropertySqlParameterSource(user);
		Object u = named.queryForObject(sql, ps, new BeanPropertyRowMapper<User>(
				User.class));
		System.out.println(u);
	}

}
