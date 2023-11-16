package com.webApp.adminA;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webApp.responseEntity.AdminViewFeedbackResponse;
import com.webApp.role.UserRole;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/showfeedback")
public class AdminShowFeedbackServlet extends HttpServlet {
	
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
HttpSession session= request.getSession();
    	
    	String accessRole = String.valueOf(UserRole.USER);
    	
    	if (!accessRole.equals(String.valueOf(session.getAttribute("role")))) {
			response.sendRedirect("noAuth.jsp");
		}
    	else {
    		
    		String filter = request.getParameter("flt");
			
    		try {
    			Class.forName("oracle.jdbc.OracleDriver");
    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        		
        		
        		String sql = "SELECT f.id AS feedback_id, f.title, f.details, f.type, f.post_time, f.isdone, "
                        + "u.id AS user_id, u.first_name, u.last_name "
                        + "FROM feedback f "
                        + "JOIN app_user u ON f.user_id = u.id";
        		
        		if (filter!=null && !filter.isEmpty()) {
					sql = sql +" WHERE f.isdone = 0";
				}
        		System.out.println(filter);
        		System.out.println(sql);
        		
        		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        			ResultSet resultSet = preparedStatement.executeQuery();
        			
        			List<AdminViewFeedbackResponse> feedbackList = new ArrayList<>();
        			
        			while (resultSet.next()) {
                        AdminViewFeedbackResponse feedback = new AdminViewFeedbackResponse();
                        feedback.setId(resultSet.getInt("feedback_id"));
                        feedback.setTitle(resultSet.getString("title"));
                        feedback.setDetails(resultSet.getString("details"));
                        feedback.setType(resultSet.getString("type"));
                        feedback.setPostDate(resultSet.getDate("post_time"));
                        feedback.setIsDone(resultSet.getBoolean("isdone"));
                        feedback.setUserId(resultSet.getInt("user_id"));
                        feedback.setUserFsname(resultSet.getString("first_name"));
                        feedback.setUserLsname(resultSet.getString("last_name"));

                        feedbackList.add(feedback);
                    }
        			
        			request.setAttribute("feedbacks",feedbackList );
        		}
        		
        	} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        	RequestDispatcher dispatcher = request.getRequestDispatcher("adminViewFeedbacks.jsp");
        	dispatcher.forward(request, response);
		}
    }

}
