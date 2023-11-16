package com.webApp.loginRegister;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.webApp.role.UserRole;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("registration.jsp");

        // Check if email is unique before proceeding
        if (isEmailUnique(email)) {
        	try {
    				Class.forName("oracle.jdbc.OracleDriver");
    			} catch (ClassNotFoundException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                // Email is unique; insert the user data into the database
        		
                try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                	
                    String sql = "INSERT INTO app_user (id, first_name, last_name, email, password, profile_image, user_role, is_pro, is_locked) " +
                                 "VALUES (user_id_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                        preparedStatement.setString(1, firstName);
                        preparedStatement.setString(2, lastName);
                        preparedStatement.setString(3, email);
                        preparedStatement.setString(4, password);
                        preparedStatement.setString(5, "profile.jpg");
                        preparedStatement.setString(6, String.valueOf(UserRole.USER));
                        preparedStatement.setInt(7, 0);
                        preparedStatement.setInt(8, 0);
                       Integer isUpdate = preparedStatement.executeUpdate();
                       
                       
                       if (isUpdate>0) {
                    	   request.setAttribute("status", "succes");
                           request.setAttribute("message", "user registered.");
						
					}
                       else {
                    	   request.setAttribute("status", "fail");
                           request.setAttribute("message", "internal error!!!");
					}
                    }
                    
                    finally {
						dispatcher.forward(request, response);
					}
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }else {
                request.setAttribute("status", "fail-user-exist");
                request.setAttribute("message", "user allready registered.");
                dispatcher.forward(request, response);
            }
    }

    private boolean isEmailUnique(String email) {
    	
    	
    	try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        	
        	
            String sql = "SELECT COUNT(*) FROM app_user WHERE email = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                	
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count == 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Assume email is not unique if there's an error
    }

}

