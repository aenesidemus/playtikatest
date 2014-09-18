package com.burlaka.playtikatest.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.burlaka.playtikatest.model.User;

public class UserDao extends AbstractDao {

	public User getByLogin(String login) {
		if (login != null) {
			startOperation();
			Criteria criteria = session.createCriteria(User.class).add(
					Restrictions.like("login", login));
			User user = (User) criteria.uniqueResult();
			close();
			return user;
		} else
			return null;
	}

//	public String login(String login) {
//		User user = getByLogin(login);
//		if(user == null || user.getStatus().equals("o"))
//			return "success"
//			else 
//		return (user == null) ? "success" : "fail";
//	}

	public void saveOrUpdate(Object obj) {
		super.saveOrUpdate(obj);
	}

	public List<User> getUsers() {
		startOperation();
		List<User> list = session.createCriteria(User.class)
				.addOrder(Order.asc("login")).list();
		close();
		return list;
	}
	
	public void delete(User user) {
		super.delete(user);
	}

	public List<User> getUpdate(User user) {
		startOperation();
		List<User> list = session.createCriteria(User.class)
		.add(Restrictions.gt("ltime", user.getSutime())).list();
		close();
		return list;
	}

}
