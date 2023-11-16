package com.webApp.adminA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.webApp.role.UserRole;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/lockuser")
public class lockUserServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session= request.getSession();
    	
    	String accessRole = String.valueOf(UserRole.USER);
    	
    	if (!accessRole.equals(String.valueOf(session.getAttribute("role")))) {
			response.sendRedirect("noAuth.jsp");
		}
    	else {
    	
    	Integer uid = Integer.valueOf(request.getParameter("uId"));
    	//RequestDispatcher dispatcher = request.getRequestDispatcher("serveUserDetails");
    	
    	
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String sql = "UPDATE app_user SET is_locked = CASE WHEN is_locked = 0 THEN 1 ELSE 0 END WHERE id = ?";
    	
    	 try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    	            
    	            preparedStatement.setInt(1, uid);
    	           Integer isUpdateInteger = preparedStatement.executeUpdate();
    	           
    	           if (isUpdateInteger>0) {
    	        	   response.sendRedirect("serveUserDetails");
				}
    	           else {
    	        	   
    	        	   response.sendRedirect("serveUserDetails");
				}
    	 } catch (SQLException e) {
    	    	
    	    response.sendRedirect("serveUserDetails");
			e.printStackTrace();
		}
    	
    	}

    }
}
