package com.burlaka.playtikatest.model.dao;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.burlaka.playtikatest.model.Message;
import com.burlaka.playtikatest.model.User;

public class MessageDao extends AbstractDao {

	public void saveOrUpdate(Object obj) {
		super.saveOrUpdate(obj);
	}

	public List<Message> getLast() {
		startOperation();
		List<Message> list = session.createCriteria(Message.class)
				.addOrder(Order.asc("ctime")).setMaxResults(10).list();

		close();
		return list;
	}
	
	public List<Message> getUpdate(User user) {
		startOperation();
		List<Message> list = session.createCriteria(Message.class)
				.add(Restrictions.gt("ctime", user.getStime()))
				.add(Restrictions.ne("user", user)).list();
		close();
		return list;
	}

}
