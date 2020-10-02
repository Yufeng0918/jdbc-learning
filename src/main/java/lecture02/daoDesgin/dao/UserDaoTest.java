package lecture02.daoDesgin.dao;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import lecture02.daoDesgin.dao.impl.domain.User;

public class UserDaoTest {

	static UserDao userDao;

	@BeforeClass
	public static void init() {
		userDao = (UserDao) DaoFactory.getInstance().getUserDao();
	}

	@Test
	public void testAddUser() {
		User user = new User();
		user.setBirthday(new Date());
		user.setName("dao name2");
		user.setMoney(1000.0f);
		
		userDao.addUser(user);
		System.out.println(user.getId());
	}
	
	
	@Test
	public void testFindUser(){
		 User user = userDao.findUser("NUS", null);
		 System.out.println(user);
		
	}
	
	
	@Test
	public void testUpdateUser(){
		 User user = userDao.getUser(5);
		 user.setMoney(20000.1f);
		 userDao.update(user);
	}
	
	
	@Test
	public void testDeleteUser(){
		 User user = userDao.getUser(6);
		 userDao.delete(user);
	}

}
