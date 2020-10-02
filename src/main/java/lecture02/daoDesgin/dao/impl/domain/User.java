package lecture02.daoDesgin.dao.impl.domain;

import java.util.Date;

public class User {

	private int id;
	private String name;
	private Date birthday;
	private float money;

	public User() {

	}

	public User(String name) {
		this.name = name;
	}

	public User(float money) {
		this.money = money;
	}

	public void showName() {
		System.out.println(this.name);
	}

	@Override
	public String toString() {
		return "id=" + this.id + " name=" + this.name + " birthday="
				+ this.birthday + " money=" + this.money;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}
}
