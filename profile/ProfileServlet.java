package com.webApp.profile;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String uName = (String) session.getAttribute("name");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
		
		request.setAttribute("status", "ok");
		request.setAttribute("email", uName);
		dispatcher.forward(request, response);
	}
}
