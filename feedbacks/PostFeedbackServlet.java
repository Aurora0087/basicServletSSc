package com.webApp.feedbacks;

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


@WebServlet("/submitFeedback")
public class PostFeedbackServlet extends HttpServlet {

	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
        String title = request.getParameter("title");
        String details = request.getParameter("details");
        String type = request.getParameter("type").toUpperCase();

        // Assuming you have the user's ID stored in the session
        HttpSession session = request.getSession();
        Integer userId = Integer.valueOf((String) session.getAttribute("id"));

        RequestDispatcher dispatcher = request.getRequestDispatcher("postFeedback.jsp");
        
        // Insert feedback data into the database
        if (insertFeedback(title, details, type, userId)) {
        	request.setAttribute("status", "ok");
		}
        else {
			request.setAttribute("status", "fail");
		}

        
        dispatcher.forward(request, response);
    }
	
	
    private Boolean insertFeedback(String title, String details, String type, Integer userId) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO feedback (id, title, details, type, post_time, user_id, isdone) " +
                         "VALUES (feedback_id_seq.NEXTVAL, ?, ?, ?, CURRENT_TIMESTAMP, ?, 0)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, details);
                preparedStatement.setString(3, type);
                preparedStatement.setInt(4, userId);
                Integer isDone = preparedStatement.executeUpdate();
                if (isDone!= 0) {
					return true;
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
