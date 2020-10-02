package lecture06.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import lecture02.daoDesgin.dao.impl.domain.User;
import lecture04.datasource.JdbcUtils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class S01_JdbcTemplateTest {

	JdbcTemplate jdbc;
	
	@Before
	public void init() {
		jdbc = new JdbcTemplate(JdbcUtils.getDataSource());
	}
	
	@Test
	public void testAddUser() {
		
		final User user = new User();
		user.setName("user");
		user.setBirthday(new Date());
		user.setMoney(200f);
		
		jdbc.execute(new ConnectionCallback<Object>() {

			public Object doInConnection(Connection con) throws SQLException,
					DataAccessException {
				String sql = "insert into user(name,birthday, money) values (?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getName());
				ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
				ps.setFloat(3, user.getMoney());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next())
					user.setId(rs.getInt(1));
				return null;
			}
		});
	}

	@Test
	public void testGetMapById() {
		int id = 1;
		String sql = "select id as userId, name, money, birthday  from user where id="
				+ id;
		System.out.println(jdbc.queryForMap(sql));
	}

	
	@Test
	public void testGetUserName() {
		
		int id = 1;
		String sql = "select name from user where id=" + id;
		Object name = jdbc.queryForObject(sql, String.class);
		System.out.println(name);
	}

	
	@Test
	public void testGetUserCount() {
		String sql = "select count(*) from user";
		System.out.println(jdbc.queryForInt(sql));
	}

	
	@Test
	public void testFindUsers() {
		
		int id = 100;
		String sql = "select id, name, money, birthday  from user where id<?";
		Object[] args = new Object[] { id };
		int[] argTypes = new int[] { Types.INTEGER };
		List<User> users = jdbc.query(sql, args, argTypes,
				new BeanPropertyRowMapper<User>(User.class));
		System.out.println(users);
	}

	@Test
	public void testFindUser() {
		
		String name = "NUS";
		String sql = "select id, name, money, birthday  from user where name=?";
		Object[] args = new Object[] { name };
		Object user = jdbc.queryForObject(sql, args,
				new BeanPropertyRowMapper<User>(User.class));
		System.out.println((User) user);
	}

	@Test
	public void findUserWithRowMapper() {
		
		String name = "NUS";
		String sql = "select id, name, money, birthday  from user where name=?";
		Object[] args = new Object[] { name };
		Object user = jdbc.queryForObject(sql, args, new RowMapper<Object>() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setMoney(rs.getFloat("money"));
				user.setBirthday(rs.getDate("birthday"));
				return user;
			}
		});
		System.out.println((User) user);
	}
}
