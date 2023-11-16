package com.webApp.videoContent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webApp.responseEntity.VideoContentDetailsResponse;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/servevideos")
public class ServeVideoDetailsServlet extends HttpServlet {
	
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String topic = request.getParameter("topic");   
    	
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
    		    + "    au.first_name,"
    		    + "    au.last_name,"
    		    + "    au.profile_image"
    		    + "	   FROM"
    		    + "    video_content vc"
    		    + "	   JOIN"
    		    + "    app_user au ON vc.user_id = au.id"
    		    + "	   WHERE"
    		    + "    vc.blocked = 0";
    	
    	if (topic!=null && !topic.isEmpty()) {
			sql=sql+" AND vc.skill LIKE '%"+topic+"%'";
		}

    		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		     PreparedStatement preparedStatement = connection.prepareStatement(sql);
    		     ResultSet resultSet = preparedStatement.executeQuery()) {

    		    List<VideoContentDetailsResponse> videoList = new ArrayList<>();
    		    while (resultSet.next()) {
    		    	
    	         // Extract data from result set and create VideoContent objects
    	         VideoContentDetailsResponse video = new VideoContentDetailsResponse(
    	        		 Integer.valueOf(resultSet.getInt("video_id")) ,
    	        		 resultSet.getString("title"),
    	        		 resultSet.getString("skill"),
    	        		 Integer.valueOf(resultSet.getInt("likes")),
    	        		 "videos/"+resultSet.getString("thumbnail_url"),
    	        		 resultSet.getBoolean("free"),
    	        		 resultSet.getDate("post_date"),
    	        		 resultSet.getString("first_name"),
    	        		 resultSet.getString("last_name"),
    	        		 "profileImage/"+resultSet.getString("profile_image")
    	        		 );
    	         videoList.add(video);
    	     }

    	     
    	     request.setAttribute("videoList", videoList);
    	     
    	     RequestDispatcher dispatcher = request.getRequestDispatcher("showAllVideoDetails.jsp");
    	     dispatcher.forward(request, response);

    	} catch (SQLException e) {
    	     e.printStackTrace();
    	}

    }

}
