package com.burlaka.playtikatest.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

@WebServlet(urlPatterns = { "/" })
public class Home extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(Home.class);

	public Home() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserDao userdao = new UserDao();

		String login = request.getParameter("login");
		logger.info("post login " + login);
		RequestDispatcher rd = null;
		User user = userdao.getByLogin(login);
		
		if (user != null && user.getStatus().equals("i"))
			rd = request.getRequestDispatcher("/WEB-INF/error.jsp");
		else {
			if (user == null)
				user = new User(login);
			else
				user.setStatus("i");

			userdao.saveOrUpdate(user);

			Cookie userName = new Cookie("login", login);
			userName.setMaxAge(30 * 60);
			response.addCookie(userName);

			fillRequest(request, user);

			rd = request.getRequestDispatcher("/WEB-INF/success.jsp");
		}

		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserDao userdao = new UserDao();
		String userName = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals("login"))
					userName = cookie.getValue();

		RequestDispatcher rd = null;
		User user = userdao.getByLogin(userName);
		if (user != null && user.getStatus().equals("i")) {
			fillRequest(request, user);

			rd = request.getRequestDispatcher("/WEB-INF/success.jsp");
		} else
			rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
		rd.forward(request, response);
	}

	private void fillRequest(HttpServletRequest request, User user) {
		UserDao userdao = new UserDao();
		MessageDao messagedao = new MessageDao();

		List<Message> listm = messagedao.getLast();
		request.setAttribute("messages", listm);
		List<User> listu = userdao.getUsers();
		request.setAttribute("users", listu);
		request.setAttribute("user", user);

	}
}
