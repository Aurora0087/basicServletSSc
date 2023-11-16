package com.webApp.loginRegister;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	
    	String uEmail = request.getParameter("email");
        String uPassword = request.getParameter("password");
        
        HttpSession session= request.getSession();
        
        
        Connection connection = null;
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        
        try {
        	Class.forName("oracle.jdbc.OracleDriver");
        	
        	connection=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        	
        	String sql = "select * from app_user where email = ? and password = ?";
        	
        	PreparedStatement statement = connection.prepareStatement(sql);
        	
        	statement.setString(1, uEmail);
        	statement.setString(2, uPassword);
        	
        	ResultSet set =  statement.executeQuery();
        	if (set.next() && Integer.parseInt(set.getString("is_locked")) == 0) {
        		
        		session.setAttribute("name", set.getString("email"));
        		session.setAttribute("id", set.getString("id"));
        		session.setAttribute("role", set.getString("user_role"));
				dispatcher.forward(request, response);
			}
        	else if (set.next() && Integer.parseInt(set.getString("is_locked")) != 0) {
        		request.setAttribute("status", "user-locked");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
        	else {
				request.setAttribute("status", "user-not-exist");
				dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace();
		}
    }
}
