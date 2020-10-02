package lecture02.daoDesgin.dao;

import lecture02.daoDesgin.dao.impl.domain.User;


public interface UserDao {
	public void addUser(User user);

	public User getUser(int userId);

	public User findUser(String loginName, String password);

	public void update(User user);

	public void delete(User user);

}
