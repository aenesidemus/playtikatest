package com.burlaka.playtikatest.model.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class AbstractDao {
	protected Session session;
	private Transaction tx;
	private HibernateFactory hf = HibernateFactory.getInstance();

	private static final Logger logger = LogManager.getLogger(HibernateFactory.class);

	public AbstractDao() {
	}

	protected void saveOrUpdate(Object obj) {
		startOperation();
		session.saveOrUpdate(obj);
		tx.commit();
	}

	protected void delete(Object obj) {
		startOperation();
		session.delete(obj);
		tx.commit();
	}

	protected Object find(Class<?> clazz, Long id) {
		Object obj = null;
		startOperation();
		obj = session.load(clazz, id);
		tx.commit();
		return obj;
	}

	protected List<?> findAll(Class<?> clazz) {
		List<?> objects = null;
		startOperation();
		Query query = session.createQuery("from " + clazz.getName());
		objects = query.list();
		tx.commit();
		return objects;
	}

	public void close() {
		if (session != null) {
			try {
				session.close();
			} catch (HibernateException ignored) {
				logger.error("Couldn't close Session", ignored);
			}
		}
	}

	protected void startOperation() throws HibernateException {
		session = hf.openSession();
		tx = session.beginTransaction();
	}

}
