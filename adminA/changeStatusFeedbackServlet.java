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


@WebServlet("/changefeedbackstatus")
public class changeStatusFeedbackServlet extends HttpServlet {
	
	
	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
    	HttpSession session= request.getSession();
    	
    	String accessRole = String.valueOf(UserRole.USER);
    	Integer fId =Integer.valueOf(request.getParameter("fId"));
    	System.out.println(fId);
    	
    	if (!accessRole.equals(String.valueOf(session.getAttribute("role")))) {
			response.sendRedirect("noAuth.jsp");
		}
    	else if(fId != null) {
    		
    		
    		RequestDispatcher dispatcher=request.getRequestDispatcher("showfeedback");
    		
    		
    		
    		if (updateFeedbackStatus(fId)) {
    			
				response.sendRedirect("showfeedback");
			}
			else {
				response.sendRedirect("showfeedback");
			}
		}
    }
    
    private Boolean updateFeedbackStatus(int fid) {
    	
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE feedback SET isdone = CASE WHEN isdone = 1 THEN 0 ELSE 1 END WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, fid);
                preparedStatement.executeUpdate();
                
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

}
