package lecture05.daoReflector.dao.refactor;

import java.sql.ResultSet;
import java.sql.SQLException;

import lecture02.daoDesgin.dao.impl.domain.Account;

	public class AccountDaoImpl extends AbstractDao {
		
	public Account findAccount(int id) {
		String sql = "select id, name, money from account where id=?";
		Object[] args = new Object[] { id };
		Object account = super.find(sql, args);
		return (Account) account;
	}

	@Override
	protected Object rowMapper(ResultSet rs) throws SQLException {
		Account a = new Account();
		a.setId(rs.getInt("id"));
		return a;
	}

}
