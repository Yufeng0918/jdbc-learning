package lecture02.daoDesgin.dao.impl.service;

import lecture02.daoDesgin.dao.UserDao;
import lecture02.daoDesgin.dao.impl.domain.User;


public class UserService {
	private UserDao userDao;

	public void regist(User user) {
		this.userDao.addUser(user);
		// sendMail.send(user);
	}
}
