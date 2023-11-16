package com.webApp.role;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/becomethecher")
public class ChangeRoleTeacher extends HttpServlet {
	
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	HttpSession session= request.getSession();
    	
    Integer uId = Integer.valueOf((String) session.getAttribute("id"));
    
    if (uId!=null) {
		
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	String sql = "UPDATE app_user SET user_role = THECHER WHERE id = ? AND user_role = USER";
    	
    	 try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    	            
    	            preparedStatement.setInt(1, uId);
    	           Integer isUpdateInteger = preparedStatement.executeUpdate();
    	           
    	           if (isUpdateInteger>0) {
    	        	   response.sendRedirect("profile");
				}
    	           else {
    	        	   
    	        	   response.sendRedirect("profile");
				}
    	 } catch (SQLException e) {
    	    	
    	    response.sendRedirect("profile");
			e.printStackTrace();
		}
	}
    else {
		response.sendRedirect("login.jsp");
	}
    }
}
