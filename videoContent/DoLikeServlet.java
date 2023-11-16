package com.webApp.videoContent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/like")
public class DoLikeServlet extends HttpServlet {

	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		Integer vId = Integer.valueOf(request.getParameter("vId"));
		
		HttpSession session = request.getSession();
		
		Integer uId = Integer.valueOf((String) session.getAttribute("id"));
		
		// Check if the user has already liked the video
        if (hasLiked(uId, vId)) {
            // User has liked the video, so unlike it
            unlikeVideo(uId, vId);
        } else {
            // User has not liked the video, so like it
            likeVideo(uId, vId);
        }
		
	}
	
	private boolean hasLiked(Integer uId, Integer vId) {
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    String sql = "SELECT COUNT(*) FROM LikesList WHERE user_id = ? AND v_id = ?";
	    System.out.println(sql);
	    
	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setInt(1, uId);
	        preparedStatement.setInt(2, vId);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	        	if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count != 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

	
	private void unlikeVideo(Integer uId, Integer vId) {
		
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	        // Remove the like entry from LikesList
	        String deleteLikeSql = "DELETE FROM LikesList WHERE user_id = ? AND v_id = ?";
	        
	        // Decrease the likes count in VideoContent
	        String updateLikesSql = "UPDATE video_content SET likes = likes - 1 WHERE id = ?";
	        
	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        	
	        	System.out.println(deleteLikeSql + " | "+uId+vId);
	            // Remove the like entry
	            try (PreparedStatement deleteLikeStatement = connection.prepareStatement(deleteLikeSql)) {
	                deleteLikeStatement.setInt(1, uId);
	                deleteLikeStatement.setInt(2, vId);
	                deleteLikeStatement.executeUpdate();
	            }

	            System.out.println(updateLikesSql);
	            // Decrease likes count in VideoContent
	            try (PreparedStatement updateLikesStatement = connection.prepareStatement(updateLikesSql)) {
	                updateLikesStatement.setInt(1, vId);
	                updateLikesStatement.executeUpdate();
	            }catch (SQLException e) {
		            e.printStackTrace();
		        }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

	
	private void likeVideo(Integer uId, Integer vId) {
		
		
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
	        String insertLikeSql = "INSERT INTO LikesList (user_id, v_id, likedat) VALUES (?, ?, ?)";
	        
	        // Increase the likes count in VideoContent
	        String updateLikesSql = "UPDATE video_content SET likes = likes + 1 WHERE id = ?";
	        
	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        	
	        	System.out.println(insertLikeSql);
	            // Add the like entry
	            try (PreparedStatement insertLikeStatement = connection.prepareStatement(insertLikeSql)) {
	                insertLikeStatement.setInt(1, uId);
	                insertLikeStatement.setInt(2, vId);
	                insertLikeStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
	                insertLikeStatement.executeUpdate();
	            }

	            System.out.println(updateLikesSql);
	            // Increase likes count in VideoContent
	            try (PreparedStatement updateLikesStatement = connection.prepareStatement(updateLikesSql)) {
	                updateLikesStatement.setInt(1, vId);
	                updateLikesStatement.executeUpdate();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

	
}
