package com.burlaka.playtikatest.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.burlaka.playtikatest.model.Message;
import com.burlaka.playtikatest.model.User;
import com.burlaka.playtikatest.model.dao.MessageDao;
import com.burlaka.playtikatest.model.dao.UserDao;
import com.google.gson.Gson;

@WebServlet("/Message")
public class Messages extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(Messages.class);

	MessageDao messagedao;
	UserDao userdao;

	public Messages() {
		super();
		messagedao = new MessageDao();
		userdao = new UserDao();
	}

	/**
	 * update new messages every second
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String userName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals("login"))
					userName = cookie.getValue();

		if (userName != null) {
			User user = userdao.getByLogin(userName);
			List<Message> msgs = messagedao.getUpdate(user);

			Map<String, String> msgsm = new LinkedHashMap<String, String>();
			for (Message m : msgs)
				msgsm.put(m.getUser().getLogin(), m.getMessage());

			String json = new Gson().toJson(msgsm);

			user.setStime(new Date());
			userdao.saveOrUpdate(user);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}

	/**
	 * add new message
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = request.getParameter("message");
		logger.info("post message " + message);
		String userName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals("login"))
					userName = cookie.getValue();

		logger.info("post cookies login " + userName);
		if (userName != null) {
			User user = userdao.getByLogin(userName);
			logger.fatal("post user id " + user.getId());
			Message m = new Message(message, user);
			messagedao.saveOrUpdate(m);
		}
	}

}
