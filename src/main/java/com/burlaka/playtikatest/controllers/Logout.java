package com.burlaka.playtikatest.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(Logout.class);

	UserDao userdao;

	public Logout() {
		super();
		userdao = new UserDao();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		Cookie loginCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals("login")) {
					loginCookie = cookie;
					break;
				}
		logger.info("post logout cookie " + (loginCookie != null));
		
		if (loginCookie != null) {
			String userName = loginCookie.getValue();
			logger.info("post logout username " + userName);
			User user = userdao.getByLogin(userName);
			logger.info("post logout user " + (user != null));
			user.setStatus("o");
			userdao.saveOrUpdate(user);
			
			loginCookie.setMaxAge(0);
			response.addCookie(loginCookie);
		}
		RequestDispatcher rd = request
				.getRequestDispatcher("/WEB-INF/login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
