package lecture05.daoReflector.dao;

import java.io.InputStream;
import java.util.Properties;

import lecture02.daoDesgin.dao.UserDao;

public class DaoFactory {
	private static UserDao userDao;
	private static DaoFactory instance = new DaoFactory();

	private DaoFactory() {
		try {
			Properties prop = new Properties();
			InputStream inStream = DaoFactory.class.getClassLoader()
					.getResourceAsStream("daoconfig.properties");
			prop.load(inStream);
			String userDaoClass = prop.getProperty(UserDao.class.getSimpleName());
			Class<?> clazz = Class.forName(userDaoClass);
			userDao = (UserDao) clazz.newInstance();
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DaoFactory getInstance() {
		return instance;
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
