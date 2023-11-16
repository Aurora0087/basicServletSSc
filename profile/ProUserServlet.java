package com.webApp.profile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/becomePro")
public class ProUserServlet extends HttpServlet {
	
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session= request.getSession();
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
    	
    	Integer uid = Integer.valueOf((String) session.getAttribute("id"));
    	
    	String cardName = (String) request.getAttribute("cardName");
    	
    	if (becomePro(uid)&& cardName!=null && !cardName.isEmpty()) {
    		
    		request.setAttribute("status", "updated");
			dispatcher.forward(request, response);
		}
    	else if (cardName==null || cardName.isEmpty()) {
    		request.setAttribute("status", "faild-card-number is not valaid");
			dispatcher.forward(request, response);
		}
    	
    	else {
    		request.setAttribute("status", "faild");
			dispatcher.forward(request, response);

		}
    	
    }
    
    private Boolean becomePro(Integer uid) {
		
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String sql = "UPDATE app_user SET is_pro = 1 WHERE id = ?";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
       		 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
       	            
       	            preparedStatement.setInt(1, uid);
       	           Integer isUpdateInteger = preparedStatement.executeUpdate();
       	           
       	           if (isUpdateInteger>0) {
       	        	   return true;
       	        	   
   				}
       	           else {
       	        	   return false;
   				}
       	 } catch (SQLException e) {
   			e.printStackTrace();
   		}
    	return false;
	}
}
