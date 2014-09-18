package com.burlaka.playtikatest.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateFactory {

	private static HibernateFactory instance = null;
	private SessionFactory sessionFactory;
	private static final Logger logger = LogManager
			.getLogger(HibernateFactory.class);

	private HibernateFactory() {
		configureSessionFactory();
	}

	static HibernateFactory getInstance() {
		if (instance == null)
			synchronized (HibernateFactory.class) {
				if (instance == null)
					instance = new HibernateFactory();
			}

		return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Session openSession() {
		return sessionFactory.openSession();
	}

	private SessionFactory configureSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		logger.info("Hibernate Configuration loaded");

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		logger.info("Hibernate serviceRegistry created");

		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		return sessionFactory;
	}

}
