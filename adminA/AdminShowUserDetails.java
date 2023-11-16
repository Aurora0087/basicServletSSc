package com.webApp.adminA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webApp.responseEntity.AdminUserViewResponse;
import com.webApp.role.UserRole;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/serveUserDetails")
public class AdminShowUserDetails extends HttpServlet {
	
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
		
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    		
    		
    		String sql= "SELECT "
    				+ "au.id,"
    				+ "au.first_name,"
    				+ "au.last_name,"
    				+ "au.email,"
    				+ "au.profile_image,"
    				+ "au.user_role,"
    				+ "au.is_pro,"
    				+ "au.is_locked"
    				+ " FROM app_user au";
    		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    			ResultSet resultSet = preparedStatement.executeQuery();
    			
    			List<AdminUserViewResponse> userDetails= new ArrayList<>();
    			while (resultSet.next()) {
					
    				AdminUserViewResponse user = new AdminUserViewResponse(
    					Integer.valueOf(resultSet.getInt("id")),
    					resultSet.getString("first_name"),
    					resultSet.getString("last_name"),
    					resultSet.getString("email"),
    					"profileImage/"+resultSet.getString("profile_image"),
    					resultSet.getString("user_role"),
    					resultSet.getBoolean("is_pro"),
    					resultSet.getBoolean("is_locked")
    						);
    				
    				userDetails.add(user);
					
				}
    			
    			request.setAttribute("userDetails", userDetails);
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("adminViewUserDetails.jsp");
    	dispatcher.forward(request, response);
    }
    }
}
