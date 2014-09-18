package com.burlaka.playtikatest.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.burlaka.playtikatest.model.User;
import com.burlaka.playtikatest.model.dao.UserDao;
import com.google.gson.Gson;

@WebServlet("/Users")
public class Users extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static final Logger logger = LogManager.getLogger(Users.class);

	UserDao userdao;

	public Users() {
		super();
		userdao = new UserDao();
	}

	/**
	 * get update of new user every 30 seconds
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("login"))
					userName = cookie.getValue();
			}

		if (userName != null) {
			User user = userdao.getByLogin(userName);
			List<User> usrs = userdao.getUpdate(user);
			List<String> usrsm = new LinkedList<String>();

			for (User u : usrs)
				usrsm.add(u.getLogin());
			String json = new Gson().toJson(usrsm);

			user.setSutime(new Date());

			userdao.saveOrUpdate(user);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
