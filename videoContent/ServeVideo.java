package com.webApp.videoContent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.management.Descriptor;

import com.webApp.responseEntity.VideoEntity;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/video")
public class ServeVideo extends HttpServlet{
	
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session= request.getSession();
    	
    	Integer uId = Integer.parseInt((String) session.getAttribute("id"));
    	
    	Integer vId = Integer.parseInt(request.getParameter("vId"));
    	
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String sql = "SELECT"
    			+ "	   vc.id AS video_id,"
    		    + "    vc.title,"
    		    + "    vc.skill,"
    		    + "    vc.likes,"
    		    + "    vc.video_url,"
    		    + "    vc.views,"
    		    + "    vc.free,"
    		    + "    vc.post_date,"
    		    + "    au.first_name,"
    		    + "    au.last_name,"
    		    + "    au.profile_image"
    		    + " FROM "
    		    + "    app_user vu, video_content vc"
    		    + " JOIN "
    		    + "    app_user au ON vc.user_id = au.id"
    		    + " WHERE " 
    		    + "    ((vc.blocked = 0 AND vc.free = 1) OR vu.is_pro = 1 OR (vu.id = au.id))"
    		    + "    AND (vu.id = ? AND vc.id = ?)";
    	
    	try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		     PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

    		    preparedStatement.setInt(1, uId);
    		    preparedStatement.setInt(2, vId);

    		    ResultSet resultSet = preparedStatement.executeQuery();
    		    
    		    RequestDispatcher descriptor = request.getRequestDispatcher("watchVideo.jsp");
    		    
    		    if (resultSet.next()) {
					VideoEntity video= new VideoEntity(
							Integer.valueOf(resultSet.getString("video_id")),
							resultSet.getString("skill"), 
							resultSet.getString("title"), 
							Integer.valueOf(resultSet.getString("likes")), 
							Integer.valueOf(resultSet.getString("views")), 
						"videos/"+	resultSet.getString("video_url"), 
							resultSet.getDate("post_date"), 
							resultSet.getString("first_name"), 
							resultSet.getString("last_name"), 
							resultSet.getString("profile_image")
							);
					
					request.setAttribute("video", video);
				}
    		    else {
    		    	VideoEntity video= new VideoEntity();
    		    	
    		    	
    		    	request.setAttribute("message", "you dont have access to watch this content.");
    		    	request.setAttribute("video", video);
					
				}
    		    descriptor.forward(request, response);

    		} catch (SQLException e) {
    		    e.printStackTrace();
    		}
	}
}
