package com.webApp.adminA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webApp.responseEntity.AdminVideoContentResponse;
import com.webApp.role.UserRole;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/adminvideodetails")
public class AdminShowVideoDetails extends HttpServlet {

	
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
    	
    	// Example: Retrieve video content data
    	String sql = "SELECT vc.id AS video_id,"
    		    + "    vc.title,"
    		    + "    vc.skill,"
    		    + "    vc.likes,"
    		    + "    vc.thumbnail_url,"
    		    + "    vc.free,"
    		    + "    vc.post_date,"
    		    + "	   vc.blocked,"
    		    + "    au.first_name,"
    		    + "    au.last_name,"
    		    + "    au.profile_image"
    		    + "	   FROM"
    		    + "    video_content vc"
    		    + "	   JOIN"
    		    + "    app_user au ON vc.user_id = au.id";

    		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		     PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		     ResultSet resultSet = preparedStatement.executeQuery()) {

    		    List<AdminVideoContentResponse> videoListResponse = new ArrayList<>();
    		    while (resultSet.next()) {
    		    	
    	         AdminVideoContentResponse video = new AdminVideoContentResponse(
    	        		 Integer.valueOf(resultSet.getInt("video_id")) ,
    	        		 resultSet.getString("title"),
    	        		 resultSet.getString("skill"),
    	        		 Integer.valueOf(resultSet.getInt("likes")),
    	        		 resultSet.getString("thumbnail_url"),
    	        		 resultSet.getBoolean("free"),
    	        		 resultSet.getDate("post_date"),
    	        		 resultSet.getBoolean("blocked"),
    	        		 resultSet.getString("first_name"),
    	        		 resultSet.getString("last_name"),
    	        		 resultSet.getString("profile_image")
    	        		 );
    	         videoListResponse.add(video);
    	         
    	     }

    	     
    	     request.setAttribute("videoList", videoListResponse);
    	     
    	     RequestDispatcher dispatcher = request.getRequestDispatcher("adminShowAllVideoDetails.jsp");
    	     dispatcher.forward(request, response);

    	} catch (SQLException e) {
    	     e.printStackTrace();
    	}
	}
	}
}
